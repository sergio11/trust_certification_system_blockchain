package com.dreamsoftware.tcs.persistence.bc.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CertificationCourseModelEntity {

    /**
     * Course Id
     */
    private String id;

    /**
     * Course Name
     */
    private String name;

    /**
     * Cost of issuing certificate
     */
    private Long costOfIssuingCertificate;

    /**
     * Cost of renewing certificate
     */
    private Long costOfRenewingCertificate;

    /**
     * Certificate Authority
     */
    private String certificationAuthority;

    /**
     * Duration In hours
     */
    private Long durationInHours;

    /**
     * Expiration in days
     */
    private Long expirationInDays;

    /**
     * can be renewed
     */
    private Boolean canBeRenewed;

    /**
     * is Enabled
     */
    private Boolean isEnabled;

    /**
     * Is Exists
     */
    private Boolean isExist;
}
