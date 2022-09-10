package com.dreamsoftware.tcs.web.security.config;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author ssanchez
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class UsersAuthenticationConfig {

    private final PasswordEncoder passwordEncoder;

    /**
     * Provide Common Authentication Provider
     *
     * @param userDetails
     * @return
     */
    @Bean("usersAuthenticationProvider")
    public AuthenticationProvider provideCommonAuthenticationProvider(
            @Qualifier("platformUserProvider") UserDetailsService userDetails) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetails);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    /**
     * Provide Users Authentication Manager
     *
     * @param usersAuthenticationProvider
     * @param socialAuthenticationProvider
     * @return
     * @throws Exception
     */
    @Bean("usersAuthenticationManager")
    public AuthenticationManager provideAuthenticationManager(
            @Qualifier("usersAuthenticationProvider") AuthenticationProvider usersAuthenticationProvider,
            @Qualifier("socialAuthenticationProvider") AuthenticationProvider socialAuthenticationProvider
    ) throws Exception {
        return new ProviderManager(usersAuthenticationProvider, socialAuthenticationProvider);
    }

    /**
     * SocialAuthenticationProvider
     */
    @PostConstruct
    protected void init() {
        log.info("init UsersAuthenticationConfig ...");
    }

}
