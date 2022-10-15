package com.dreamsoftware.tcs.stream.events.course;

import com.dreamsoftware.tcs.utils.EntityAnnotation;
import lombok.*;

/**
 *
 * @author ssanchez
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityAnnotation(entityClass = CourseCertificateRegistrationRequestEvent.class)
public class CourseCertificateRegistrationRequestEvent extends AbstractCourseManagementEvent {

    /**
     * Course Id
     */
    private String id;

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
