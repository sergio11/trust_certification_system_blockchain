package com.dreamsoftware.tcs.persistence.nosql.repository;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificateIssuanceRequestEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificateStatusEnum;
import java.util.Optional;
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
     * @param certificateId
     * @return
     */
    Optional<CertificateIssuanceRequestEntity> findByCertificateId(final String certificateId);

    /**
     *
     * @param studentId
     * @return
     */
    Iterable<CertificateIssuanceRequestEntity> findByStudentOrderByUpdatedAtDesc(final String studentId);

    /**
     *
     * @param caMemberId
     * @return
     */
    Iterable<CertificateIssuanceRequestEntity> findByCaMemberOrderByUpdatedAtDesc(final ObjectId caMemberId);

    /**
     *
     * @param id
     * @return
     */
    Long countByCertificateId(final String id);

    /**
     *
     * @param student
     * @param courseEdition
     * @return
     */
    Long countByStudentAndCourseEdition(final ObjectId student, final ObjectId courseEdition);

}
