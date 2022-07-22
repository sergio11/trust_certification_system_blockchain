package com.dreamsoftware.blockchaingateway.persistence.bc.repository;

import com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.CertificationCourseEntity;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.CertificationCourseEventEntity;
import com.dreamsoftware.blockchaingateway.persistence.exception.RepositoryException;
import io.reactivex.Flowable;

/**
 *
 * @author ssanchez
 */
public interface ICertificationCourseBlockchainRepository {

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
    CertificationCourseEntity register(final String walletHash, final String name, final Long costOfIssuingCertificate,
            final Long durationInHours, final Long expirationInDays, final Boolean canBeRenewed, final Long costOfRenewingCertificate) throws RepositoryException;

    /**
     * Enable Certification Course
     *
     * @param caWallet
     * @param courseId
     * @return
     * @throws RepositoryException
     */
    CertificationCourseEntity enable(final String caWallet, final String courseId) throws RepositoryException;

    /**
     * Disable Certification Course
     *
     * @param caWallet
     * @param courseId
     * @return
     * @throws RepositoryException
     */
    CertificationCourseEntity disable(final String caWallet, final String courseId) throws RepositoryException;

    /**
     * Remove Certification Course
     *
     * @param caWallet
     * @param courseId
     * @return
     * @throws RepositoryException
     */
    CertificationCourseEntity remove(final String caWallet, final String courseId) throws RepositoryException;

    /**
     * Get Certification Course
     *
     * @param caWallet
     * @param courseId
     * @return
     * @throws RepositoryException
     */
    CertificationCourseEntity get(final String caWallet, final String courseId) throws RepositoryException;

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
     * @return @throws RepositoryException
     */
    Flowable<CertificationCourseEventEntity> getEvents() throws RepositoryException;

}
