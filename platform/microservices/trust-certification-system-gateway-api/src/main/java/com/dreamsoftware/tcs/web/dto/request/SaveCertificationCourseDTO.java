package com.dreamsoftware.tcs.web.dto.request;

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
public class SaveCertificationCourseDTO {

    /**
     * Name
     */
    @Schema(description = "Certification Course Name", required = true)
    @JsonProperty("name")
    @NotBlank(message = "{certification_course_name_not_null}")
    private String name;

    /**
     * Description
     */
    @Schema(description = "Certification Course Description", required = true)
    @JsonProperty("description")
    @NotBlank(message = "{certification_course_description_not_null}")
    private String description;

    /**
     * Cost of issuing certificate not null
     */
    @Schema(description = "Cost of Issuing new Certification Course", required = true)
    @JsonProperty("costOfIssuingCertificate")
    private Long costOfIssuingCertificate;

    /**
     * Duration In Hours
     */
    @Schema(description = "Duration In Hours", required = true)
    @JsonProperty("durationInHours")
    private Long durationInHours;

    /**
     * Duration In Hours
     */
    @Schema(description = "Expiration In Days")
    @JsonProperty("expirationInDays")
    private Long expirationInDays;

    /**
     * Can be renewed
     */
    @Schema(description = "Can Be Renewed")
    @JsonProperty("canBeRenewed")
    private Boolean canBeRenewed;

    /**
     * Cost of renewing certificate
     */
    @Schema(description = "Cost of renewing certificate")
    @JsonProperty("costOfRenewingCertificate")
    private Long costOfRenewingCertificate;

    /**
     * CA Wallet Hash
     */
    @JsonIgnore
    private String caWalletHash;
}
