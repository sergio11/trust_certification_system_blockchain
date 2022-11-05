package com.dreamsoftware.tcs.persistence.nosql.repository;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseStateEnum;
import org.bson.types.ObjectId;

/**
 *
 * @author ssanchez
 */
public interface CertificationCourseEditionRepositoryCustom {

    /**
     *
     * @param id
     * @param newStatus
     * @return
     */
    void updateStatus(ObjectId id, CertificationCourseStateEnum newStatus);

}
