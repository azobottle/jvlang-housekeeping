package com.jvlang.housekeeping.util;

import com.jvlang.housekeeping.pojo.JwtUser;
import com.jvlang.housekeeping.pojo.TODO;
import dev.hilla.EndpointInvocationException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Lombok;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Component
@Slf4j
public class UserUtils {
    private static final ThreadLocal<JwtUser> currentUser = new ThreadLocal<>();

    public static void _set_current_user(JwtUser u) {
        currentUser.set(u);
    }

    public static void _remove_current_user() {
        currentUser.remove();
    }

    @Nullable
    public static JwtUser getCurrentUser() {
        return currentUser.get();
    }

    @Autowired
    Environment env;

    @Autowired
    HttpServletRequest request;

    private volatile SecretKey _key;

    public SecretKey getPrivateKey() {
        var profiles = Arrays.asList(env.getActiveProfiles());
        if (profiles.contains("dev") || profiles.contains("test")) {
            if (_key == null) synchronized (UserUtils.class) {
                if (_key == null) {
                    log.info("Init key , profiles are {}", profiles);
                    byte[] bytes;
                    try (var ins = Utils.ClassLoader.getResourceAsStream("jwt_dev.key")) {
                        var outs = new ByteArrayOutputStream();
                        ins.transferTo(outs);
                        bytes = outs.toByteArray();
                    } catch (IOException e) {
                        throw Lombok.sneakyThrow(e);
                    }
                    _key = new SecretKeySpec(bytes, 0, bytes.length, "HmacSHA256");
                }
            }
            return _key;
        } else {
            throw new TODO("还未决定在生产环境中部署私钥的方式。"); // TODO
        }
    }

    public static String encodedPassword(@NonNull String password) {
        return password;
    }

    public static String assignJwt(@NonNull Long user_id, @NonNull SecretKey key) {
        log.debug("Assign Jwt for user {}", user_id);
        return Jwts.builder()
                .subject("auth")
                .issuedAt(new Date())
                .issuer("jvlang")
                .claim("user_id", user_id)
                .signWith(key)
                .compact();
    }

    public static JwtParser jwtParser(SecretKey key) {
        return Jwts.parser()
                .verifyWith(key)
                .build();
    }

    @Nullable
    public JwtUser readUser() {
        var ha = request.getHeader("Authorization");
        log.debug("Authorization header : {}", ha);
        if (ha == null) {
            return null;
        }
        String token;
        if (ha.startsWith("Bearer ")) {
            token = ha.substring("Bearer ".length());
        } else {
            throw new RuntimeException("格式不支持的token，认证失败");
        }
        try {
            var payload = UserUtils.jwtParser(getPrivateKey())
                    .parseSignedClaims(token)
                    .getPayload();
            return JwtUser.Impl.builder()
                    .userId(Objects.requireNonNull(payload.get("user_id", Long.class)))
                    .build();
        } catch (JwtException err) {
            throw new RuntimeException("错误的token，认证失败");
        }
    }
}
