package com.example.application.configuration;

import com.example.application.util.Jsons;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static com.fasterxml.jackson.databind.DeserializationFeature.*;

@Configuration
@Slf4j
public class JsonConfig {
    private static final AtomicReference<ObjectMapper> globalObjectMapper = new AtomicReference<>(null);

    public static ObjectMapper getGlobalObjectMapper() {
        while (true) {
            var m = globalObjectMapper.get();
            if (m == null) {
                log.warn("Get global object mapper before it init !");
            } else {
                return m;
            }
        }
    }

    @Bean("setupObjectMapper")
    Object setupObjectMapper(@Autowired
                             ObjectMapper objectMapper) {
        objectMapper.enable(
                FAIL_ON_NULL_FOR_PRIMITIVES,
                FAIL_ON_NUMBERS_FOR_ENUMS,
                FAIL_ON_READING_DUP_TREE_KEY,
                FAIL_ON_TRAILING_TOKENS
        );
        objectMapper.disable(
                FAIL_ON_IGNORED_PROPERTIES,
                FAIL_ON_UNKNOWN_PROPERTIES
        );
        objectMapper.registerModule(new ObjectIdModule());
        globalObjectMapper.set(objectMapper);
        log.debug("init ObjectMapper --- {}", objectMapper);
        return new Object();
    }

    public static class ObjectIdModule extends SimpleModule {
        public ObjectIdModule() {
            super("jvlang_mongodb_objectId",
                    Version.unknownVersion(),
                    Map.of(ObjectId.class, new ObjectIdDeserializer()),
                    List.of(new ObjectIdSerializer())
            );
        }

        public static class ObjectIdDeserializer extends JsonDeserializer<ObjectId> {
            @Override
            public @Nullable ObjectId deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
                var t = ctxt.readTree(p);
                return Jsons.deserializeObjectId(t);
            }
        }

        public static class ObjectIdSerializer extends JsonSerializer<ObjectId> {
            @Override
            public Class<ObjectId> handledType() {
                return ObjectId.class;
            }

            @Override
            public void serialize(ObjectId value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeString(value.toString());
            }
        }
    }


}
