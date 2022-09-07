package com.dreamsoftware.tcs.web.dto.response;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ssanchez
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpSocialUserDTO implements Serializable {

    /**
     * Id
     */
    private String id;

    /**
     * Access Token
     */
    private String accessToken;

    /**
     * User Name
     */
    private String name;

    /**
     * User Surname
     */
    private String surname;

    /**
     * Password Clear
     */
    private String passwordClear;

    /**
     * User Email
     */
    private String email;

    /**
     * User Agent
     */
    private String userAgent;

    /**
     * User Language
     */
    private String language;

    /**
     * Social Provider (FACEBOOK, GOOGLE)
     */
    private String provider;

    /**
     * Avatar URL
     */
    private String avatarUrl;
}
