package com.dreamsoftware.tcs.dto;

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
public class CertificateIssuedQRDataDTO {

    /**
     * CA Wallet Hash
     */
    private String caWalletHash;

    /**
     * Student Wallet Hash
     */
    private String studentWalletHash;

    /**
     * Course Id
     */
    private String courseId;

}
