package com.dreamsoftware.tcs.web.security.config;

import com.dreamsoftware.tcs.config.properties.LdapProperties;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;

/**
 *
 * @author ssanchez
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class AdminsAuthenticationConfig {

    private final LdapProperties ldapProperties;

    /**
     *
     * @return
     */
    @Bean
    public LdapContextSource provideLdapContextSource() {
        LdapContextSource ldapContextSource = new LdapContextSource();
        ldapContextSource.setUrl(ldapProperties.getLdapUrl());
        ldapContextSource.setUserDn(ldapProperties.getLdapUsername());
        ldapContextSource.setPassword(ldapProperties.getLdapUserPassword());
        ldapContextSource.afterPropertiesSet();
        return ldapContextSource;
    }

    /**
     *
     * @return
     */
    @Bean
    public LdapTemplate provideLdapTemplate() {
        return new LdapTemplate(provideLdapContextSource());
    }

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
