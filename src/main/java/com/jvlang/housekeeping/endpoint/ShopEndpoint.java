package com.jvlang.housekeeping.endpoint;

import com.jvlang.housekeeping.aop.AllowRoleAll;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;
import lombok.extern.slf4j.Slf4j;

/**
 * 关于商店页面信息的接口。
 */
@Endpoint
@AnonymousAllowed
@Slf4j
public class ShopEndpoint {
    @AllowRoleAll
    public void a() {

    }

//    public static class
}
