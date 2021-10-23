package com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity;

import java.math.BigInteger;
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
public class CertificationCourseEntity {

    private String id;
    private String name;
    private Long costOfIssuingCertificate;
    private Long costOfRenewingCertificate;
    private String certificationAuthority;
    private Long durationInHours;
    private Long expirationInDays;
    private Boolean canBeRenewed;
    private Boolean isEnabled;
    private Boolean isExist;
}
