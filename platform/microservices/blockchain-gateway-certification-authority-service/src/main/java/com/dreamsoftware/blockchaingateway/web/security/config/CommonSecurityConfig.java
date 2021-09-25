package com.dreamsoftware.blockchaingateway.web.security.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author ssanchez
 */
@Configuration
public class CommonSecurityConfig {

    private Logger logger = LoggerFactory.getLogger(CommonSecurityConfig.class);

    @Bean
    public PasswordEncoder providePasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @PostConstruct
    protected void init() {
        logger.info("init Common Security Config ...");
    }
}
