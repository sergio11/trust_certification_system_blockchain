package com.dreamsoftware.blockchaingateway.services;

import com.dreamsoftware.blockchaingateway.web.dto.request.SaveCertificationCourseDTO;

/**
 *
 * @author ssanchez
 */
public interface IBlockchainProcessor {

    /**
     * on User Activated
     *
     * @param id
     */
    void onUserActivated(final String id);

    /**
     *
     * @param certificationCourseDTO
     */
    void onRegisterCertificationCourse(final SaveCertificationCourseDTO certificationCourseDTO);
}
