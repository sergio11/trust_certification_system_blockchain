package com.dreamsoftware.tcs.web.security.config;

import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;

/**
 *
 * @author ssanchez
 */
@Configuration
@Slf4j
public class AdminsAuthenticationConfig {

    /**
     *
     * @param usersAuthenticationProvider
     * @return
     * @throws Exception
     */
    @Bean("adminAuthenticationManager")
    public AuthenticationManager provideAuthenticationManager(
            @Qualifier("adminAuthenticationProvider") AuthenticationProvider usersAuthenticationProvider
    ) throws Exception {
        return new ProviderManager(usersAuthenticationProvider);
    }

    @PostConstruct
    protected void init() {
        log.info("init AdminsAuthenticationConfig ...");
    }
}
