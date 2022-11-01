package com.dreamsoftware.tcs.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class UpdateCertificationCourseEditionDTO {

    /**
     * Name
     */
    @JsonProperty("name")
    private String name;

    /**
     * Cost of Issuing Certificate
     */
    @JsonProperty("costOfIssuingCertificate")
    private Long costOfIssuingCertificate;

    /**
     * Cost of renewing certificate
     */
    @JsonProperty("costOfRenewingCertificate")
    private Long costOfRenewingCertificate;

    /**
     * Duration in hours
     */
    @JsonProperty("durationInHours")
    private Long durationInHours;

    /**
     * Expiration in days
     */
    @JsonProperty("expirationInDays")
    private Long expirationInDays;

    /**
     * Can be renewed
     */
    @JsonProperty("canBeRenewed")
    private Boolean canBeRenewed;

}
