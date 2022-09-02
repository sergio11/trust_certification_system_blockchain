package com.dreamsoftware.tcs.web.security.provider;

import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.stereotype.Component;

/**
 * Implementation of UserDetailsContextMapper strategy, which is responsible for
 * mapping user objects to and from LDAP context data:
 *
 * @author ssanchez
 */
@Component
@Slf4j
public class LdapUserDetailsService implements UserDetailsContextMapper {

    @Override
    public UserDetails mapUserFromContext(DirContextOperations dco, String username, Collection<? extends GrantedAuthority> clctn) {

        log.info("mapUserFromContext: " + username);

        return null;
    }

    @Override
    public void mapUserToContext(UserDetails ud, DirContextAdapter dca) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
