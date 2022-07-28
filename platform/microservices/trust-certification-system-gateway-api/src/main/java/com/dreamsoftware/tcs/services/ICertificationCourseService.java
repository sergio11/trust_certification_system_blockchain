package com.dreamsoftware.tcs.services;

import com.dreamsoftware.tcs.web.dto.request.SaveCertificationCourseDTO;
import com.dreamsoftware.tcs.web.dto.response.CertificationCourseDetailDTO;

/**
 *
 * @author ssanchez
 */
public interface ICertificationCourseService {

    /**
     * Enable Certification Course
     *
     * @param caWalletHash
     * @param courseId
     * @return
     * @throws Throwable
     */
    CertificationCourseDetailDTO enable(final String caWalletHash, final String courseId) throws Throwable;

    /**
     * Disable Certification Course
     *
     * @param caWalletHash
     * @param courseId
     * @return
     * @throws Throwable
     */
    CertificationCourseDetailDTO disable(final String caWalletHash, final String courseId) throws Throwable;

    /**
     * Save Model
     *
     * @param model
     */
    void save(final SaveCertificationCourseDTO model);

    /**
     * Remove Certificacion course
     *
     * @param caWalletHash
     * @param courseId
     * @return
     * @throws java.lang.Throwable
     */
    CertificationCourseDetailDTO remove(final String caWalletHash, final String courseId) throws Throwable;

    /**
     * Check if can be issued
     *
     * @param caWalletHash
     * @param courseId
     * @return
     * @throws java.lang.Throwable
     */
    Boolean canBeIssued(final String caWalletHash, final String courseId) throws Throwable;

    /**
     * Check if can be renewed
     *
     * @param caWalletHash
     * @param courseId
     * @return
     * @throws java.lang.Throwable
     */
    Boolean canBeRenewed(final String caWalletHash, final String courseId) throws Throwable;

    /**
     * Get Detail
     *
     * @param caWalletHash
     * @param courseId
     * @return
     */
    CertificationCourseDetailDTO getDetail(final String caWalletHash, final String courseId) throws Throwable;
}
