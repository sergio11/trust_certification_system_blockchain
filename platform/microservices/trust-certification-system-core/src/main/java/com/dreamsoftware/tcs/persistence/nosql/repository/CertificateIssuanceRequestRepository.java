package com.dreamsoftware.tcs.persistence.nosql.repository;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificateIssuanceRequestEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificateStatusEnum;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ssanchez
 */
@Repository
public interface CertificateIssuanceRequestRepository extends MongoRepository<CertificateIssuanceRequestEntity, ObjectId>, CertificateIssuanceRequestRepositoryCustom {

    /**
     *
     * @param id
     * @param status
     * @return
     */
    Long countByIdAndStatus(final ObjectId id, final CertificateStatusEnum status);

    /**
     *
     * @param studentWalletHash
     * @return
     */
    Iterable<CertificateIssuanceRequestEntity> findByStudentWalletHashOrderByUpdatedAtDesc(final String studentWalletHash);

    /**
     *
     * @param caWalletHash
     * @return
     */
    Iterable<CertificateIssuanceRequestEntity> findByCaWalletHashOrderByUpdatedAtDesc(final String caWalletHash);

}
