package com.dreamsoftware.tcs.web.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class SaveCertificationCourseEditionDTO {

    /**
     * Certification Course Edition Nam
     */
    @Schema(description = "Certification Course Edition Name", required = true)
    @JsonProperty("name")
    private String name;

    /**
     * Cost of issuing certificate
     */
    @Schema(description = "Cost of Issuing new certificate edition", required = true)
    @JsonProperty("cost_issue_certificate")
    private Long costOfIssuingCertificate;

    /**
     * Duration In Hours
     */
    @Schema(description = "Duration In Hours", required = true)
    @JsonProperty("duration_in_hours")
    private Long durationInHours;

    /**
     * Expiration in days
     */
    @Schema(description = "Expiration In Days", required = false)
    @JsonProperty("expiration_in_days")
    private Long expirationInDays;

    /**
     * Can be renewed
     */
    @Schema(description = "Can Be Renewed", required = false)
    @JsonProperty("can_be_renewed")
    private Boolean canBeRenewed;

    /**
     * Cost of renewing certificate
     */
    @Schema(description = "Cost of renewing certificate", required = false)
    @JsonProperty("cost_renew_certificate")
    private Long costOfRenewingCertificate;

    /**
     * Start At
     */
    @JsonProperty("start_at")
    private Date startAt;

    /**
     * End At
     */
    @JsonProperty("end_at")
    private Date endAt;

    /**
     * Certification Course
     */
    @JsonIgnore
    private String certificationCourseId;

    /**
     * CA Wallet Hash
     */
    @JsonIgnore
    private String caWalletHash;
}
