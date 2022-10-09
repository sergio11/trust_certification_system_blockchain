package com.dreamsoftware.tcs.persistence.nosql.repository;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseStateEnum;
import org.bson.types.ObjectId;

/**
 *
 * @author ssanchez
 */
public interface CertificationCourseRepositoryCustom {

    /**
     *
     * @param id
     * @param newStatus
     * @return
     */
    CertificationCourseEntity updateStatus(ObjectId id, CertificationCourseStateEnum newStatus);

}
