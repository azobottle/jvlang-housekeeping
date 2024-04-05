package com.jvlang.housekeeping;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.lang.NonNull;

/**
 * The entry point of the Spring Boot application.
 * <p>
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 */
@SpringBootApplication
@Theme(value = "jvlang-housekeeping")
@Slf4j
public class Application implements AppShellConfigurator {
    @Nullable
    private static volatile ConfigurableApplicationContext ctx;

    @SneakyThrows
    @NonNull
    public static ConfigurableApplicationContext getContext() {
        while (ctx == null) {
            //noinspection BusyWait
            Thread.sleep(1000);
            log.warn("Thread sleep 1000 for waiting get application context .");
        }
        return ctx;
    }

    public static void main(String[] args) {
        log.info("\n" + """
                        +----------------------------------------------------
                        | Environments :
                        | {}
                        | DEV_MYSQL_ADDR={}
                        |
                        | Properties :
                        | {}
                        +----------------------------------------------------
                        """,
                System.getenv(),
                System.getenv("DEV_MYSQL_ADDR"),
                System.getProperties()
        );
        ctx = SpringApplication.run(Application.class, args);
    }


    @Value("${spring.datasource.url}")
    String datasourceUrl;

    @Value("${server.port}")
    String serverPort;

    @PostConstruct
    void postConstruct() {
        log.info('\n' + """
                        +---------------------------------------------------------------------+
                        | Show Config Info
                        | ---
                        |
                        | spring.datasource.url : {}
                        | server.port           : {}
                        +---------------------------------------------------------------------+
                        """,
                datasourceUrl, serverPort);
    }
}
