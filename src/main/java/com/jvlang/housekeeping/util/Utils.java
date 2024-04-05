package com.jvlang.housekeeping.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jvlang.housekeeping.pojo.AbstractEntity;
import dev.hilla.exception.EndpointException;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
}
