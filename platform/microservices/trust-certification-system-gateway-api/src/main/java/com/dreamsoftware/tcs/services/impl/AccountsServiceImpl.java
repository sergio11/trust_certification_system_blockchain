package com.dreamsoftware.tcs.services.impl;

import com.dreamsoftware.tcs.config.properties.StreamChannelsProperties;
import com.dreamsoftware.tcs.mapper.*;
import com.dreamsoftware.tcs.persistence.ldap.repository.IUserLdapRepository;
import com.dreamsoftware.tcs.persistence.nosql.entity.*;
import com.dreamsoftware.tcs.persistence.nosql.repository.AuthenticationProviderRepository;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserLoginRepository;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.service.IWalletService;
import com.dreamsoftware.tcs.services.IAccountsService;
import com.dreamsoftware.tcs.services.IFacebookService;
import com.dreamsoftware.tcs.services.IGoogleService;
import com.dreamsoftware.tcs.services.IUploadImagesService;
import com.dreamsoftware.tcs.stream.events.notifications.users.UserPendingValidationNotificationEvent;
import com.dreamsoftware.tcs.stream.events.user.NewCertificationAuthorityEvent;
import com.dreamsoftware.tcs.stream.events.user.NewStudentEvent;
import com.dreamsoftware.tcs.stream.events.user.UserPasswordResetEvent;
import com.dreamsoftware.tcs.web.dto.request.*;
import com.dreamsoftware.tcs.web.dto.response.*;
import com.dreamsoftware.tcs.web.security.provider.social.SocialProviderAuthenticationToken;
import com.dreamsoftware.tcs.web.security.userdetails.ICommonUserDetailsAware;
import com.dreamsoftware.tcs.web.security.utils.JwtTokenHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

