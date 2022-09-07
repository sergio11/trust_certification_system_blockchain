package com.dreamsoftware.tcs.web.security.provider.common;

import com.dreamsoftware.tcs.mapper.UserDetailsMapper;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author ssanchez
 */
@Service("platformUserProvider")
@RequiredArgsConstructor
@Slf4j
public class PlatformUserProviderImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserDetailsMapper userDetailsMapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.debug("UserDetailsServiceImpl CALLED loadUserBy {}", email);
        return userRepository.findOneByEmail(email)
                .map(userEntity -> userDetailsMapper.entityToDTO(userEntity))
                .orElseThrow(() -> new BadCredentialsException("User " + email + " was not found in the "
                + "database"));
    }
}
