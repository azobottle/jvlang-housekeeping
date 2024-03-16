package com.jvlang.housekeeping.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jvlang.housekeeping.pojo.TODO;
import dev.hilla.EndpointInvoker;
import dev.hilla.exception.EndpointException;
import jakarta.annotation.Nullable;
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
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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

    @Pointcut("execution(* dev.hilla.EndpointController.serveEndpoint(..))")
    public void onEndPoint() {
    }

    @Around("onEndPoint()")
    public ResponseEntity<String> aroundEndPoint(ProceedingJoinPoint pjp) {
        var args = pjp.getArgs();
        String endpointName = (String) args[0];
        String methodName = (String) args[1];
        @Nullable ObjectNode body = (ObjectNode) args[2];
        log.debug("[{} {}] before endpoint , args is {}",
                endpointName, methodName, Arrays.asList(args));
        var checkAuthRes = checkAuth(endpointName, methodName);
        if (checkAuthRes != null) {
            return checkAuthRes;
        }
        try {
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
            log.debug("[{} {}] finally endpoint",
                    endpointName, methodName);
        }
    }

    public static String createResponseErrorObject(ObjectMapper om, String errorMessage) {
        ObjectNode objectNode = om.createObjectNode();
        objectNode.put(EndpointException.ERROR_MESSAGE_FIELD, errorMessage);
        return objectNode.toString();
    }

    @Nullable
    private ResponseEntity<String> checkAuth(
            @NonNull String endpointName,
            @NonNull String methodName
    ) {
        Class<?> clz;
        try {
            clz = Class.forName("com.jvlang.housekeeping.endpoint." + endpointName);
        } catch (ClassNotFoundException e) {
            log.error("Can't found endpoint class for name {}", endpointName, e);
            return ResponseEntity
                    .internalServerError()
                    .body(createResponseErrorObject(objectMapper, "服务器发生错误(反射获取接口类失败)"));
        }
        var methods = Arrays.stream(clz.getDeclaredMethods())
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
                Arrays.stream(clz.getAnnotations()),
                method != null ? Arrays.stream(method.getAnnotations()) : Stream.empty()
        ).filter(it -> it instanceof AllowRole).toList();

        if (annoList.isEmpty()) {
            log.warn("[{} {}] 未对此接口设置权限！", endpointName, methodName);
        }

        for (Annotation anno : annoList) {
            if (anno instanceof AllowRole a) {
                log.debug("[{} {}] check permission for roles {}",
                        endpointName, methodName, anno);
                // TODO
                log.warn("这里的鉴权代码还没写！");
            }
        }
        return null;
    }
}
