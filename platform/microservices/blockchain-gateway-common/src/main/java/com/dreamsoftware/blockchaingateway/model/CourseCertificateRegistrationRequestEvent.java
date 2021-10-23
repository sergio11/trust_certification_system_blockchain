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
public class CourseCertificateRegistrationRequestEvent {

    private String name;
    private Long costOfIssuingCertificate;
    private Long durationInHours;
    private Long expirationInDays;
    private Long canBeRenewed;
    private Long costOfRenewingCertificate;
    private String caWallet;
}
