package com.dreamsoftware.tcs.model.events;

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
public class CourseCertificateRegistrationRequestEvent {

    /**
     * Course Name
     */
    private String name;

    /**
     * Cost of issuing certificate
     */
    private Long costOfIssuingCertificate;

    /**
     * Duration in hours
     */
    private Long durationInHours;

    /**
     * Expiration in days
     */
    private Long expirationInDays;

    /**
     * Can be renewed
     */
    private Boolean canBeRenewed;

    /**
     * Cost of Renewing Certificate
     */
    private Long costOfRenewingCertificate;

    /**
     * Ca Wallet
     */
    private String caWalletHash;
}