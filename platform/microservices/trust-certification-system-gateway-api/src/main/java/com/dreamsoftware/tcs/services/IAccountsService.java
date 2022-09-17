package com.dreamsoftware.tcs.services;

import com.dreamsoftware.tcs.web.dto.request.SignInAdminUserDTO;
import com.dreamsoftware.tcs.web.dto.request.SignInUserDTO;
import com.dreamsoftware.tcs.web.dto.request.SignInUserExternalProviderDTO;
import com.dreamsoftware.tcs.web.dto.request.SignUpUserDTO;
import com.dreamsoftware.tcs.web.dto.request.SignUpExternalProviderDTO;
import com.dreamsoftware.tcs.web.dto.response.AuthenticationDTO;
import com.dreamsoftware.tcs.web.dto.response.AuthenticationProviderDTO;
import com.dreamsoftware.tcs.web.dto.response.SimpleUserDTO;
import java.util.Optional;
import org.springframework.mobile.device.Device;

/**
 *
 * @author ssanchez
 */
public interface IAccountsService {

    /**
     * Sign in user
     *
     * @param dto
     * @param device
     * @return
     */
    AuthenticationDTO signin(final SignInUserDTO dto, final Device device);

    /**
     * Sign in an admin user
     *
     * @param dto
     * @param device
     * @return
     */
    AuthenticationDTO signin(final SignInAdminUserDTO dto, final Device device);

    /**
     *
     * @param dto
     * @param device
     * @return
     * @throws java.lang.Throwable
     */
    AuthenticationDTO signin(final SignInUserExternalProviderDTO dto, final Device device) throws Throwable;

    /**
     * Refresh Access Token
     *
     * @param token
     * @return
     */
    AuthenticationDTO refresh(final String token);

    /**
     * Restore Security Context For
     *
     * @param token
     * @param clientAddr
     * @param clientUserAgent
     */
    void restoreSecurityContextFor(final String token, final String clientAddr, final String clientUserAgent);

    /**
     * Signup User
     *
     * @param user
     * @return
     */
    SimpleUserDTO signup(final SignUpUserDTO user);

    /**
     * Signup SignUpExternalProviderDTO
     *
     * @param user
     * @return
     * @throws java.lang.Throwable
     */
    SimpleUserDTO signup(final SignUpExternalProviderDTO user) throws Throwable;

    /**
     * Activate Account
     *
     * @param confirmationToken
     * @return
     * @throws java.lang.Throwable
     */
    SimpleUserDTO activate(final String confirmationToken) throws Throwable;

    /**
     *
     * @param email
     */
    void resetPassword(final String email);

    /**
     * Find Auth Provider By Key
     *
     * @param key
     * @return
     */
    Optional<AuthenticationProviderDTO> findAuthProviderByKey(final String key);
}
