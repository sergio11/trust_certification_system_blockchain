package com.dreamsoftware.blockchaingateway.services.impl;

import com.dreamsoftware.blockchaingateway.mapper.SignUpUserMapper;
import com.dreamsoftware.blockchaingateway.mapper.SimpleUserMapper;
import com.dreamsoftware.blockchaingateway.mapper.UserDetailsMapper;
import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.blockchaingateway.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.blockchaingateway.services.IAccountsService;
import com.dreamsoftware.blockchaingateway.web.dto.request.SignInUserDTO;
import com.dreamsoftware.blockchaingateway.web.dto.request.SignUpUserDTO;
import com.dreamsoftware.blockchaingateway.web.dto.response.AccessTokenDTO;
import com.dreamsoftware.blockchaingateway.web.dto.response.AuthenticationDTO;
import com.dreamsoftware.blockchaingateway.web.dto.response.SimpleUserDTO;
import com.dreamsoftware.blockchaingateway.web.security.userdetails.ICommonUserDetailsAware;
import com.dreamsoftware.blockchaingateway.web.security.utils.JwtTokenHelper;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 *
 * @author ssanchez
 */
@Service
@RequiredArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private static final Logger logger = LoggerFactory.getLogger(AccountsServiceImpl.class);

    private final String AUTH_TYPE = "Bearer";

    private final AuthenticationManager authenticationManager;
    private final JwtTokenHelper jwtTokenHelper;
    private final UserRepository userRepository;
    private final UserDetailsMapper userDetailsMapper;
    private final SignUpUserMapper signUpUserMapper;
    private final SimpleUserMapper simpleUserMapper;

    /**
     *
     * @param dto
     * @param device
     * @return
     */
    @Override
    public AuthenticationDTO signin(SignInUserDTO dto, Device device) {
        Assert.notNull(dto, "Auth Request DTO can not be null");
        Assert.notNull(device, "Device can not be null");

        final Authentication authRequest = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());

        // Get User details for authentication request
        final ICommonUserDetailsAware<ObjectId> userDetails = getUserDetails(authRequest);

        // Generate Access Token
        final AccessTokenDTO accessTokenDTO = jwtTokenHelper.generateToken(userDetails, device);

        // Generate and return Authentication Response
        return AuthenticationDTO.builder()
                .accessToken(accessTokenDTO)
                .type(AUTH_TYPE)
                .build();
    }

    @Override
    public AuthenticationDTO refresh(String token) {
        Assert.notNull(token, "Token can not be null");
        Assert.hasLength(token, "Token can not be empty");

        return AuthenticationDTO.builder()
                .accessToken(jwtTokenHelper.refreshToken(token))
                .type(AUTH_TYPE)
                .build();
    }

    @Override
    @Transactional
    public void restoreSecurityContextFor(String token) {
        Assert.notNull(token, "Token can not be null");
        try {
            if (jwtTokenHelper.validateToken(token)) {
                final ObjectId userId = new ObjectId(jwtTokenHelper.getSubFromToken(token));
                logger.debug("Restore User Details into security context for user id -> " + userId);
                final ICommonUserDetailsAware<ObjectId> userDetails = userRepository.findById(userId)
                        .map(userEntity -> userDetailsMapper.entityToDTO(userEntity))
                        .orElse(null);
                if (userDetails != null) {
                    SecurityContextHolder.getContext().setAuthentication(
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
                }
            }
        } catch (final Exception ex) {
            logger.debug("Fail to restore security context for token -> " + token);
        }
    }

    /**
     *
     * @param user
     * @return
     */
    @Override
    public SimpleUserDTO signup(SignUpUserDTO user) {
        Assert.notNull(user, "user can not be null");
        final UserEntity userEntitySaved = userRepository.save(
                signUpUserMapper.signUpUserDTOToUserEntity(user));
        return simpleUserMapper.entityToDTO(userEntitySaved);
    }

    /**
     * Get User Details
     *
     * @param authRequest
     * @return
     */
    private ICommonUserDetailsAware<ObjectId> getUserDetails(final Authentication authRequest) {
        Assert.notNull(authRequest, "Auth Request can not be null");
        final Authentication authentication = authenticationManager.authenticate(authRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return (ICommonUserDetailsAware<ObjectId>) authentication.getPrincipal();
    }

}
