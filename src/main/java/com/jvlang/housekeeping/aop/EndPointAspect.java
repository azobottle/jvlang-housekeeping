package com.jvlang.housekeeping.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jvlang.housekeeping.Application;
import com.jvlang.housekeeping.pojo.JwtUser;
import com.jvlang.housekeeping.pojo.Role0;
import com.jvlang.housekeeping.pojo.exceptions.BusinessFailed;
import com.jvlang.housekeeping.repo.RelationUserRoleRepository;
import com.jvlang.housekeeping.util.UserUtils;
import dev.hilla.EndpointInvocationException;
import dev.hilla.exception.EndpointException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Lombok;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Stream;

@Aspect
@Component
@Slf4j
public class EndPointAspect {
    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse response;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserUtils userUtils;

    @Pointcut("execution(* dev.hilla.EndpointController.serveEndpoint(..))")
    public void onEndPoint() {
    }

    @Around("onEndPoint()")
    public ResponseEntity<String> aroundEndPoint(ProceedingJoinPoint pjp) {
        try {
            var args = pjp.getArgs();
            String endpointName = (String) args[0];
            String methodName = (String) args[1];
            @org.checkerframework.checker.nullness.qual.Nullable ObjectNode body = (ObjectNode) args[2];
            log.debug("[{} {}] >>> before endpoint , args is {}",
                    endpointName, methodName, Arrays.asList(args));
            var u = readUser();
            try {
                UserUtils._set_current_user(u);
                try {
                    var checkAuthRes = checkAuth(endpointName, methodName, u);
                    if (checkAuthRes != null) {
                        return checkAuthRes;
                    }
                    var res = pjp.proceed(args);
                    log.debug("[{} {}] after endpoint , result is {}",
                            endpointName, methodName, res);
                    //noinspection unchecked
                    return (ResponseEntity<String>) res;
                } catch (Throwable e) {
                    log.debug("[{} {}] error on endpoint",
                            endpointName, methodName);
                    throw Lombok.sneakyThrow(e);
                } finally {
                    log.debug("[{} {}] <<< finally endpoint",
                            endpointName, methodName);
                }
            } finally {
                UserUtils._remove_current_user();
            }
        } catch (Throwable err) {
            if (err instanceof BusinessFailed) {
                response.setHeader("x-jvlang-business-failed", "1");
            }
            throw Lombok.sneakyThrow(err);
        }
    }

    private static String createResponseErrorObject(ObjectMapper om, String errorMessage) {
        ObjectNode objectNode = om.createObjectNode();
        objectNode.put(EndpointException.ERROR_MESSAGE_FIELD, errorMessage);
        return objectNode.toString();
    }

    @Nullable
    private JwtUser readUser() throws EndpointInvocationException.EndpointAccessDeniedException {
        var ha = request.getHeader("Authorization");
        if (ha == null) {
            return null;
        }
        try {
            var payload = UserUtils.jwtParser(userUtils.getPrivateKey())
                    .parseSignedClaims(ha)
                    .getPayload();
            return JwtUser.Impl.builder()
                    .userId(Objects.requireNonNull(payload.get("user_id", Long.class)))
                    .build();
        } catch (JwtException err) {
            throw new EndpointInvocationException.EndpointAccessDeniedException("错误的token，认证失败");
        }
    }

    @Nullable
    private ResponseEntity<String> checkAuth(
            @NonNull String endpointName,
            @NonNull String methodName,
            @Nullable JwtUser jwtUser
    ) {
        log.debug("[{} {}] check auth start", endpointName, methodName);
        Class<?> endpointClz;
        try {
            endpointClz = Class.forName("com.jvlang.housekeeping.endpoint." + endpointName);
        } catch (ClassNotFoundException e) {
            log.error("Can't found endpoint class for name {}", endpointName, e);
            return ResponseEntity
                    .internalServerError()
                    .body(createResponseErrorObject(objectMapper, "服务器发生错误(反射获取接口类失败)"));
        }
        var methods = Arrays.stream(endpointClz.getDeclaredMethods())
                .filter(m -> Modifier.isPublic(m.getModifiers()))
                .filter(m -> methodName.equals(m.getName()))
                .toList();
        if (methods.size() >= 2) {
            log.error("Multi methods {} in endpoint {} named {} !", methods, endpointName, methodName);
            return ResponseEntity
                    .internalServerError()
                    .body(createResponseErrorObject(objectMapper, "服务器发生错误(获取接口方法重复)"));
        }
        var method = methods.isEmpty() ? null : methods.get(0);
        var annoList = Stream.concat(
                Arrays.stream(endpointClz.getAnnotations()),
                method != null ? Arrays.stream(method.getAnnotations()) : Stream.empty()
        ).toList();

        var allowRoles = new HashSet<Role0>();
        for (Annotation anno : annoList) {
            if (anno instanceof AllowRole a) {
                allowRoles.addAll(Arrays.stream(a.value()).toList());
            }
        }
        if (allowRoles.isEmpty()) {
            log.warn("[{} {}] 未对此接口设置权限！", endpointName, methodName);
            return null;
        }

        if (jwtUser == null) {
            log.debug("[{} {}] need login", endpointName, methodName);
            return ResponseEntity
                    .status(401)
                    .body(createResponseErrorObject(
                            objectMapper,
                            "请登录")
                    );
        }

        var repository = Application.getContext().getBean(RelationUserRoleRepository.class);
        var roles = repository.findRoleNamesByUserId(jwtUser.getUserId());

        log.debug("[{} {}] check permission : allowRoles = {} , roles = {}",
                endpointName, methodName, allowRoles, roles);

        // 取交集
        var allowedRoles = new HashSet<>(allowRoles);
        allowedRoles.retainAll(roles);

        if (allowedRoles.isEmpty()) {
            return ResponseEntity
                    .status(403)
                    .body(createResponseErrorObject(
                            objectMapper,
                            "您没有权限进行操作 - " + endpointName + " " + methodName +
                                    " , 您的角色是 " + roles + " , 但是需要角色 " + allowedRoles + " .")
                    );
        }

        return null;
    }
}
