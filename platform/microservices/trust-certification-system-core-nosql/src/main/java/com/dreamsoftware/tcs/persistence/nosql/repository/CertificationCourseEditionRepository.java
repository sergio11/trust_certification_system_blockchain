package com.dreamsoftware.tcs.persistence.nosql.repository;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEditionEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseStateEnum;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ssanchez
 */
@Repository
public interface CertificationCourseEditionRepository extends MongoRepository<CertificationCourseEditionEntity, ObjectId>, CertificationCourseEditionRepositoryCustom {

    /**
     *
     * @param courseEditionId
     * @return
     */
    Long countById(final ObjectId courseEditionId);

    /**
     *
     * @param courseEditionId
     * @param status
     * @return
     */
    Long countByIdAndStatusNot(final ObjectId courseEditionId, final CertificationCourseStateEnum status);

    /**
     *
     * @param courseEditionId
     * @param status
     * @return
     */
    Long countByIdAndStatus(final ObjectId courseEditionId, final CertificationCourseStateEnum status);

    /**
     *
     * @param courseId
     * @return
     */
    Iterable<CertificationCourseEditionEntity> findAllByCourse(final ObjectId courseId);

}
