package com.dreamsoftware.tcs.services;

import com.dreamsoftware.tcs.web.dto.request.SaveCertificationCourseDTO;
import com.dreamsoftware.tcs.web.dto.response.CertificationCourseDetailDTO;
import com.dreamsoftware.tcs.web.dto.response.UpdateCertificationCourseDTO;
import java.util.Optional;
import org.bson.types.ObjectId;

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
     * @throws java.lang.Throwable
     */
    CertificationCourseDetailDTO getDetail(final String caWalletHash, final String courseId) throws Throwable;

    /**
     *
     * @return @throws Throwable
     */
    Iterable<CertificationCourseDetailDTO> getAll() throws Throwable;

    /**
     *
     * @param caWalletHash
     * @return
     * @throws Throwable
     */
    Iterable<CertificationCourseDetailDTO> getAllByCA(final String caWalletHash) throws Throwable;

    /**
     *
     * @param courseId
     * @param userId
     * @return
     */
    Boolean isTheOwner(final String courseId, final ObjectId userId);

    /**
     *
     * @param courseId
     * @return
     */
    Optional<UpdateCertificationCourseDTO> editById(final String courseId);

    /**
     *
     * @param courseId
     * @param model
     * @param caWalletHash
     * @return
     */
    Optional<CertificationCourseDetailDTO> update(final String courseId, final UpdateCertificationCourseDTO model, final String caWalletHash);
}
