package com.dreamsoftware.tcs.web.security.provider.admin;

import com.dreamsoftware.tcs.config.properties.LdapProperties;
import com.dreamsoftware.tcs.mapper.UserLdapAccountMapper;
import com.dreamsoftware.tcs.web.security.model.UserLdapAccount;
import com.dreamsoftware.tcs.web.security.userdetails.ICommonUserDetailsAware;
import java.util.List;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 *
 * @author ssanchez
 */
@Component("adminAuthenticationProvider")
@RequiredArgsConstructor
@Slf4j
public class AdminProviderImpl implements AuthenticationProvider {

    private final LdapTemplate ldapTemplate;
    private final LdapProperties ldapProperties;
    private final UserLdapAccountMapper userLdapAccountMapper;

    /**
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        log.debug("AdminProviderImpl - authenticate for: " + authentication.getName());
        boolean authenticate = ldapTemplate.authenticate(ldapProperties.getLdapBaseUserSearch(), new EqualsFilter("uid", authentication.getName()).encode(),
                authentication.getCredentials().toString());
        if (!authenticate) {
            throw new BadCredentialsException("Ldap Authentication Failed");
        }
        log.debug("AdminProviderImpl - authenticate succesfully!");
        final ICommonUserDetailsAware<String> userDetails = userLdapAccountMapper.entityToDTO(findAccountByUid(authentication.getName()));
        return new UsernamePasswordAuthenticationToken(userDetails,
                authentication.getCredentials().toString(), userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    /**
     *
     * @param uid
     * @return
     */
    private UserLdapAccount findAccountByUid(final String uid) {
        log.debug("AdminProviderImpl - findAccountByUid uid: " + uid);
        final List<UserLdapAccount> users = ldapTemplate.search(ldapProperties.getLdapBaseUserSearch(), new EqualsFilter("uid", uid).encode(), new UserAccountAttributesMapper());

        if (users.isEmpty()) {
            throw new IllegalArgumentException(uid + " can not be found!");
        }
        return users.get(0);
    }

    private class UserAccountAttributesMapper implements AttributesMapper {

        @Override
        public Object mapFromAttributes(Attributes attributes) throws NamingException {
            attributes.getIDs().asIterator().forEachRemaining((entryId -> {
                log.debug("AdminProviderImpl - entryId -> " + entryId);
            }));
            final UserLdapAccount userAccount = new UserLdapAccount();
            Attribute attribute = attributes.get("uid");
            if (attribute != null) {
                userAccount.setId((String) attribute.get());
            }
            attribute = attributes.get("mail");
            if (attribute != null) {
                userAccount.setEmail((String) attribute.get());
            }
            return userAccount;
        }
    }
}
