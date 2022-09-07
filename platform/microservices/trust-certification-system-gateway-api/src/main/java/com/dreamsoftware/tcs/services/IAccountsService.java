package com.dreamsoftware.tcs.services;

import com.dreamsoftware.tcs.web.dto.request.SignInAdminUserDTO;
import com.dreamsoftware.tcs.web.dto.request.SignInUserDTO;
import com.dreamsoftware.tcs.web.dto.request.SignInUserViaExternalProviderDTO;
import com.dreamsoftware.tcs.web.dto.request.SignUpUserDTO;
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
     */
    AuthenticationDTO signin(final SignInUserViaExternalProviderDTO dto, final Device device);

    /**
     * Sign in using facebook as external provider
     *
     * @param dto
     * @return
     */
    SimpleUserDTO signupViaFacebook(final SignInUserViaExternalProviderDTO dto);

    /**
     *
     * @param dto
     * @return
     */
    SimpleUserDTO signupViaGoogle(final SignInUserViaExternalProviderDTO dto);

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
     */
    void restoreSecurityContextFor(final String token);

    /**
     * Signup User
     *
     * @param user
     * @return
     */
    SimpleUserDTO signup(final SignUpUserDTO user);

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
