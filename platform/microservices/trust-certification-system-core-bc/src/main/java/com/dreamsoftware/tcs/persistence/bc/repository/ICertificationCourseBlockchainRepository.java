package com.dreamsoftware.tcs.persistence.bc.repository;

import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationCourseBcEntity;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationCourseEventEntity;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;

/**
 *
 * @author ssanchez
 */
public interface ICertificationCourseBlockchainRepository extends IBlockchainEventRepository<CertificationCourseEventEntity> {

    /**
     *
     * @param walletHash
     * @param id
     * @param costOfIssuingCertificate
     * @param durationInHours
     * @param expirationInDays
     * @param canBeRenewed
     * @param costOfRenewingCertificate
     * @return
     * @throws RepositoryException
     */
    CertificationCourseBcEntity addCertificationCourse(final String walletHash, final String id, final Long costOfIssuingCertificate,
                                                       final Long durationInHours, final Long expirationInDays, final Boolean canBeRenewed, final Long costOfRenewingCertificate) throws RepositoryException;

    /**
     * Enable Certification Course
     *
     * @param caWallet
     * @param courseId
     * @return
     * @throws RepositoryException
     */
    CertificationCourseBcEntity enable(final String caWallet, final String courseId) throws RepositoryException;

    /**
     * Disable Certification Course
     *
     * @param caWallet
     * @param courseId
     * @return
     * @throws RepositoryException
     */
    CertificationCourseBcEntity disable(final String caWallet, final String courseId) throws RepositoryException;

    /**
     * Remove Certification Course
     *
     * @param caWallet
     * @param courseId
     * @return
     * @throws RepositoryException
     */
    CertificationCourseBcEntity remove(final String caWallet, final String courseId) throws RepositoryException;

    /**
     * Get Certification Course
     *
     * @param caWallet
     * @param courseId
     * @return
     * @throws RepositoryException
     */
    CertificationCourseBcEntity get(final String caWallet, final String courseId) throws RepositoryException;

    /**
     *
     * @return @throws RepositoryException
     */
    Iterable<CertificationCourseBcEntity> getAll() throws RepositoryException;

    /**
     *
     * @param caWallet
     * @return
     * @throws RepositoryException
     */
    Iterable<CertificationCourseBcEntity> getAllByCa(final String caWallet) throws RepositoryException;

    /**
     * Get Certification Course
     *
     * @param courseId
     * @return
     * @throws RepositoryException
     */
    CertificationCourseBcEntity get(final String courseId) throws RepositoryException;

    /**
     * Can be issued
     *
     * @param caWallet
     * @param courseId
     * @return
     * @throws RepositoryException
     */
    Boolean canBeIssued(final String caWallet, final String courseId) throws RepositoryException;

    /**
     * Can be renewed
     *
     * @param caWallet
     * @param courseId
     * @return
     * @throws RepositoryException
     */
    Boolean canBeRenewed(final String caWallet, final String courseId) throws RepositoryException;

    /**
     *
     * @param caWallet
     * @param model
     * @return
     * @throws RepositoryException
     */
    CertificationCourseBcEntity update(final String caWallet, final CertificationCourseBcEntity model) throws RepositoryException;

}
