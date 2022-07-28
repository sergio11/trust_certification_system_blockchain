package com.dreamsoftware.tcs.persistence.nosql.repository;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseStateEnum;

/**
 *
 * @author ssanchez
 */
public interface CertificationCourseRepositoryCustom {

    /**
     *
     * @param courseId
     * @param newStatus
     */
    void updateStatus(String courseId, CertificationCourseStateEnum newStatus);

}
