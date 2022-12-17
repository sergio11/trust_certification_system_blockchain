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
    @Schema(description = "Certification Course Edition Name")
    @JsonProperty("name")
    private String name;

    /**
     * Cost of issuing certificate
     */
    @Schema(description = "Cost of Issuing new certificate edition", required = true)
    @JsonProperty("costOfIssuingCertificate")
    private Long costOfIssuingCertificate;

    /**
     * Duration In Hours
     */
    @Schema(description = "Duration In Hours", required = true)
    @JsonProperty("durationInHours")
    private Long durationInHours;

    /**
     * Expiration in days
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
     * Start At
     */
    @Schema(description = "Course start at")
    @JsonProperty("startAt")
    private Date startAt;

    /**
     * End At
     */
    @Schema(description = "Course end at")
    @JsonProperty("endAt")
    private Date endAt;

    /**
     * Min Percentage Attendance Required
     */
    @Schema(description = "Min Percentage Attendance Required", required = true)
    @JsonProperty("minPercentageAttendanceRequired")
    private Integer minPercentageAttendanceRequired;

    /**
     * Max Attendee Count
     */
    @Schema(description = "Max Attendee Count", required = true)
    @JsonProperty("maxAttendeeCount")
    private Long maxAttendeeCount;

    /**
     * Max Attendance Count
     */
    @Schema(description = "Max Attendance Count", required = true)
    @JsonProperty("maxAttendanceCount")
    private Long maxAttendanceCount;

    /**
     * Course registration cost in TCS tokens
     */
    @Schema(description = "Course registration cost in TCS tokens")
    @JsonProperty("enrollCost")
    private Long enrollCost;

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
