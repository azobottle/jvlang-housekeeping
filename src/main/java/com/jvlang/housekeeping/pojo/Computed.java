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
                                    new Tuple2<>(that, Utils.Coll.arrayListOf(f))
                            );
                        } catch (IllegalAccessException e) {
                            throw Lombok.sneakyThrow(e);
                        }
                    })
                    .filter(it -> it.getV1() != null)
                    .collect(Collectors.toMap(Tuple2::getV1, Tuple2::getV2, (v1, v2) -> {
                        v1.getV2().addAll(v2.getV2());
                        return v1;
                    }, Utils.Coll.mapSupByKeyCmp(keyCmp, keyCanCmpWithoutComparator)))
                    .entrySet()
                    .stream()
                    .map(e -> {
                        K id = idType.cast(e.getKey());
                        return new Tuple3<>(id, cache.getOrDefault(id, null), e.getValue());
                    })
                    .toList();
            var need_query_ids = list.stream().filter(it -> it.getV2() == null).map(Tuple3::getV1).toList();
            var queried = needQueryOnCacheMissing.apply(need_query_ids);
            log.debug("after computed user public info ids : need_query_ids={} , list={} queried={}",
                    need_query_ids, list, queried);
            return list
                    .stream()
                    .map(it -> {
                        var k = Objects.requireNonNull(it.getV1(), "Why it null ?");
                        var v = it.getV2();
                        if (v == null) {
                            var queried_model = queried.get(k);
                            if (queried_model != null) {
                                cache.put(k, queried_model);
                                v = queried_model;
                            }
                        }
                        return new Tuple2<>(it.getV1(), v);
                    })
                    .filter(it -> it.getV2() != null)
                    .collect(Collectors.toMap(Tuple2::getV1, Tuple2::getV2, (v1, v2) -> {
                        throw new IllegalStateException(String.format(
                                "Duplicate key (attempted merging values %s and %s) , list is %s",
                                v1, v2, list));
                    }, Utils.Coll.mapSupByKeyCmp(keyCmp, keyCanCmpWithoutComparator)));
        }

        public static @Nonnull Map<@Nonnull Long, @Nonnull UserPubInfo> getUserPubInfo(Object that) {
            ThreadLocalUtils.BusinessThreadScope.assertEnteredBusinessThreadScope();
            return getModelFromId(
                    ThreadLocalUtils.BusinessThreadScope.userPubInfoCache,
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
