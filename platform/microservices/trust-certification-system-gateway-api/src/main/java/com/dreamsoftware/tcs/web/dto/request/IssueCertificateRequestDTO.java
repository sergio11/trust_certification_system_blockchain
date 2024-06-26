package com.dreamsoftware.tcs.web.dto.request;

import com.dreamsoftware.tcs.web.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class IssueCertificateRequestDTO {

    /**
     * Certificate Course Edition Id
     */
    @Schema(description = "Certification Course Edition Id", required = true)
    @JsonProperty("certificateCourseId")
    @CourseEditionShouldExist(message = "{course_edition_should_exist}")
    @ShouldNotHaveAssociatedCertificate(message = "{course_edition_has_associated_certificate}")
    @ShouldHaveEnoughFundForIssuingCertificate(message = "{not_enough_funds_for_issuing_certificate}")
    @UserMustReachAttendControlLimit(message = "{user_must_reach_attend_control_limit}")
    private String certificateCourseId;

    /**
     * Certificate Type
     */
    @Schema(description = "Certificate Type (ACHIEVEMENT, ATTENDANCE)", required = true)
    @JsonProperty("type")
    @NotBlank(message = "{certificate_type_not_null}")
    @ValidCertificateType(message = "{certificate_type_invalid}", groups = {IExtended.class})
    private String type;


    /**
     * Student Wallet Hash
     */
    @JsonIgnore
    private String studentWalletHash;

}
