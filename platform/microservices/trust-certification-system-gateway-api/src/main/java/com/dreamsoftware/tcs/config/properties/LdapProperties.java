package com.dreamsoftware.tcs.config.properties;

import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

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
     * LDAP Server URI
     */
    @Value("${ldap.uri}")
    private String uri;

    /**
     * LDAP Admin DN
     */
    @Value("${ldap.admin-dn}")
    private String adminDn;

    /**
     * LDAP Admin Password
     */
    @Value("${ldap.admin-password}")
    private String adminPassword;

    /**
     * LDAP Search Base
     */
    @Value("${ldap.search-base}")
    private String searchBase; //ou=organization;

    /**
     * LDAP Search Filter
     */
    @Value("${ldap.search-filter}")
    private String searchFilter; //uid={0}

    /**
     * LDAP Group Search Base
     */
    @Value("${ldap.group-search-base}")
    private String groupSearchBase; //ou=groups

    /**
     * LDAP Group Search Filter
     */
    @Value("${ldap.group-search-filter}")
    private String groupSearchFilter; //memberUid={1}

    /**
     * LDAP Group Role Attribute
     */
    @Value("${ldap.group-role-attribute}")
    private String groupRoleAttribute; //cn

    @PostConstruct
    public void onPostConstruct() {
        log.debug("LdapProperties uri: " + uri);
        log.debug("LdapProperties adminDn: " + adminDn);
        log.debug("LdapProperties adminPassword: " + adminPassword);
        log.debug("LdapProperties searchBase: " + searchBase);
        log.debug("LdapProperties searchFilter: " + searchFilter);
        log.debug("LdapProperties groupSearchBase: " + groupSearchBase);
        log.debug("LdapProperties groupSearchFilter: " + groupSearchFilter);
        log.debug("LdapProperties groupRoleAttribute: " + groupRoleAttribute);
    }
}
