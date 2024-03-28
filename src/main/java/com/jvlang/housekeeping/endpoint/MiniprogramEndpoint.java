package com.jvlang.housekeeping.endpoint;

import com.jvlang.housekeeping.aop.AllowNoLogin;
import com.jvlang.housekeeping.pojo.vo.MiniprogramLoginResult;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;
import lombok.extern.slf4j.Slf4j;

@Endpoint
@AnonymousAllowed
@Slf4j
public class MiniprogramEndpoint {
    @AllowNoLogin
    public MiniprogramLoginResult login(String code) {
        // https://api.weixin.qq.com/sns/jscode2sessionappid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
        return MiniprogramLoginResult.builder()
                .success(true)
                .build();
    }
}
