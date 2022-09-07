package com.dreamsoftware.tcs.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class SimpleSocialUserDTO {

    /**
     * Id
     */
    @JsonProperty("identity")
    private String id;

    /**
     * Email
     */
    @JsonProperty("email")
    private String email;

    /**
     * Name
     */
    @JsonProperty("name")
    private String name;

    /**
     * Surname
     */
    @JsonProperty("surname")
    private String surname;

    /**
     * Birthday
     */
    @JsonProperty("birthday")
    private String birthday;

    /**
     * Locale
     */
    @JsonProperty("locale")
    private String locale;

    /**
     * Access Token
     */
    @JsonProperty("token")
    private String accessToken;

    /**
     * Social Provider
     */
    @JsonProperty("provider")
    private String provider;

    /**
     * Avartar URL
     */
    @JsonProperty("avatar")
    private String avatarUrl;
}
