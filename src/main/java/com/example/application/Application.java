package com.example.application;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * The entry point of the Spring Boot application.
 * <p>
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 */
@SpringBootApplication
@EnableMongoRepositories
@Theme(value = "jvlang-housekeeping")
@Slf4j
public class Application implements AppShellConfigurator {


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Value("${server.port}")
    Integer port;

    @PostConstruct
    void pc() {
        log.info("""
                |
                +-------------------------------------------+
                | Server start at :                         |
                | http://localhost:{}
                +-------------------------------------------+
                """, port);
    }
}
