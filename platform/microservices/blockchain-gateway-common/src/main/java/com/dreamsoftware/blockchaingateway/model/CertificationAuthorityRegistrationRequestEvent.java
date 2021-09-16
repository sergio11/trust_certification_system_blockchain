package com.dreamsoftware.blockchaingateway.model;

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
public class CertificationAuthorityRegistrationRequestEvent {

    /**
     * Certification Authority Name
     */
    private String name;

    /**
     * Certification Authority Default cost of issuing certificate
     */
    private Long defaultCostOfIssuingCertificate;

    /**
     * Wallet Hash
     */
    private String walletHash;
}
