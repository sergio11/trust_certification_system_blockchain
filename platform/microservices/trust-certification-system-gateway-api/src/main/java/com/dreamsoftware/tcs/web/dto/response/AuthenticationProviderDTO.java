package com.dreamsoftware.tcs.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Auth Provider DTO
 *
 * @author ssanchez
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationProviderDTO {

    /**
     * User Identity
     */
    @JsonProperty("identity")
    private String identity;

    /**
     * Key
     */
    @JsonProperty("key")
    private String key;

    /**
     * Type
     */
    @JsonProperty("type")
    private String type;

    /**
     * User
     */
    @JsonProperty("user")
    private SimpleUserDTO user;

}
