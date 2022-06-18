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

    /**
     * Remove Certificacion course
     *
     * @param caWallet
     * @param courseId
     * @return
     * @throws java.lang.Throwable
     */
    CertificationCourseDetailDTO remove(final String caWallet, final String courseId) throws Throwable;

    /**
     * Check if can be issued
     *
     * @param caWallet
     * @param courseId
     * @return
     * @throws java.lang.Throwable
     */
    Boolean canBeIssued(final String caWallet, final String courseId) throws Throwable;

    /**
     * Check if can be renewed
     *
     * @param caWallet
     * @param courseId
     * @return
     * @throws java.lang.Throwable
     */
    Boolean canBeRenewed(final String caWallet, final String courseId) throws Throwable;

    /**
     * Get Detail
     *
     * @param caWallet
     * @param courseId
     * @return
     */
    CertificationCourseDetailDTO getDetail(final String caWallet, final String courseId) throws Throwable;
}
