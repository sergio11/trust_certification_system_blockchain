package com.dreamsoftware.tcs.web.security.config;

import java.util.List;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;

/**
 *
 * @author ssanchez
 */
@Configuration
@Slf4j
@Import(value = {AdminsAuthenticationConfig.class, UsersAuthenticationConfig.class})
public class AuthenticationProvidersConfig {

    /**
     * Provide Authentication Manager
     *
     * @param authenticationProviderList
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager provideAuthenticationManager(
            final List<AuthenticationProvider> authenticationProviderList) throws Exception {
        return new ProviderManager(authenticationProviderList);
    }

    @PostConstruct
    public void init() {
        log.info("Init AuthenticationProvidersConfig ...");
    }
}
