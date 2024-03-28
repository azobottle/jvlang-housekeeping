package com.jvlang.housekeeping.config;

import com.fasterxml.jackson.databind.*;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    @Autowired
    ObjectMapper objectMapper;

    @PostConstruct
    void postConstruct() {
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
        globalObjectMapper.set(objectMapper);
        log.debug("init ObjectMapper --- {}", objectMapper);
    }
}
