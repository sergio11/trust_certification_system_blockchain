package com.dreamsoftware.tcs.persistence.bc.repository.entity;

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
public class CertificationAuthorityEntity {

    /**
     * Name
     */
    private String name;

    /**
     * Default cost of issuing certificate
     */
    private BigInteger defaultCostOfIssuingCertificate;

    /**
     * Is Enabled
     */
    private Boolean isEnabled;

    /**
     * Is Exists
     */
    private Boolean isExist;
}
