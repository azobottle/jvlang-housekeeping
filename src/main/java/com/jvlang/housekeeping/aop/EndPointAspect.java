package com.jvlang.housekeeping.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jvlang.housekeeping.Application;
import com.jvlang.housekeeping.pojo.JwtUser;
import com.jvlang.housekeeping.pojo.Role0;
import com.jvlang.housekeeping.pojo.exceptions.BusinessFailed;
import com.jvlang.housekeeping.repo.UserRoleRepository;
import com.jvlang.housekeeping.util.ThreadLocalUtils;
import com.jvlang.housekeeping.util.UserUtils;
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.jvlang.housekeeping.util.Utils.Http.createResponseErrorObject;
import static com.jvlang.housekeeping.util.Utils.Throwables.findAnyCauseInstanceOf;

@Aspect
@Component
@Slf4j
public class EndPointAspect {

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
        var args = pjp.getArgs();
        String endpointName = (String) args[0];
        String methodName = (String) args[1];
        @org.checkerframework.checker.nullness.qual.Nullable ObjectNode body = (ObjectNode) args[2];
        log.debug("[{} {}] >>> ------------------------- before endpoint , args is {}",
                endpointName, methodName, Arrays.asList(args));
        var u = userUtils.readUser();
        ThreadLocalUtils.BusinessThreadScope.enter(u, null);
        try {
            ThreadLocalUtils.BusinessThreadScope.assertEnteredBusinessThreadScope();
            try {
                var checkAuthRes = checkAuth(endpointName, methodName, u);
                if (checkAuthRes != null) {
                    return checkAuthRes;
                }
                //noinspection unchecked
                var res = (ResponseEntity<String>) pjp.proceed(args);
                log.debug("[{} {}] after endpoint , result is {}",
                        endpointName, methodName, res);
                if (res.getStatusCode().isError()) {
                    log.debug("[{} {}] error on endpoint", endpointName, methodName);
                }
                return res;
            } catch (Throwable e) {
                log.error("[{} {}] error on endpoint not catched",
                        endpointName, methodName);
                return ResponseEntity
                        .internalServerError()
                        .body(createResponseErrorObject(objectMapper, "服务器未知错误: " + e.getMessage()));
            } finally {
                log.debug("[{} {}] <<< ------------------------- finally endpoint",
                        endpointName, methodName);
            }
        } finally {
            ThreadLocalUtils.BusinessThreadScope.exit();
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
            methods = methods.stream().sorted((a, b) -> {
                var ca = a.getDeclaringClass();
                var cb = b.getDeclaringClass();
                if (ca == cb) return 0;
                return ca.isAssignableFrom(cb) ? 1 : -1;
            }).toList();
            log.trace("Sorted methods : {}", methods
                    .stream()
                    .map(it -> "%s (declaring class is %s)"
                            .formatted(
                                    it,
                                    it.getDeclaringClass()
                            ))
                    .toList());
            var subClass = methods.get(0).getDeclaringClass();
            methods = methods.stream().filter(it -> it.getDeclaringClass() == subClass).toList();
            log.trace("Filtered methods : {}", methods);
        }

        var allowRoles = new HashSet<Role0>();
        var allowNoLogin = new AtomicBoolean(false);

        for (var anno : endpointClz.getAnnotations()) {
            prepareAllowRoles(anno, allowRoles, allowNoLogin);
        }
        for (var method : methods) {
            var annoList = method.getAnnotations();

            for (var anno : annoList) {
                prepareAllowRoles(anno, allowRoles, allowNoLogin);
            }
        }

        if (allowNoLogin.get()) {
            log.debug("[{} {}] Allow no login", endpointName, methodName);
            return null;
        }

        if (allowRoles.isEmpty()) {
            log.warn("\n" + """
                    +------------------------------ WARNING ------------------------------+
                    | 未对 [{} {}] 接口设置权限！
                    |
                    | 如果需要允许所有已登录的人访问，则应当使用 @AllowRoleAll 在Endpoint的类或方法上
                    | 如果允许不登录即可访问，则应当使用 @AllowNoLogin 在Endpoint的类或方法上
                    | 如果需要特定角色才能访问，则应当使用 @AllowRole
                    +---------------------------------------------------------------------+
                    """, endpointName, methodName);
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

        var repository = Application.getContext().getBean(UserRoleRepository.class);
        var roles = repository.findRolesByUserId(jwtUser.getUserId());

        log.debug("[{} {}] check permission : allowRoles = {} , roles = {}",
                endpointName, methodName, allowRoles, roles);

        // 取交集
        var rolesWereAllowed = new HashSet<>(allowRoles);
        rolesWereAllowed.retainAll(roles);

        if (rolesWereAllowed.isEmpty()) {
            return ResponseEntity
                    .status(403)
                    .body(createResponseErrorObject(
                            objectMapper,
                            "您没有权限进行操作 [" + endpointName + " " + methodName +
                                    "] , 您的角色是 " + roles + " , 但是需要角色 " + allowRoles + " .")
                    );
        }

        return null;
    }

    private void prepareAllowRoles(Annotation anno, Set<Role0> allowRoles, AtomicBoolean allowNoLogin) {
        if (anno instanceof AllowRole a) {
            allowRoles.addAll(Arrays.asList(a.value()));
        }
        if (anno instanceof AllowRoleAll) {
            allowRoles.addAll(Arrays.asList(Role0.values()));
        }
        if (anno instanceof AllowNoLogin) {
            allowNoLogin.set(true);
        }
    }
}
