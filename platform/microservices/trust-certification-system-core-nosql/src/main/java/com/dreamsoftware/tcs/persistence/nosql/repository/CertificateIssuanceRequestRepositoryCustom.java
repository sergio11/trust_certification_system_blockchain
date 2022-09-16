package com.dreamsoftware.tcs.persistence.nosql.repository;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificateIssuanceRequestEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificateStatusEnum;
import org.bson.types.ObjectId;

/**
 *
 * @author ssanchez
 */
public interface CertificateIssuanceRequestRepositoryCustom {

    /**
     *
     * @param id
     * @param newStatus
     * @return
     */
    CertificateIssuanceRequestEntity updateStatus(ObjectId id, CertificateStatusEnum newStatus);

}
