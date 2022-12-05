package com.dreamsoftware.tcs.services;

import com.dreamsoftware.tcs.web.core.FileInfoDTO;
import com.dreamsoftware.tcs.web.dto.request.SaveCertificationCourseDTO;
import com.dreamsoftware.tcs.web.dto.request.SaveCertificationCourseEditionDTO;
import com.dreamsoftware.tcs.web.dto.response.*;

import java.util.Optional;
import org.bson.types.ObjectId;

/**
 *
 * @author ssanchez
 */
public interface ICertificationCourseService {

    /**
     * Enable Certification Course
     * @param caWalletHash
     * @param courseId
     * @throws Throwable
     */
    void enable(final String caWalletHash, final String courseId) throws Throwable;

    /**
     * Enable Certification Course Edition
     * @param caWalletHash
     * @param courseId
     * @param editionId
     * @throws Throwable
     */
    void enable(final String caWalletHash, final String courseId, final String editionId) throws Throwable;

    /**
     * Disable Certification Course
     * @param caWalletHash
     * @param courseId
     * @throws Throwable
     */
    void disable(final String caWalletHash, final String courseId) throws Throwable;

    /**
     * Disable Certification Course Edition
     * @param caWalletHash
     * @param courseId
     * @param editionId
     * @throws Throwable
     */
    void disable(final String caWalletHash, final String courseId, final String editionId) throws Throwable;

    /**
     *
     * @param studentWalletHash
     * @param courseId
     * @param editionId
     * @throws Throwable
     */
    void enroll(final String studentWalletHash, final String courseId, final String editionId) throws Throwable;

    /**
     *
     * @param studentWalletHash
     * @param courseId
     * @param editionId
     * @param securityToken
     * @throws Throwable
     */
    void checkIn(final String studentWalletHash, final String courseId, final String editionId, final String securityToken) throws Throwable;

    /**
     *
     * @param courseEditionId
     * @return
     */
    boolean currentUserHasBeenEnrolledTo(final String courseEditionId);

    /**
     *
     * @param courseEditionId
     * @return
     */
    boolean currentUserHasBeenReachAttendControlLimitTo(final String courseEditionId);

    /**
     *
     * @param courseEditionId
     * @return
     */
    boolean courseEditionAllowEnrollment(final String courseEditionId);

    /**
     *
     * @param courseEditionId
     * @return
     */
    boolean courseEditionAllowCheckIn(final String courseEditionId);

    /**
     *
     * @param courseId
     * @param courseEdition
     * @param studentWalletHash
     * @param width
     * @param height
     * @return
     * @throws Exception
     */
    FileInfoDTO getEnrollmentQR(final String courseId, final String courseEdition, final String studentWalletHash, final Integer width, final Integer height) throws Exception;

    /**
     *
     * @param model
     * @return
     */
    SimpleCertificationCourseDetailDTO save(final SaveCertificationCourseDTO model);

    /**
     *
     * @param model
     * @return
     */
    CertificationCourseEditionDetailDTO save(final SaveCertificationCourseEditionDTO model);

    /**
     * Remove Certification course
     * @param caWalletHash
     * @param courseId
     * @throws Throwable
     */
    void remove(final String caWalletHash, final String courseId) throws Throwable;

    /**
     * Remove Certification course edition
     * @param caWalletHash
     * @param courseId
     * @param editionId
     * @throws Throwable
     */
    void remove(final String caWalletHash, final String courseId, final String editionId) throws Throwable;

    /**
     * Check if can be issued
     *
     * @param courseId
     * @return
     * @throws java.lang.Throwable
     */
    Boolean canBeIssued(final String courseId) throws Throwable;

    /**
     * Check if can be renewed
     *
     * @param courseId
     * @return
     * @throws java.lang.Throwable
     */
    Boolean canBeRenewed(final String courseId) throws Throwable;

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
    Iterable<SimpleCertificationCourseDetailDTO> getAll() throws Throwable;

    /**
     *
     * @param term
     * @return
     * @throws Throwable
     */
    Iterable<SimpleCertificationCourseDetailDTO> searchCourses(final String term) throws Throwable;

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
    Boolean isTheCourseOwner(final String courseId, final ObjectId userId);

    /**
     *
     * @param courseId
     * @return
     */
    Optional<UpdateCertificationCourseDTO> editCertificationCourseById(final String courseId);

    /**
     *
     * @param editionId
     * @return
     */
    Optional<UpdateCertificationCourseEditionDTO> editCertificationCourseEditionById(final String editionId);


    /**
     *
     * @param courseId
     * @param model
     * @param caWalletHash
     * @return
     */
    Optional<SimpleCertificationCourseDetailDTO> update(final String courseId, final UpdateCertificationCourseDTO model, final String caWalletHash);

    /**
     *
     * @param courseId
     * @param editionId
     * @param model
     * @param caWalletHash
     * @return
     */
    Optional<CertificationCourseEditionDetailDTO> update(final String courseId, final String editionId, final UpdateCertificationCourseEditionDTO model, final String caWalletHash);
}
