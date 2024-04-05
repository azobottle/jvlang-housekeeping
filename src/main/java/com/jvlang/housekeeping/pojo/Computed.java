package com.jvlang.housekeeping.pojo;

import com.jvlang.housekeeping.Application;
import com.jvlang.housekeeping.pojo.vo.UserPubInfo;
import com.jvlang.housekeeping.repo.UserRepository;
import com.jvlang.housekeeping.util.ThreadLocalUtils;
import com.jvlang.housekeeping.util.Tuple2;
import com.jvlang.housekeeping.util.Tuple3;
import com.jvlang.housekeeping.util.Utils;
import dev.hilla.Nonnull;
import jakarta.persistence.Transient;
import lombok.Lombok;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public interface Computed {
    @Transient
    default @Nonnull Map<@Nonnull Long, @Nonnull UserPubInfo> getUserPubInfos() {
        return autoFillUserPubInfo(this);
    }


    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD})
    @interface UserId {
    }

    Logger log = LoggerFactory.getLogger(Computed.class);

    static @Nonnull Map<@Nonnull Long, @Nonnull UserPubInfo> autoFillUserPubInfo(Object that) {
        var cache = Optional.ofNullable(ThreadLocalUtils.userPubInfoRejectCache.get())
                .orElseGet(() -> {
                    log.warn("请先进入业务线程的作用域再使用业务相关的ThreadLocal！");
                    return new ConcurrentHashMap<>();
                });
        var list = Arrays.stream(that.getClass().getDeclaredFields())
                .filter(f -> !Modifier.isStatic(f.getModifiers()))
                .filter(f -> f.getAnnotation(UserId.class) != null)
                .map(f -> {
                    f.setAccessible(true);
                    try {
                        return new Tuple2<>(
                                (@org.checkerframework.checker.nullness.qual.Nullable Long) f.get(that),
                                new Tuple2<>(that, Utils.Coll.arrayList(f))
                        );
                    } catch (IllegalAccessException e) {
                        throw Lombok.sneakyThrow(e);
                    }
                })
                .filter(it -> it.getA() != null)
                .collect(Collectors.toMap(Tuple2::getA, Tuple2::getB, (v1, v2) -> {
                    v1.getB().addAll(v2.getB());
                    return v1;
                }, TreeMap::new))
                .entrySet()
                .stream()
                .map(e -> {
                    var uid = e.getKey();
                    //noinspection RedundantCast
                    return new Tuple3<>((Long) uid, cache.getOrDefault(uid, null), e.getValue());
                })
                .toList();
        var need_query_uids = list.stream().filter(it -> it.getB() == null).map(Tuple3::getA).toList();
        var pubInfos = Application.getContext()
                .getBean(UserRepository.class)
                .getPubInfos(need_query_uids)
                .stream()
                .collect(Collectors.toMap(UserPubInfo::getId, it -> it));
        log.debug("query user public info , uid list were {} , result is {}", need_query_uids, pubInfos);
        return list.stream().map(it -> {
            var uid = it.getA();
            if (it.getB() == null) {
                var info = pubInfos.get(uid);
                cache.put(uid, info);
                it.setB(info);
            }
            return Map.entry(it.getA(), it.getB());
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
