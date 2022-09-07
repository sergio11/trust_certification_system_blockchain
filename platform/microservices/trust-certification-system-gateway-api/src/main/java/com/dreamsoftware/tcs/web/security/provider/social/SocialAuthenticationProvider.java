package com.dreamsoftware.tcs.web.security.provider.social;

import com.dreamsoftware.tcs.mapper.UserDetailsMapper;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 *
 * @author ssanchez
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SocialAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final UserDetailsMapper userDetailsMapper;

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final String socialProviderKey = authentication.getCredentials().toString();
        log.debug("Auth for social provider key -> " + socialProviderKey);

        return userRepository.findOneByAuthProviderKey(socialProviderKey)
                .map(userEntity -> new SocialProviderAuthenticationToken(userDetailsMapper.entityToDTO(userEntity), socialProviderKey))
                .orElseThrow(() -> new BadCredentialsException("Social Authentication fail for key -> " + socialProviderKey));

    }

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(SocialProviderAuthenticationToken.class);
    }
}
