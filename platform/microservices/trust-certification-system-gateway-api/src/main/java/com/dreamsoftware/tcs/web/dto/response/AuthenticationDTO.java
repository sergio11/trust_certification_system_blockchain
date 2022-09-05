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
public class AuthenticationDTO {

    /**
     * Access Token
     */
    @JsonProperty("access_token")
    private AccessTokenDTO accessToken;

    /**
     * Type
     */
    @JsonProperty("type")
    private String type;

    /**
     * Last Login
     */
    @JsonProperty("last_login")
    private SimpleUserLoginDTO lastLogin;

}
