package com.dreamsoftware.tcs.config.properties;

import org.springframework.context.annotation.Configuration;
import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 *
 * @author ssanchez
 */
@Configuration
@Data
@RefreshScope
@Slf4j
public class LdapProperties {

    /**
     * Ldap URL
     */
    @Value("${ldap.url}")
    private String ldapUrl;

    /**
     * Ldap Base User Search
     */
    @Value("${ldap.base-user-search}")
    private String ldapBaseUserSearch;

    /**
     * Ldap Username
     */
    @Value("${ldap.username}")
    private String ldapUsername;

    /**
     * Ldap User Password
     */
    @Value("${ldap.password}")
    private String ldapUserPassword;

    @PostConstruct
    public void onPostConstruct() {
        log.debug("Ldap URL: " + ldapUrl);
        log.debug("Ldap Base User Search: " + ldapBaseUserSearch);
        log.debug("Ldap Usernanme: " + ldapUsername);
        log.debug("Ldap User Password: " + ldapUserPassword);
    }
}
