package com.dreamsoftware.blockchaingateway.web.security.config;

import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 *
 * @author ssanchez
 */
@Configuration
public class AuthenticationProvidersConfig {

    private Logger logger = LoggerFactory.getLogger(AuthenticationProvidersConfig.class);

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationProvidersConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Provide Common Authentication Provider
     *
     * @param userDetails
     * @return
     */
    @Bean
    public AuthenticationProvider provideCommonAuthenticationProvider(
            @Qualifier("UserDetailsService") UserDetailsService userDetails) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetails);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

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
        logger.info("Init Database Authentication Config ...");
    }
}
