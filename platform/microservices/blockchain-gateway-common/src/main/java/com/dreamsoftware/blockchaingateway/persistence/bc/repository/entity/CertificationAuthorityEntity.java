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
public class CertificationAuthorityEntity {

    private String name;
    private BigInteger defaultCostOfIssuingCertificate;
    private Boolean isEnabled;
    private Boolean isExist;
}
