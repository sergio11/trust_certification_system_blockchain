package com.dreamsoftware.tcs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ssanchez
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OnNewIssueCertificateRequestEvent {

    /**
     * Student Wallet Hash
     */
    private String studentWalletHash;

    /**
     * Course Id
     */
    private String courseId;

    /**
     * Qualification
     */
    private Long qualification;
}
