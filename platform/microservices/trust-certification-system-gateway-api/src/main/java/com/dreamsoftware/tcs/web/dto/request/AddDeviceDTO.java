package com.dreamsoftware.tcs.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;

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
public class AddDeviceDTO {

    /**
     * Device Id
     */
    @Schema(description = "Device Id", required = true)
    @JsonProperty("device_id")
    private String deviceId;

    /**
     * Registration Token
     */
    @Schema(description = "Device Registration token", required = true)
    @NotBlank(message = "{device_registration_token_not_blank}")
    @JsonProperty("registration_token")
    private String registrationToken;
}
