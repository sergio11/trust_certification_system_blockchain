package com.dreamsoftware.tcs.persistence.nosql.repository;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationAuthorityEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEntity;
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

}
