package com.example.application.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.fasterxml.jackson.databind.DeserializationFeature.*;

@Configuration
@Slf4j
public class JsonConfig {

    @Autowired
    ObjectMapper objectMapper;

    @PostConstruct
    void setupObjectMapper() {
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
        log.debug("init ObjectMapper --- {}", objectMapper);
    }
}
