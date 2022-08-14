package com.dreamsoftware.tcs.services;

import com.dreamsoftware.tcs.web.dto.internal.PasswordResetTokenDTO;

/**
 * Password Reset Token Service Interface
 *
 * @author ssanchez
 */
public interface IPasswordResetTokenService {

    /**
     * Create Password Reset Token For User with email
     *
     * @param email
     * @return
     */
    PasswordResetTokenDTO createPasswordResetTokenForUserWithEmail(final String email);

    /**
     * Get Password Reset Token For User with email
     *
     * @param email
     * @return
     */
    PasswordResetTokenDTO getPasswordResetTokenForUserWithEmail(final String email);

    /**
     * Is Valid
     *
     * @param id
     * @param token
     * @return
     */
    Boolean isValid(final String id, final String token);

    /**
     * Delete Expired Tokes
     */
    void deleteExpiredTokens();
}
