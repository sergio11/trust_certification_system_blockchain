package com.dreamsoftware.tcs.web.security.provider;

import com.dreamsoftware.tcs.web.security.userdetails.impl.UserDetailsImpl;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
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
public class AdminProviderImpl extends LdapUserDetailsMapper implements UserDetailsContextMapper {

    @Override
    public UserDetails mapUserFromContext(DirContextOperations dco, String username, Collection<? extends GrantedAuthority> clctn) {
        Object o = dco.getObjectAttribute("userPassword");
        byte[] bytes = (byte[]) o;
        String hashPassword = new String(bytes);
        final Set<GrantedAuthority> grantedAuthorities = clctn.stream().map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toSet());
        return UserDetailsImpl.<String>builder()
                .id(dco.getStringAttribute("uid"))
                .email(dco.getStringAttribute("mail"))
                .password(hashPassword)
                .name(dco.getStringAttribute("cn"))
                .surname(dco.getStringAttribute("sn"))
                .grantedAuthorities(grantedAuthorities)
                .build();

    }

    @Override
    public void mapUserToContext(UserDetails ud, DirContextAdapter dca) {
    }
}
