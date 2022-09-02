package com.dreamsoftware.tcs.web.security.provider;

import com.dreamsoftware.tcs.web.security.userdetails.impl.UserDetailsImpl;
import com.paypal.orders.Name;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
public class AdminProviderImpl implements UserDetailsContextMapper {

    @Override
    public UserDetails mapUserFromContext(DirContextOperations dco, String username, Collection<? extends GrantedAuthority> clctn) {

        log.info("mapUserFromContext: " + username);

        Set<GrantedAuthority> grantedAuthorities = clctn.stream().map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toSet());

        Object o = dco.getObjectAttribute("userPassword");
        byte[] bytes = (byte[]) o;
        String hashPassword = new String(bytes);

        log.debug("Hash Password: " + hashPassword);

        /*return new UserDetailsImpl<Name>(
                dco.getDn(),
                dco.getStringAttribute("uid"),
                hashPassword,
                dco.getStringAttribute("cn"),
                dco.getStringAttribute("sn"),
                dco.getStringAttribute("mail"),
                grantedAuthorities
        );*/
        return null;
    }

    @Override
    public void mapUserToContext(UserDetails ud, DirContextAdapter dca) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
