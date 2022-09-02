package com.dreamsoftware.tcs.web.security.config;

import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author ssanchez
 */
@Configuration
@Slf4j
public class CommonSecurityConfig {

    /**
     * Provide Password Encoder
     *
     * @return
     */
    @Bean
    public PasswordEncoder providePasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @PostConstruct
    protected void init() {
        log.info("init Common Security Config ...");
    }
}
