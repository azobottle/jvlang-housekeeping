package com.jvlang.housekeeping.util;

import com.jvlang.housekeeping.pojo.JwtUser;
import com.jvlang.housekeeping.pojo.vo.UserPubInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ThreadLocalUtils {
    private ThreadLocalUtils() {
    }

    public static final class BusinessThreadScope {

        private BusinessThreadScope() {
        }

        public static void enter(JwtUser u) {
            currentUser.set(u);
            userPubInfoRejectCache.set(new ConcurrentHashMap<>());
        }

        public static void exit() {
            userPubInfoRejectCache.remove();
            currentUser.remove();
        }
    }

    public static final ThreadLocal<ConcurrentHashMap<Long, UserPubInfo>> userPubInfoRejectCache = new ThreadLocal<>();

    public static final ThreadLocal<JwtUser> currentUser = new ThreadLocal<>();
}
