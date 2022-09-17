package com.dreamsoftware.tcs.web.security.provider.admin;

import com.dreamsoftware.tcs.mapper.UserLdapAccountMapper;
import com.dreamsoftware.tcs.persistence.ldap.repository.IUserLdapRepository;
import com.dreamsoftware.tcs.web.security.userdetails.ICommonUserDetailsAware;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final IUserLdapRepository userLdapRepository;
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
        boolean authenticate = userLdapRepository.authenticate(authentication.getName(),
                authentication.getCredentials().toString());
        if (!authenticate) {
            throw new BadCredentialsException("Ldap Authentication Failed");
        }
        log.debug("AdminProviderImpl - authenticate succesfully!");
        final ICommonUserDetailsAware<String> userDetails = userLdapRepository.findOneByUid(authentication.getName())
                .map(userEntity -> userLdapAccountMapper.entityToDTO(userEntity))
                .orElse(null);
        if (userDetails == null) {
            throw new BadCredentialsException("Ldap Authentication Failed");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,
                authentication.getCredentials().toString(), userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
