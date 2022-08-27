package com.dreamsoftware.tcs.services;

import com.dreamsoftware.tcs.web.dto.request.SignInUserDTO;
import com.dreamsoftware.tcs.web.dto.request.SignUpUserDTO;
import com.dreamsoftware.tcs.web.dto.response.AuthenticationDTO;
import com.dreamsoftware.tcs.web.dto.response.SimpleUserDTO;
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
    AuthenticationDTO signin(final SignInUserDTO dto, Device device);

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
}
