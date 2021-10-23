package com.dreamsoftware.blockchaingateway.services;

import com.dreamsoftware.blockchaingateway.web.dto.request.SaveCertificationCourseDTO;
import com.dreamsoftware.blockchaingateway.web.dto.response.CertificationCourseDetailDTO;

/**
 *
 * @author ssanchez
 */
public interface ICertificationCourseService {

    /**
     * Enable Certification Course
     *
     * @param caWallet
     * @param courseId
     * @return
     * @throws Throwable
     */
    CertificationCourseDetailDTO enable(final String caWallet, final String courseId) throws Throwable;

    /**
     * Disable Certification Course
     *
     * @param caWallet
     * @param courseId
     * @return
     * @throws Throwable
     */
    CertificationCourseDetailDTO disable(final String caWallet, final String courseId) throws Throwable;

    /**
     * Save Model
     *
     * @param model
     */
    void save(final SaveCertificationCourseDTO model);
}
