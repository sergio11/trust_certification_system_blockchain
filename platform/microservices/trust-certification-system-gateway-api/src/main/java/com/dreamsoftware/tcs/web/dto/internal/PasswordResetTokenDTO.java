package com.dreamsoftware.tcs.web.dto.internal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Password Reset Token
 *
 * @author ssanchez
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetTokenDTO {

    /**
     * Id
     */
    private String id;

    /**
     * Token
     */
    private String token;

    /**
     * Expiry Date
     */
    private String expiryDate;

    /**
     * Name
     */
    private String name;

    /**
     * Mail
     */
    private String email;

    /**
     * Locale
     */
    private String locale;

}
