package com.dreamsoftware.tcs.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 *
 * @author ssanchez
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleCertificationCourseDetailDTO {

    /**
     * Identity
     */
    @JsonProperty("identity")
    private String id;

    /**
     * Name
     */
    @JsonProperty("name")
    private String name;

    /**
     * Description
     */
    @JsonProperty("description")
    private String description;

    /**
     * Created At
     */
    @JsonProperty("created_at")
    private Date createdAt;

    /**
     * Status
     */
    @JsonProperty("status")
    private String status;

    /**
     * Certification Authority
     */
    @JsonProperty("certification_authority")
    private SimpleCertificationAuthorityDetailDTO ca;

    /**
     * Cost of Issuing Certificate
     */
    @JsonProperty("cost_issuing_certificate")
    private Long costOfIssuingCertificate;

    /**
     * Cost of renewing certificate
     */
    @JsonProperty("cost_renewing_certificate")
    private Long costOfRenewingCertificate;

    /**
     * Duration in hours
     */
    @JsonProperty("duration_in_hours")
    private Long durationInHours;

    /**
     * Expiration in days
     */
    @JsonProperty("expiration_in_days")
    private Long expirationInDays;

    /**
     * Can be renewed
     */
    @JsonProperty("can_be_renewed")
    private Boolean canBeRenewed;
}
