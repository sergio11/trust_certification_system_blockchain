package com.dreamsoftware.tcs.persistence.nosql.repository;

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
     */
    void updateStatus(ObjectId id, CertificateStatusEnum newStatus);

}
