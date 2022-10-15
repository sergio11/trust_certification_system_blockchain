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
public class CertificateIssuanceRequestDTO {

    /**
     * Identity
     */
    @JsonProperty("identity")
    private String id;

    /**
     * Status
     */
    @JsonProperty("status")
    private String status;

    /**
     * Qualification
     */
    @JsonProperty("qualification")
    private Long qualification;

    /**
     * Course
     */
    //@JsonProperty("course")
    //private SimpleCertificationCourseDetailDTO course;

    /**
     * Student Wallet Hash
     */
    @JsonProperty("student_wallet_hash")
    private String studentWalletHash;

    /**
     * CA Wallet Hash
     */
    @JsonProperty("ca_wallet_hash")
    private String caWalletHash;

}
