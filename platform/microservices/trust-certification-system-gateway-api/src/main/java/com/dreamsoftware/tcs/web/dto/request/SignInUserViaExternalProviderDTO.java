package com.dreamsoftware.tcs.web.dto.request;

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
public class SignInUserViaExternalProviderDTO {

    /**
     * Token
     */
    @Schema(description = "Authentication token obtained through login with facebook",
            required = true)
    @NotBlank(message = "{user_provider_access_token_not_null}")
    @JsonProperty("token")
    private String token;

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
     * Id
     */
    @Parameter(hidden = true)
    @JsonIgnore
    private String id;

    /**
     * User Agent
     */
    @Parameter(hidden = true)
    @JsonIgnore
    private String userAgent;

    /**
     * User language
     */
    @Parameter(hidden = true)
    @JsonIgnore
    private String language;
}
