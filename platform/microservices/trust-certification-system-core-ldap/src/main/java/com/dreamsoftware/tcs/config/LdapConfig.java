package com.dreamsoftware.tcs.config;

import com.dreamsoftware.tcs.config.properties.LdapProperties;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

/**
 *
 * @author ssanchez
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class LdapConfig {

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

    @PostConstruct
    protected void init() {
        log.debug("init LdapConfig ...");
    }
}
