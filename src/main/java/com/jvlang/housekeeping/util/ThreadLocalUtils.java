package com.jvlang.housekeeping.util;

import com.jvlang.housekeeping.pojo.JwtUser;
import com.jvlang.housekeeping.pojo.vo.UserPubInfo;
import org.springframework.lang.Nullable;

import java.util.concurrent.ConcurrentHashMap;

public final class ThreadLocalUtils {
    private ThreadLocalUtils() {
    }

    /**
     * 这玩意是对业务线程的ThreadLocal作用域的抽象。
     * <p/>
     * 当在请求线程中用到多线程时，要注意这些东西的作用域。
     */
    public static final class BusinessThreadScope {
        private BusinessThreadScope() {
        }

        public static void assertEnteredBusinessThreadScope() {
            var v = isBusinessThreadScope.get();
            if (v == null || !v) {
                throw new IllegalStateException("系统错误，未进入 BusinessThreadScope 而调用其 ThreadLocal 变量。");
            }
        }

        public static final ThreadLocal<Boolean> isBusinessThreadScope = ThreadLocal.withInitial(() -> false);

        public static final ThreadLocal<ConcurrentHashMap<Long, UserPubInfo>> userPubInfoCache = new ThreadLocal<>();

        public static final ThreadLocal<@org.checkerframework.checker.nullness.qual.Nullable JwtUser> currentUser = new ThreadLocal<>();

        public static void enter(@Nullable JwtUser u, @Nullable ConcurrentHashMap<Long, UserPubInfo> m) {
            isBusinessThreadScope.set(true);
            currentUser.set(u);
            userPubInfoCache.set(m == null ? new ConcurrentHashMap<>() : m);
        }

        public static void exit() {
            isBusinessThreadScope.set(false);
            userPubInfoCache.remove();
            currentUser.remove();
        }
    }
}
