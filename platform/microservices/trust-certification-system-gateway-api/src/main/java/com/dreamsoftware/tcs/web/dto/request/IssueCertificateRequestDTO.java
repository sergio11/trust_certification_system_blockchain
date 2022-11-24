package com.dreamsoftware.tcs.web.dto.request;

import com.dreamsoftware.tcs.web.validation.constraints.IExtended;
import com.dreamsoftware.tcs.web.validation.constraints.ValidCertificateType;
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
    @Schema(description = "Certification Course Id", required = true)
    @JsonProperty("certificateCourseId")
    @NotBlank(message = "{certification_course_id_not_null}")
    private String certificateCourseEditionId;

    /**
     * Qualification Obtained
     */
    @Schema(description = "Qualification Obtained", required = true)
    @JsonProperty("qualification")
    private Long qualification;

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
