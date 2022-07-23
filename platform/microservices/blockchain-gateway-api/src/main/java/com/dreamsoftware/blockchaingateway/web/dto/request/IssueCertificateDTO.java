package com.dreamsoftware.blockchaingateway.web.dto.request;

import com.dreamsoftware.blockchaingateway.web.validation.constraints.ShouldBeAValidObjectId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.Valid;
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
public class IssueCertificateDTO {

    /**
     * Certificate Course Id
     */
    @Schema(description = "Certification Course Id", required = true)
    @JsonProperty("certificateCourseId")
    @NotBlank(message = "{certification_course_id_not_null}")
    private String certificateCourseId;

    /**
     * Qualification Obtained
     */
    @Schema(description = "Qualification Obtained", required = true)
    @JsonProperty("qualification")
    private Long qualification;

    /**
     * Recipient User
     */
    @Schema(description = "Recipient User", required = true)
    @JsonProperty("recipientUserId")
    @Valid
    @ShouldBeAValidObjectId(message = "user_id_not_valid")
    private String recipientUserId;

    /**
     * Issuer Wallet Hash
     */
    @JsonIgnore
    private String issuerWalletHash;

}