/**
 * @author ssanchez
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AccountsServiceImpl implements IAccountsService {

    private final String AUTH_TYPE = "Bearer";

    private final JwtTokenHelper jwtTokenHelper;
    private final UserRepository userRepository;
    private final UserDetailsMapper userDetailsMapper;
    private final SignUpUserMapper signUpUserMapper;
    private final SimpleUserMapper simpleUserMapper;
    private final IWalletService walletService;
    private final StreamBridge streamBridge;
    private final StreamChannelsProperties streamChannelsProperties;
    private final UserLoginRepository userLoginRepository;
    private final SimpleUserLoginMapper simpleUserLoginMapper;
    private final IFacebookService facebookService;
    private final SimpleSocialUserMapper simpleSocialUserMapper;
    private final SignUpSocialUserMapper signUpSocialUserMapper;
    @Qualifier("UploadUserAvatarService")
    private final IUploadImagesService uploadUserAvatarService;
    private final AuthProviderMapper authProviderMapper;
    private final AuthenticationProviderRepository authProviderEntityRepository;
    private final IGoogleService googleService;
    @Qualifier("usersAuthenticationManager")
    private final AuthenticationManager usersAuthenticationManager;
    @Qualifier("adminAuthenticationManager")
    private final AuthenticationManager adminAuthenticationManager;
    private final IUserLdapRepository userLdapRepository;
    private final UserLdapAccountMapper userLdapAccountMapper;

    private final PasswordEncoder passwordEncoder;

    /**
     * @param dto
     * @param device
     * @return
     */
    @Override
    public AuthenticationDTO signin(final SignInUserDTO dto, final Device device) {
        Assert.notNull(dto, "DTO can not be null");
        Assert.notNull(dto.getUserAgent(), "UserAgent can not be null");
        Assert.notNull(dto.getRemoteAddr(), "RemoteAddr can not be null");
        Assert.notNull(device, "Device can not be null");

        final Float loginLat = StringUtils.isNoneEmpty(dto.getLatitude())
                ? Float.valueOf(dto.getLatitude()) : null;
        final Float loginLon = StringUtils.isNoneEmpty(dto.getLongitude())
                ? Float.valueOf(dto.getLongitude()) : null;
        final Authentication authRequest = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
        return signin(authRequest, loginLat, loginLon, dto.getUserAgent(), dto.getRemoteAddr(), device);
    }

    /**
     * @param dto
     * @param device
     * @return
     */
    @Override
    public AuthenticationDTO signin(final SignInAdminUserDTO dto, final Device device) {
        Assert.notNull(dto, "DTO can not be null");
        Assert.notNull(dto.getUserAgent(), "UserAgent can not be null");
        Assert.notNull(dto.getRemoteAddr(), "RemoteAddr can not be null");
        Assert.notNull(device, "Device can not be null");
        final Authentication authenticationRequest = new UsernamePasswordAuthenticationToken(dto.getUid(), dto.getPassword());
        final ICommonUserDetailsAware<String> userDetails = authenticate(adminAuthenticationManager, authenticationRequest);
        final AccessTokenDTO accessTokenDTO = jwtTokenHelper.generateToken(userDetails, device, dto.getUserAgent(), dto.getRemoteAddr());
        // Generate and return Authentication Response
        return AuthenticationDTO.builder()
                .accessToken(accessTokenDTO)
                .type(AUTH_TYPE)
                .build();
    }

    /**
     * @param dto
     * @param device
     * @return
     */
    @Override
    public AuthenticationDTO signin(final SignInUserExternalProviderDTO dto, final Device device) throws Throwable {
        Assert.notNull(dto, "DTO can not be null");
        Assert.notNull(dto.getUserAgent(), "UserAgent can not be null");
        Assert.notNull(dto.getRemoteAddr(), "RemoteAddr can not be null");
        Assert.notNull(device, "Device can not be null");

        final String accountId;
        switch (AuthenticationProviderTypeEnum.valueOf(dto.getProvider())) {
            case FACEBOOK:
                accountId = facebookService.getFbIdByAccessToken(dto.getToken());
                break;
            case GOOGLE:
                accountId = googleService.getGoogleIdByAccessToken(dto.getToken());
                break;
            default:
                throw new IllegalStateException("Invalid Provider");
        }

        final Float loginLat = StringUtils.isNoneEmpty(dto.getLatitude())
                ? Float.valueOf(dto.getLatitude()) : null;
        final Float loginLon = StringUtils.isNoneEmpty(dto.getLongitude())
                ? Float.valueOf(dto.getLongitude()) : null;

        final Authentication authRequest = new SocialProviderAuthenticationToken(accountId);

        authProviderEntityRepository.updateAuthenticationProviderTokenForkey(dto.getToken(), accountId);

        return signin(authRequest, loginLat, loginLon, dto.getUserAgent(), dto.getRemoteAddr(), device);
    }

    /**
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
     * @param token
     * @param clientAddr
     * @param clientUserAgent
     */
    @Override
    public void restoreSecurityContextFor(final String token, final String clientAddr, final String clientUserAgent) {
        Assert.notNull(token, "Token can not be null");
        Assert.notNull(clientAddr, "clientAddr can not be null");
        Assert.notNull(clientUserAgent, "clientUserAgent can not be null");
        log.debug("restoreSecurityContextFor: token " + token + ", clientAddr " + clientAddr + ", clientUserAgent " + clientUserAgent);
        try {
            if (jwtTokenHelper.validateToken(token, clientAddr, clientUserAgent)) {
                ICommonUserDetailsAware<String> userDetails = null;
                final String sub = jwtTokenHelper.getSubFromToken(token);
                final Collection<String> authorities = jwtTokenHelper.getAuthoritiesFromToken(token);
                if (authorities.contains(AuthorityEnum.ROLE_STUDENT.name()) || authorities.contains(AuthorityEnum.ROLE_CA_MEMBER.name())) {
                    log.debug("Restore User Details into security context for user id -> " + sub);
                    userDetails = userRepository.findById(new ObjectId(sub))
                            .map(userDetailsMapper::entityToDTO)
                            .orElse(null);
                } else if (authorities.contains(AuthorityEnum.ROLE_ADMIN.name())) {
                    log.debug("Restore User Details into security context for user id -> " + sub);
                    userDetails = userLdapRepository.findOneByUid(sub)
                            .map(userLdapAccountMapper::entityToDTO)
                            .orElse(null);
                }
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
     * @param user
     * @return
     */
    @Override
    public SimpleUserDTO signup(final SignUpUserDTO user) {
        Assert.notNull(user, "user can not be null");
        // Map to user entity
        final UserEntity userToSave = signUpUserMapper.signUpUserDTOToStudentUserEntity(user);
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
     * @param user
     * @return
     */
    @Override
    public SimpleUserDTO signup(final SignUpExternalProviderDTO user) throws Throwable {
        Assert.notNull(user, "user can not be null");
        final SimpleUserDTO userDTO;
        switch (AuthenticationProviderTypeEnum.valueOf(user.getProvider())) {
            case FACEBOOK:
                userDTO = signupViaFacebook(user);
                break;
            case GOOGLE:
                userDTO = signupViaGoogle(user);
                break;
            default:
                throw new IllegalStateException("Invalid Provider");
        }
        return userDTO;
    }

    /**
     * @param ca
     * @return
     */
    @Override
    public SimpleUserDTO signup(final SignupAsCaAdminDTO ca) {
        Assert.notNull(ca, "ca can not be null");
        final UserEntity userToSave = signUpUserMapper.signUpUserDTOToCaAdminUserEntity(ca.getAdmin());
        final CertificationAuthorityEntity caEntity = CertificationAuthorityEntity
                .builder()
                .createdDate(new Date())
                .executiveDirector(ca.getExecutiveDirector())
                .location(ca.getLocation())
                .name(ca.getName())
                .defaultCostOfIssuingCertificate(ca.getDefaultCostOfIssuingCertificate())
                .supportMail(ca.getSupportMail())
                .build();
        userToSave.setCa(caEntity);
        caEntity.setAdmin(userToSave);
        final UserEntity userEntitySaved = userRepository.save(userToSave);
        final SimpleUserDTO simpleUserDTO = simpleUserMapper.entityToDTO(userEntitySaved);
        streamBridge.send(streamChannelsProperties.getNotificationDeliveryRequest(), UserPendingValidationNotificationEvent.builder()
                .userId(simpleUserDTO.getIdentity())
                .build());
        return simpleUserDTO;
    }

    /**
     * Activate Account
     *
     * @param confirmationToken
     * @return
     */
    @Override
    public SimpleUserDTO activate(final String confirmationToken) throws Throwable {
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
        if (userToActivate.getType() == UserTypeEnum.STUDENT) {
            streamBridge.send(streamChannelsProperties.getUserManagement(), NewStudentEvent.builder()
                    .walletHash(userActivated.getWalletHash())
                    .build());
        } else if (userToActivate.getType() == UserTypeEnum.CA_ADMIN) {
            streamBridge.send(streamChannelsProperties.getUserManagement(), NewCertificationAuthorityEvent.builder()
                    .caId(userToActivate.getCa().getId().toString())
                    .walletHash(userToActivate.getWalletHash())
                    .build());
        }
        return simpleUserMapper.entityToDTO(userActivated);
    }

    /**
     * @param email
     */
    @Override
    public void resetPassword(final String email) {
        Assert.notNull(email, "Email can not be null");
        streamBridge.send(streamChannelsProperties.getUserManagement(),
                UserPasswordResetEvent.builder()
                        .email(email)
                        .build());
    }

    /**
     *
     * @param changePasswordRequestDTO
     */
    @Override
    public void changePassword(final ChangePasswordRequestDTO changePasswordRequestDTO) {
        Assert.notNull(changePasswordRequestDTO, "changePasswordRequestDTO can not be null");
        userRepository.updatePasswordByConfirmationToken(passwordEncoder.encode(changePasswordRequestDTO.getPasswordClear()), changePasswordRequestDTO.getConfirmationToken());
    }

    /**
     * @param key
     * @return
     */
    @Override
    public Optional<AuthenticationProviderDTO> findAuthProviderByKey(final String key) {
        Assert.notNull(key, "key can not be null");
        return authProviderEntityRepository.findOneByKey(key).map(authProviderMapper::entityToDTO);
    }

    /**
     * @param dto
     * @return
     */
    private SimpleUserDTO signupViaFacebook(final SignUpExternalProviderDTO dto) throws Throwable {
        Assert.notNull(dto, "externalProviderDto can not be null");
        // Fetch User FB id
        final String fbId = facebookService.getFbIdByAccessToken(dto.getToken());
        // Fetch User Information from facebook service
        final SimpleSocialUserDTO userFacebookDTO
                = facebookService.fetchUserInformation(fbId, dto.getToken());
        // Signup Social User
        return signupSocial(userFacebookDTO, dto.getUserAgent(), dto.getLanguage());
    }

    /**
     * @param dto
     * @return
     */
    private SimpleUserDTO signupViaGoogle(final SignUpExternalProviderDTO dto) throws Throwable {
        Assert.notNull(dto, "externalProviderDto can not be null");
        try {
            // Fetch User Information via Google
            final SimpleSocialUserDTO userDTO = googleService.fetchUserInformation(dto.getToken());
            // Signup Social User
            return signupSocial(userDTO, dto.getUserAgent(), dto.getLanguage());
        } catch (IOException ex) {
            throw new RuntimeException("Signup Via Google fail");
        }
    }

    /**
     * @param socialUser
     * @param userAgent
     * @param defaultLanguage
     * @return
     */
    private SimpleUserDTO signupSocial(final SimpleSocialUserDTO socialUser, final String userAgent, final String defaultLanguage) throws Throwable {
        // Map User Information to SigUp Request
        final SignUpSocialUserDTO signUpSocialUser = simpleSocialUserMapper.simpleSocialUserDTOToSignUpSocialUserDTO(socialUser);
        // Configure ISO3 Language
        if (StringUtils.isNoneEmpty(signUpSocialUser.getLanguage())) {
            signUpSocialUser.setLanguage(defaultLanguage);
        }
        // Configure User Agent from HTTP Header
        signUpSocialUser.setUserAgent(userAgent);
        final UserEntity userToSave = signUpSocialUserMapper.signUpUserSocialDTOToUserEntity(signUpSocialUser);
        // Generate Wallet for external user
        userToSave.setWalletHash(walletService.generateWallet());
        final UserEntity userEntitySaved = userRepository.save(userToSave);
        // Upload Avatar URL
        if (StringUtils.isNoneEmpty(signUpSocialUser.getAvatarUrl())) {
            uploadUserAvatarService.uploadFromUrl(userEntitySaved.getId(), signUpSocialUser.getAvatarUrl());
        }
        streamBridge.send(streamChannelsProperties.getUserManagement(), NewStudentEvent.builder()
                .walletHash(userEntitySaved.getWalletHash())
                .build());
        return simpleUserMapper.entityToDTO(userEntitySaved);

    }

    /**
     * @param authenticationRequest
     * @param latitude
     * @param longitude
     * @param userAgent
     * @param remoteAddr
     * @param device
     * @return
     */
    private AuthenticationDTO signin(
            final Authentication authenticationRequest,
            final Float latitude,
            final Float longitude,
            final String userAgent,
            final String remoteAddr,
            final Device device) {

        Assert.notNull(authenticationRequest, "Authentication can not be null");
        Assert.notNull(latitude, "latitude can not be null");
        Assert.notNull(longitude, "longitude can not be null");
        Assert.notNull(userAgent, "userAgent can not be null");
        Assert.notNull(remoteAddr, "remoteAddr can not be null");
        Assert.notNull(device, "Device can not be null");

        final ICommonUserDetailsAware<String> userDetails = authenticate(usersAuthenticationManager, authenticationRequest);

        // Generate Access Token
        final AccessTokenDTO accessTokenDTO = jwtTokenHelper.generateToken(userDetails, device, userAgent, remoteAddr);

        // Get last success login
        final SimpleUserLoginDTO lastLoginDTO = userLoginRepository.findFirstByUserIdOrderByCreatedAtDesc(new ObjectId(userDetails.getUserId()))
                .map(simpleUserLoginMapper::entityToDTO).orElse(null);

        // Save Current success login
        UserLoginPlatformEnum loginPlatform;
        switch (device.getDevicePlatform()) {
            case ANDROID:
                loginPlatform = UserLoginPlatformEnum.ANDROID;
                break;
            case IOS:
                loginPlatform = UserLoginPlatformEnum.IOS;
                break;
            default:
                loginPlatform = UserLoginPlatformEnum.WEB;
        }

        userRepository.findById(new ObjectId(userDetails.getUserId())).ifPresent((userEntity) -> {
            final UserLoginEntity loginEntity = UserLoginEntity.builder()
                    .locationLat(latitude)
                    .locationLong(longitude)
                    .userAgent(userAgent)
                    .platform(loginPlatform)
                    .user(userEntity)
                    .build();
            // track user login
            userLoginRepository.save(loginEntity);
        });

        // Generate and return Authentication Response
        return AuthenticationDTO.builder()
                .accessToken(accessTokenDTO)
                .type(AUTH_TYPE)
                .lastLogin(lastLoginDTO)
                .build();
    }

    /**
     * @param authenticationManager
     * @param authenticationRequest
     * @return
     */
    private ICommonUserDetailsAware<String> authenticate(final AuthenticationManager authenticationManager, final Authentication authenticationRequest) {
        Assert.notNull(authenticationManager, "authenticationManager can not be null");
        Assert.notNull(authenticationRequest, "authenticationRequest can not be null");
        // Get User details for authentication request
        final Authentication authentication = authenticationManager.authenticate(authenticationRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return (ICommonUserDetailsAware<String>) authentication.getPrincipal();
    }

}
