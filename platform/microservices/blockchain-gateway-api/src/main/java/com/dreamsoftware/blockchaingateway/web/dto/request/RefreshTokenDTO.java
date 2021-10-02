package com.dreamsoftware.blockchaingateway.web.dto.request;

import javax.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class RefreshTokenDTO {

    /**
     * Token
     */
    @Schema(description = "Current Authentication Token (JWT format)",
            required = true)
    @NotBlank(message = "{user_access_token_not_null}")
    @JsonProperty("token")
    private String token;
}
