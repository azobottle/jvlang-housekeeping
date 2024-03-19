package com.jvlang.housekeeping.test.util;

import com.jvlang.housekeeping.test.TestUtils;
import com.jvlang.housekeeping.util.UserUtils;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.crypto.SecretKey;


@Slf4j
public class UserUtilsTest {
    @BeforeClass
    void setup() {
        TestUtils.setup();
    }

    @Test
    void testSimpleExample() {
        SecretKey key = Jwts.SIG.HS256.key().build();
        var jws = UserUtils.assignJwt(114514L, key);
        log.debug("jws  {}", jws);
        var jws2 = UserUtils
                .jwtParser(key)
                .parseSignedClaims(jws);
        log.debug("jws2 {}", jws2);

        Assert.assertEquals(
                jws2.getPayload().get("user_id", Long.class),
                114514L
        );

        Assert.assertThrows(JwtException.class, () -> {
            UserUtils.jwtParser(key)
                    .parseSignedClaims(jws + "_will_error");
        });
    }
}
