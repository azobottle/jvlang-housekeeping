package com.jvlang.housekeeping.test.util;

import com.jvlang.housekeeping.Application;
import com.jvlang.housekeeping.util.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

@SpringBootTest(classes = Application.class)
@Slf4j
public class UserUtilsApplicationTest extends AbstractTestNGSpringContextTests {
    @Autowired
    UserUtils userUtils;

    @Test
    void testSimple() {
        log.debug("userUtils is {}", userUtils);
        var key = userUtils.getPrivateKey();
        log.debug("key is {}", key);
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
    }
}
