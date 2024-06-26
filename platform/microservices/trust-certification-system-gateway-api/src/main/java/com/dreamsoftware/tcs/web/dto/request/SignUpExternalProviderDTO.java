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
public class SignUpExternalProviderDTO {

    /**
     * Token
     */
    @Schema(description = "Authentication token obtained through login with facebook",
            required = true)
    @NotBlank(message = "{provider_access_token_not_null}")
    @JsonProperty("token")
    private String token;

    /**
     * Type
     */
    @Schema(description = "External Provider (FACEBOOK, GOOGLE)", required = true)
    @JsonProperty("provider")
    @NotBlank(message = "{provider_not_null}")
    @ValidAuthProviderType(message = "{provider_type_invalid}", groups = {IExtended.class})
    private String provider;

    /**
     * User language
     */
    @Parameter(hidden = true)
    @JsonIgnore
    private String language;

    /**
     * User agent
     */
    @Parameter(hidden = true)
    @JsonIgnore
    private String userAgent;
}
