package com.dreamsoftware.blockchaingateway.persistence.bc.repository;

import com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.CertificationCourseEntity;
import com.dreamsoftware.blockchaingateway.persistence.exception.RepositoryException;

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
     * @throws RepositoryException
     */
    void register(final String walletHash, final String name, final Long costOfIssuingCertificate,
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
}
