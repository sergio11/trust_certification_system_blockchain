package com.dreamsoftware.tcs.web.security.config;

import com.dreamsoftware.tcs.config.properties.LdapProperties;
import com.dreamsoftware.tcs.web.security.provider.AdminProviderImpl;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.DirContextAuthenticationStrategy;
import org.springframework.ldap.core.support.ExternalTlsDirContextAuthenticationStrategy;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.authentication.LdapAuthenticator;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.search.LdapUserSearch;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.util.Assert;

/**
 * @author ssanchez
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class AdminsAuthenticationConfig {

    private final LdapProperties ldapProperties;
    private final AdminProviderImpl userDetailsService;

    /**
     * Provide Authentication Strategy
     *
     * @return
     */
    @Bean
    public DirContextAuthenticationStrategy provideAuthenticationStrategy() {
        final ExternalTlsDirContextAuthenticationStrategy authStra = new ExternalTlsDirContextAuthenticationStrategy();
        authStra.setShutdownTlsGracefully(true);
        return authStra;
    }

    /**
     *
     * @param authenticationStrategy
     * @return
     */
    @Bean
    public LdapContextSource provideLdapContextSource(final DirContextAuthenticationStrategy authenticationStrategy) {
        Assert.notNull(authenticationStrategy, "DirContextAuthenticationStrategy must be provided");
        final DefaultSpringSecurityContextSource contextSource = new DefaultSpringSecurityContextSource(ldapProperties.getUri());
        contextSource.setUserDn(ldapProperties.getAdminDn());
        log.debug("Admin DN for DefaultSpringSecurityContextSource " + ldapProperties.getAdminDn());
        contextSource.setPassword(ldapProperties.getAdminPassword());
        log.debug("Admin Password DN for DefaultSpringSecurityContextSource " + ldapProperties.getAdminPassword());
        contextSource.setAuthenticationStrategy(authenticationStrategy);
        contextSource.afterPropertiesSet();
        return contextSource;
    }

    /**
     * LdapUserSearch implementation which uses an Ldap filter to locate the
     * user.
     *
     * @param contextSource
     * @return
     */
    @Bean
    public LdapUserSearch provideLdapUserSearch(final LdapContextSource contextSource) {
        Assert.notNull(contextSource, "LdapContextSource must be provided");
        return new FilterBasedLdapUserSearch(ldapProperties.getSearchBase(), ldapProperties.getSearchFilter(), contextSource);
    }

    /**
     * This interface is responsible for performing the user authentication and
     * retrieving the user's information from the directory
     *
     * @param contextSource
     * @param ldapUserSearch
     * @return
     */
    @Bean
    public LdapAuthenticator provideLdapAuthenticator(final LdapContextSource contextSource, final LdapUserSearch ldapUserSearch) {
        Assert.notNull(contextSource, "LdapContextSource must be provided");
        Assert.notNull(ldapUserSearch, "LdapUserSearch must be provided");
        final BindAuthenticator ldapAuthenticator = new BindAuthenticator(contextSource);
        ldapAuthenticator.setUserSearch(ldapUserSearch);
        return ldapAuthenticator;
    }

    ;

    /**
     * Once the user has been authenticated,
     * this interface is called to obtain the set of granted authorities for the user
     * @param contextSource
     * @return
     */
    @Bean
    public LdapAuthoritiesPopulator provideLdapAuthoritiesPopulator(final LdapContextSource contextSource) {
        Assert.notNull(contextSource, "LdapContextSource must be provided");
        final DefaultLdapAuthoritiesPopulator ldapAuthoritiesPopulator = new DefaultLdapAuthoritiesPopulator(contextSource, ldapProperties.getGroupSearchBase());
        ldapAuthoritiesPopulator.setGroupSearchFilter(ldapProperties.getGroupSearchFilter());
        ldapAuthoritiesPopulator.setGroupRoleAttribute(ldapProperties.getGroupRoleAttribute());
        ldapAuthoritiesPopulator.setSearchSubtree(true);
        ldapAuthoritiesPopulator.setConvertToUpperCase(true);
        return ldapAuthoritiesPopulator;
    }

    /**
     * An AuthenticationProvider implementation that authenticates against an
     * LDAP server.
     *
     * @param ldapAuthenticator
     * @param ldapAuthoritiesPopulator
     * @return
     */
    @Bean
    public AuthenticationProvider provideAuthenticationProvider(final LdapAuthenticator ldapAuthenticator, final LdapAuthoritiesPopulator ldapAuthoritiesPopulator) {
        Assert.state(ldapAuthenticator != null, "LdapAuthenticator must be provided");
        Assert.state(ldapAuthoritiesPopulator != null, "LdapAuthoritiesPopulator must be provided");
        final LdapAuthenticationProvider authenticationProvider = new LdapAuthenticationProvider(ldapAuthenticator, ldapAuthoritiesPopulator);
        authenticationProvider.setUserDetailsContextMapper(userDetailsService);
        return authenticationProvider;
    }

    @PostConstruct
    protected void init() {
        log.info("init AdminsAuthenticationConfig ...");
    }
}
