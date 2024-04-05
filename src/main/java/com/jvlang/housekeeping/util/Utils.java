package com.jvlang.housekeeping.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.hilla.exception.EndpointException;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.core.env.Environment;
import org.springframework.lang.Nullable;

import java.io.InputStream;
import java.util.*;
import java.util.function.Supplier;

public final class Utils {
    private Utils() {
    }

    public static final class Coll {
        private Coll() {
        }

        @SafeVarargs
        public static <T> ArrayList<T> arrayList(T... items) {
            return new ArrayList<>(Arrays.stream(items).toList());
        }

        public static <K, V> Supplier<Map<K, V>> mapSupByKeyCmp(
                @org.springframework.lang.Nullable Comparator<K> keyCmp,
                boolean keyCanCmpWithoutComparator
        ) {
            return keyCanCmpWithoutComparator
                    ? TreeMap::new
                    : keyCmp != null
                    ? () -> new TreeMap<>(keyCmp)
                    : LinkedHashMap::new;
        }
    }

    public static final class Copy {
        private Copy() {
        }

        @SneakyThrows
        public static <T, R> R byJson(ObjectMapper objectMapper, T src, Class<R> clz) {
            return objectMapper.readValue(objectMapper.writeValueAsString(src), clz);
        }
    }

    public static final class Sync {
        private Sync() {
        }
    }

    public static final class ClassLoader {
        private ClassLoader() {
        }

        public static InputStream getResourceAsStream(@NonNull String name) {
            return Objects.requireNonNull(
                    Objects.requireNonNull(
                            Thread.currentThread().getContextClassLoader(),
                            "Current thread context class loader is null ( when get resource " + name + " as stream) !"
                    ).getResourceAsStream(name),
                    "Get resource " + name + " as stream return null !"
            );
        }
    }

    public static final class Http {
        private Http() {
        }

        public static String createResponseErrorObject(ObjectMapper om, String errorMessage) {
            ObjectNode objectNode = om.createObjectNode();
            objectNode.put(EndpointException.ERROR_MESSAGE_FIELD, errorMessage);
            return objectNode.toString();
        }
    }

    public static final class Env {
        private Env() {
        }

        public static boolean profileIs(Environment env, String... names) {
            var profiles = Arrays.asList(env.getActiveProfiles());
            return Arrays.stream(names).anyMatch(profiles::contains);
        }
    }

    @SuppressWarnings("SpellCheckingInspection")
    public static final class Throwables {
        private Throwables() {
        }

        public static <T> @Nullable T findAnyCauseInstanceOf(@Nullable Throwable err, Class<T> type) {
            if (err == null) return null;
            var clz = err.getClass();
            if (Objects.equals(type, clz) || type.isAssignableFrom(clz)) {
                return type.cast(err);
            }
            return findAnyCauseInstanceOf(err.getCause(), type);
        }

    }
}
