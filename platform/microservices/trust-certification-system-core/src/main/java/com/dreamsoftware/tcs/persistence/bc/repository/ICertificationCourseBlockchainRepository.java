package com.dreamsoftware.tcs.persistence.bc.repository;

import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationCourseModelEntity;
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
     * @param name
     * @param costOfIssuingCertificate
     * @param durationInHours
     * @param expirationInDays
     * @param canBeRenewed
     * @param costOfRenewingCertificate
     * @return
     * @throws RepositoryException
     */
    CertificationCourseModelEntity register(final String walletHash, final String name, final Long costOfIssuingCertificate,
            final Long durationInHours, final Long expirationInDays, final Boolean canBeRenewed, final Long costOfRenewingCertificate) throws RepositoryException;

    /**
     * Enable Certification Course
     *
     * @param caWallet
     * @param courseId
     * @return
     * @throws RepositoryException
     */
    CertificationCourseModelEntity enable(final String caWallet, final String courseId) throws RepositoryException;

    /**
     * Disable Certification Course
     *
     * @param caWallet
     * @param courseId
     * @return
     * @throws RepositoryException
     */
    CertificationCourseModelEntity disable(final String caWallet, final String courseId) throws RepositoryException;

    /**
     * Remove Certification Course
     *
     * @param caWallet
     * @param courseId
     * @return
     * @throws RepositoryException
     */
    CertificationCourseModelEntity remove(final String caWallet, final String courseId) throws RepositoryException;

    /**
     * Get Certification Course
     *
     * @param caWallet
     * @param courseId
     * @return
     * @throws RepositoryException
     */
    CertificationCourseModelEntity get(final String caWallet, final String courseId) throws RepositoryException;

    /**
     * Get Certification Course
     *
     * @param courseId
     * @return
     * @throws RepositoryException
     */
    CertificationCourseModelEntity get(final String courseId) throws RepositoryException;

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
    CertificationCourseModelEntity update(final String caWallet, final CertificationCourseModelEntity model) throws RepositoryException;

}
