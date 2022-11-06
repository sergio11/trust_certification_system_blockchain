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
public class CertificateIssuedDTO {

    /**
     * Identity
     */
    @JsonProperty("identity")
    private String id;

    /**
     * Course
     */
    @JsonProperty("course")
    private String course;

    /**
     * Expiration Date
     */
    @JsonProperty("expiration_date")
    private String expirationDate;

    /**
     * Qualification
     */
    @JsonProperty("qualification")
    private Long qualification;

    /**
     * Duration In Hours
     */
    @JsonProperty("duration_in_hours")
    private String durationInHours;

    /**
     * Expedition Date
     */
    @JsonProperty("expedition_date")
    private String expeditionDate;

    /**
     * Is Visible
     */
    @JsonProperty("is_visible")
    private Boolean isVisible;

    /**
     * Is Enabled
     */
    @JsonProperty("is_enabled")
    private Boolean isEnabled;

    /**
     * Is Exist
     */
    @JsonProperty("is_exist")
    private Boolean isExist;

    /**
     * File Certificate Hash
     */
    @JsonProperty("file_certificate_hash")
    private String fileCertificateHash;

}
