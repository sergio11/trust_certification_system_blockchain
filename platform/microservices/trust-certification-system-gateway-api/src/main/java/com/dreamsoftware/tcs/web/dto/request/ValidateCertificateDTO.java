package com.dreamsoftware.tcs.web.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 *
 * @author ssanchez
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidateCertificateDTO {

    /**
     * Certificate Payload
     */
    @Schema(description = "Certificate Payload", required = true)
    @JsonProperty("payload")
    @NotBlank(message = "{certificate_payload_not_null}")
    private String payload;
}
