package com.dreamsoftware.tcs.persistence.nosql.repository;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEditionEntity;
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
     * @param courseId
     * @param caId
     * @return
     */
    Long countByIdAndCaId(final ObjectId courseId, final ObjectId caId);

    /**
     *
     * @param courseEditionId
     * @return
     */
    Long countById(final ObjectId courseEditionId);

    /**
     *
     * @param courseId
     * @return
     */
    Iterable<CertificationCourseEditionEntity> findAllByCourse(final ObjectId courseId);

}
