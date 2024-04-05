package com.jvlang.housekeeping.pojo;

import com.jvlang.housekeeping.Application;
import com.jvlang.housekeeping.pojo.vo.UserPubInfo;
import com.jvlang.housekeeping.repo.UserRepository;
import com.jvlang.housekeeping.util.ThreadLocalUtils;
import com.jvlang.housekeeping.util.Tuple2;
import com.jvlang.housekeeping.util.Tuple3;
import com.jvlang.housekeeping.util.Utils;
import dev.hilla.Nonnull;
import dev.hilla.Nullable;
import jakarta.persistence.Transient;
import lombok.Lombok;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Computed {
    // 实现类需要重写一下才会被 typescript openapi 生成。

    @Transient
    default @Nonnull Map<@Nonnull Long, @Nonnull UserPubInfo> getUserPubInfos() {
        return Util.getUserPubInfo(this);
    }


    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD})
    @interface UserId {
    }

    @Slf4j
    final class Util {
        private Util() {
        }

        @SuppressWarnings("SameParameterValue")
        private static <V, @org.checkerframework.checker.nullness.qual.Nullable K> Map<K, V> getModelFromId(
                @NonNull ThreadLocal<ConcurrentHashMap<K, V>> businessThreadCache,
                @NonNull Stream<Field> fields,
                @Nullable Object that,
                @NonNull Class<@org.checkerframework.checker.nullness.qual.Nullable K> idType,
                @org.springframework.lang.Nullable Comparator<K> keyCmp,
                boolean keyCanCmpWithoutComparator,
                @NonNull Function<
                        List<K>,
                        Map<
                                @org.checkerframework.checker.nullness.qual.NonNull K,
                                @org.checkerframework.checker.nullness.qual.NonNull V
                                >
                        > needQueryOnCacheMissing
        ) {
            var cache = Optional.ofNullable(businessThreadCache.get())
                    .orElseGet(() -> {
                        log.warn("请先进入业务线程的作用域再使用业务相关的ThreadLocal！");
                        return new ConcurrentHashMap<>();
                    });
            var list = fields
                    .map(f -> {
                        f.setAccessible(true);
                        try {
                            return new Tuple2<>(
                                    idType.cast(f.get(that)),
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
                    }, Utils.Coll.mapSupByKeyCmp(keyCmp, keyCanCmpWithoutComparator)))
                    .entrySet()
                    .stream()
                    .map(e -> {
                        var id = idType.cast(e.getKey());
                        return new Tuple3<>(id, cache.getOrDefault(id, null), e.getValue());
                    })
                    .toList();
            var need_query_ids = list.stream().filter(it -> it.getB() == null).map(Tuple3::getA).toList();

            var queried = needQueryOnCacheMissing.apply(need_query_ids);
            return list.stream().map(it -> {
                var k = it.getA();
                var v = it.getB();
                if (v == null) {
                    var queried_model = queried.get(k);
                    cache.put(k, queried_model);
                    v = queried_model;
                }
                return Map.entry(it.getA(), v);
            }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> {
                throw new IllegalStateException(String.format(
                        "Duplicate key (attempted merging values %s and %s) , list is %s",
                        v1, v2, list));
            }, Utils.Coll.mapSupByKeyCmp(keyCmp, keyCanCmpWithoutComparator)));
        }

        public static @Nonnull Map<@Nonnull Long, @Nonnull UserPubInfo> getUserPubInfo(Object that) {
            return getModelFromId(
                    ThreadLocalUtils.userPubInfoRejectCache,
                    Arrays.stream(that.getClass().getDeclaredFields())
                            .filter(f -> !Modifier.isStatic(f.getModifiers()))
                            .filter(f -> f.getAnnotation(UserId.class) != null),
                    that,
                    Long.class,
                    null,
                    true,
                    uids -> Application.getContext()
                            .getBean(UserRepository.class)
                            .getPubInfos(uids)
                            .stream()
                            .collect(Collectors.toMap(UserPubInfo::getId, it -> it))
            );
        }
    }

}
