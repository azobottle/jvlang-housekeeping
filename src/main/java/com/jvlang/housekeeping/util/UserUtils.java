package com.jvlang.housekeeping.util;

import com.jvlang.housekeeping.pojo.JwtUser;
import com.jvlang.housekeeping.pojo.TODO;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.Nullable;
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
}
