package com.jvlang.housekeeping.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jvlang.housekeeping.pojo.AbstractEntity;
import lombok.SneakyThrows;

public final class Utils {
    private Utils() {
    }

    public static class Copy {
        private Copy() {
        }

        @SneakyThrows
        public static <T, R> R byJson(ObjectMapper objectMapper, T src, Class<R> clz) {
            return objectMapper.readValue(objectMapper.writeValueAsString(src), clz);
        }
    }
}
