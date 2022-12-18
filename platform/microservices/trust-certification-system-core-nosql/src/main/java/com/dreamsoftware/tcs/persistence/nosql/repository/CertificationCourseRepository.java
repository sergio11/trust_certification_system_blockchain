package com.dreamsoftware.tcs.persistence.nosql.repository;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationAuthorityEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseStateEnum;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ssanchez
 */
@Repository
public interface CertificationCourseRepository extends MongoRepository<CertificationCourseEntity, ObjectId>, CertificationCourseRepositoryCustom {

    /**
     *
     * @param ca
     * @return
     */
    Iterable<CertificationCourseEntity> findAllByCa(final CertificationAuthorityEntity ca);

    /**
     *
     * @param status
     * @return
     */
    Iterable<CertificationCourseEntity> findAllByStatusNotOrderByCreatedAtDesc(final CertificationCourseStateEnum status);

    /**
     *
     * @param id
     * @return
     */
    Long countById(final ObjectId id);

    /**
     *
     * @param id
     * @param status
     * @return
     */
    Long countByIdAndStatusNot(final ObjectId id, final CertificationCourseStateEnum status);

    /**
     *
     * @param id
     * @param status
     * @return
     */
    Long countByIdAndStatus(final ObjectId id, final CertificationCourseStateEnum status);

}
