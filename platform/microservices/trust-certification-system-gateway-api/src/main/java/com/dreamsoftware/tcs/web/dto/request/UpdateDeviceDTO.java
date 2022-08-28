package com.dreamsoftware.tcs.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
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
public class UpdateDeviceDTO {

    /**
     * Device Id
     */
    @JsonProperty("device_id")
    private String deviceId;

    /**
     * Registration Token
     */
    @NotBlank(message = "{device.registration.token.not.blank}")
    @JsonProperty("registration_token")
    private String registrationToken;
}
