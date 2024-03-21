package com.jvlang.housekeeping.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jvlang.housekeeping.pojo.entity.User;
import com.jvlang.housekeeping.repo.UserRepository;
import com.jvlang.housekeeping.util.UserUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.jvlang.housekeeping.util.Utils.Http.createResponseErrorObject;

@RestController
@Slf4j
public class LoginController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    HttpServletResponse response;

    @Autowired
    UserUtils userUtils;

    @PostMapping("/jvlang/login")
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
        return userRepository
                .findOne(Example.of(User.builder()
                        .username(body.username)
                        .encodedPassword(UserUtils.encodedPassword(body.password))
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
