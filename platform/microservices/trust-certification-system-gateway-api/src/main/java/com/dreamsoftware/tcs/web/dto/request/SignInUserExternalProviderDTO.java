package com.dreamsoftware.tcs.web.dto.request;

import com.dreamsoftware.tcs.web.validation.constraints.IExtended;
import com.dreamsoftware.tcs.web.validation.constraints.ValidAuthProviderType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class SignInUserExternalProviderDTO {

    /**
     * Token
     */
    @Schema(description = "Authentication token obtained through login with facebook",
            required = true)
    @NotBlank(message = "{user_provider_access_token_not_null}")
    @JsonProperty("token")
    private String token;

    /**
     * Type
     */
    @JsonProperty("provider")
    @NotBlank(message = "{provider_not_null}")
    @ValidAuthProviderType(message = "{provider_type_invalid}", groups = {IExtended.class})
    private String provider;

    /**
     * Latitude
     */
    @Schema(description = "Current Latitude value", required = false)
    @JsonProperty("latitude")
    private String latitude;

    /**
     * Longitude
     */
    @Schema(description = "Current Longitude value", required = false)
    @JsonProperty("longitude")
    private String longitude;

    /**
     * User Agent
     */
    @Parameter(hidden = true)
    @JsonIgnore
    private String userAgent;

}
