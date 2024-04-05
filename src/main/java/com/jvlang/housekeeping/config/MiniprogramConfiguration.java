package com.jvlang.housekeeping.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "miniprogram")
@Slf4j
@Data
public class MiniprogramConfiguration {
    String appId;
    String appSecret;

    @PostConstruct
    void pc() {
        log.debug("Miniprogram config : {}", this);
    }
}
