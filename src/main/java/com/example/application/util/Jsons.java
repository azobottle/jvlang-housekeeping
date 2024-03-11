package com.example.application.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.bson.types.ObjectId;
import org.springframework.lang.Nullable;

public final class Jsons {
    private Jsons() {
    }

    @Nullable
    public static ObjectId deserializeObjectId(JsonNode t) {
        if (t.isTextual()) {
            return new ObjectId(t.textValue());
        } else if (t.isObject()) {
            var oid = t.get("$oid");
            if (oid != null && oid.isTextual()) {
                return new ObjectId(oid.textValue());
            } else {
                throw new IllegalArgumentException("invalid object type : " + t);
            }
        } else if (t.isNull()) {
            return null;
        } else {
            throw new IllegalArgumentException("invalid type : " + t);
        }
    }

    @SneakyThrows
    public static <T, R> R beanCopy(ObjectMapper objectMapper, T src, Class<R> clz) {
        return objectMapper.readValue(objectMapper.writeValueAsString(src), clz);
    }
}
