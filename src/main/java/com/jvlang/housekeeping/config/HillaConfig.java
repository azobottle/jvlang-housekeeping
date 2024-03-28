package com.jvlang.housekeeping.config;

import dev.hilla.auth.CsrfChecker;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HillaConfig {
    @Autowired
    CsrfChecker csrfChecker;

    @PostConstruct
    void postConstruct() {
        csrfChecker.setCsrfProtection(false);
    }
}
