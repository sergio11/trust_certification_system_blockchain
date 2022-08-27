package com.dreamsoftware.tcs.services.impl;

import com.dreamsoftware.tcs.config.properties.StreamChannelsProperties;
import com.dreamsoftware.tcs.mapper.SignUpUserMapper;
import com.dreamsoftware.tcs.mapper.SimpleUserMapper;
import com.dreamsoftware.tcs.mapper.UserDetailsMapper;
import com.dreamsoftware.tcs.stream.events.OnNewUserRegistrationEvent;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserTypeEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.service.IWalletService;
import com.dreamsoftware.tcs.services.IAccountsService;
import com.dreamsoftware.tcs.services.IPasswordResetTokenService;
import com.dreamsoftware.tcs.web.dto.request.SignInUserDTO;
import com.dreamsoftware.tcs.web.dto.request.SignUpUserDTO;
import com.dreamsoftware.tcs.web.dto.response.AccessTokenDTO;
import com.dreamsoftware.tcs.web.dto.response.AuthenticationDTO;
import com.dreamsoftware.tcs.web.dto.response.SimpleUserDTO;
import com.dreamsoftware.tcs.web.security.userdetails.ICommonUserDetailsAware;
import com.dreamsoftware.tcs.web.security.utils.JwtTokenHelper;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.dreamsoftware.tcs.services.ISecurityTokenGeneratorService;
import com.dreamsoftware.tcs.stream.events.notifications.users.PasswordResetNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.users.UserPendingValidationNotificationEvent;
import com.dreamsoftware.tcs.web.dto.internal.PasswordResetTokenDTO;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author ssanchez
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AccountsServiceImpl implements IAccountsService {

    private final String AUTH_TYPE = "Bearer";

    private final AuthenticationManager authenticationManager;
    private final JwtTokenHelper jwtTokenHelper;
    private final UserRepository userRepository;
    private final UserDetailsMapper userDetailsMapper;
    private final SignUpUserMapper signUpUserMapper;
    private final SimpleUserMapper simpleUserMapper;
    private final ISecurityTokenGeneratorService tokenGeneratorService;
    private final IWalletService walletService;
    private final StreamBridge streamBridge;
    private final StreamChannelsProperties streamChannelsProperties;
    private final IPasswordResetTokenService passwordResetTokenService;

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

    /**
     *
     * @param token
     * @return
     */
    @Override
    public AuthenticationDTO refresh(String token) {
        Assert.notNull(token, "Token can not be null");
        Assert.hasLength(token, "Token can not be empty");

        return AuthenticationDTO.builder()
                .accessToken(jwtTokenHelper.refreshToken(token))
                .type(AUTH_TYPE)
                .build();
    }

    /**
     *
     * @param token
     */
    @Override
    public void restoreSecurityContextFor(String token) {
        Assert.notNull(token, "Token can not be null");
        try {
            if (jwtTokenHelper.validateToken(token)) {
                final ObjectId userId = new ObjectId(jwtTokenHelper.getSubFromToken(token));
                log.debug("Restore User Details into security context for user id -> " + userId);
                final ICommonUserDetailsAware<ObjectId> userDetails = userRepository.findById(userId)
                        .map(userEntity -> userDetailsMapper.entityToDTO(userEntity))
                        .orElse(null);
                if (userDetails != null) {
                    SecurityContextHolder.getContext().setAuthentication(
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
                }
            }
        } catch (final Exception ex) {
            log.debug("Fail to restore security context for token -> " + token);
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
        // Map to user entity
        final UserEntity userToSave = signUpUserMapper.signUpUserDTOToUserEntity(user);
        // Configure confirmation token
        final String confirmationToken = tokenGeneratorService.generateToken(userToSave.getName());
        userToSave.setConfirmationToken(confirmationToken);
        final UserEntity userEntitySaved = userRepository.save(userToSave);
        final SimpleUserDTO simpleUserDTO = simpleUserMapper.entityToDTO(userEntitySaved);
        if (simpleUserDTO.getState().equals(SimpleUserDTO.PENDING_ACTIVATE_STATE)) {
            streamBridge.send(streamChannelsProperties.getNotificationDeliveryRequest(), UserPendingValidationNotificationEvent.builder()
                    .userId(simpleUserDTO.getIdentity())
                    .build());
        }
        return simpleUserDTO;
    }

    /**
     * Activate Account
     *
     * @param confirmationToken
     * @return
     */
    @Override
    public SimpleUserDTO activate(String confirmationToken) throws Throwable {
        Assert.notNull(confirmationToken, "confirmation Token can not be null");
        Assert.hasLength(confirmationToken, "confirmation token can not be empty");
        final UserEntity userToActivate = userRepository.findOneByConfirmationToken(confirmationToken).orElseThrow(() -> {
            throw new IllegalStateException("User can not be found");
        });
        // Generate Wallet
        final String walletHash = walletService.generateWallet();
        log.debug("Wallet created with hash: " + walletHash);
        userToActivate.setState(UserStateEnum.PENDING_VALIDATE);
        userToActivate.setConfirmationToken(null);
        userToActivate.setWalletHash(walletHash);
        final UserEntity userActivated = userRepository.save(userToActivate);
        streamBridge.send(streamChannelsProperties.getNewUserRegistration(), OnNewUserRegistrationEvent.builder()
                .name(userActivated.getName())
                .userType(userActivated.getType())
                .walletHash(userActivated.getWalletHash())
                .build());
        return simpleUserMapper.entityToDTO(userActivated);
    }

    /**
     *
     * @param email
     */
    @Override
    public void resetPassword(final String email) {
        Assert.notNull(email, "Email can not be null");
        final PasswordResetTokenDTO resetPasswordToken = Optional.ofNullable(passwordResetTokenService.getPasswordResetTokenForUserWithEmail(email))
                .orElseGet(() -> passwordResetTokenService.createPasswordResetTokenForUserWithEmail(email));
        streamBridge.send(streamChannelsProperties.getNewUserRegistration(),
                PasswordResetNotificationEvent.builder()
                        .email(resetPasswordToken.getEmail())
                        .expiryDate(resetPasswordToken.getExpiryDate())
                        .id(resetPasswordToken.getId())
                        .locale(resetPasswordToken.getLocale())
                        .token(resetPasswordToken.getToken())
                        .name(resetPasswordToken.getName())
                        .build());
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
