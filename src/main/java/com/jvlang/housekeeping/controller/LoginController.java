package com.jvlang.housekeeping.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jvlang.housekeeping.pojo.Role0;
import com.jvlang.housekeeping.pojo.entity.User;
import com.jvlang.housekeeping.pojo.entity.UserRole;
import com.jvlang.housekeeping.repo.UserRepository;
import com.jvlang.housekeeping.repo.UserRoleRepository;
import com.jvlang.housekeeping.util.ThreadLocalUtils;
import com.jvlang.housekeeping.util.UserUtils;
import com.jvlang.housekeeping.util.Utils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.jvlang.housekeeping.util.UserUtils.DEV_ADMIN_PLAIN_PASSWORD;
import static com.jvlang.housekeeping.util.UserUtils.DEV_ADMIN_USERNAME;
import static com.jvlang.housekeeping.util.Utils.Http.createResponseErrorObject;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@Slf4j
public class LoginController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserUtils userUtils;

    @Autowired
    Environment env;


    @PostMapping("/api/jvlang/login")
    public ResponseEntity<String> login(@RequestBody LoginBody body) {
        if (Strings.isEmpty(body.username)) {
            return ResponseEntity
                    .badRequest()
                    .body(createResponseErrorObject(objectMapper, "用户名不能为空"));
        }
        if (Strings.isEmpty(body.password)) {
            return ResponseEntity
                    .badRequest()
                    .body(createResponseErrorObject(objectMapper, "密码不能为空"));
        }
        if (Utils.Env.profileIs(env, "dev")
                && body.username.equals(DEV_ADMIN_USERNAME)
                && body.password.equals(DEV_ADMIN_PLAIN_PASSWORD)
        ) {
            synchronized (LoginController.class) {
                ThreadLocalUtils.BusinessThreadScope.enter(null, null);
                try {
                    var existed_dev = userRepository.findOne(
                            Example.of(User.builder()
                                    .username(DEV_ADMIN_USERNAME)
                                    .build()
                            ));
                    if (existed_dev.isEmpty()) {
                        log.warn("\n" + """
                                +-------------------------------+
                                |    Create Dev Admin user !    |
                                +-------------------------------+
                                """);

                        var saved = userRepository.save(User.builder()
                                .username(DEV_ADMIN_USERNAME)
                                .encodedPassword(UserUtils.encodePassword(DEV_ADMIN_PLAIN_PASSWORD))
                                .description("仅当在开发模式以正确的用户名和密码登陆时才会创建")
                                .nickName("超管（开发模式）")
                                .build()
                        );
                        userRoleRepository.save(UserRole.builder()
                                .userId(Objects.requireNonNull(saved).getId())
                                .role(Role0.SuperAdmin)
                                .build()
                        );

                    }
                } finally {
                    ThreadLocalUtils.BusinessThreadScope.exit();
                }
            }
        }
        return userRepository
                .findOne(Example.of(User.builder()
                        .username(body.username)
                        .encodedPassword(UserUtils.encodePassword(body.password))
                        .build()))
                .map(it -> {
                    log.info("User login success : {}", it);
                    return it;
                })

                .map(user -> {
                    // Need see : node_modules\@hilla\frontend\Authentication.js -> login function
                    return ResponseEntity
                            .status(200)
                            .header(
                                    "x-jvlang-set-auth",
                                    "Bearer " + UserUtils.assignJwt(user.getId(), userUtils.getPrivateKey())
                            )
                            .header("Result", "success")
                            .body("{\"message\":\"登录成功\"}");
                })
                .orElseGet(() -> {
                    log.warn("User login failed : {}", body);
                    return ResponseEntity
                            .badRequest()
                            .body(createResponseErrorObject(objectMapper, "用户名或密码错误"));
                });
    }

    @Data
    public static class LoginBody {
        String username;
        String password;
    }
}
