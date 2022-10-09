package com.dreamsoftware.tcs.persistence.nosql.repository;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationAuthorityEntity;

/**
 *
 * @author ssanchez
 */
public interface CertificationAuthorityRepositoryCustom {

    /**
     * @param caId
     * @param certificationAuthorityEntity
     */
    CertificationAuthorityEntity update(String caId, CertificationAuthorityEntity certificationAuthorityEntity);

}
