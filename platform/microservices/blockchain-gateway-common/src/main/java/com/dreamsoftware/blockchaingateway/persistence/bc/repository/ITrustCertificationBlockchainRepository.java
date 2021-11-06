package com.dreamsoftware.blockchaingateway.persistence.bc.repository;

import com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.CertificateIssuedEntity;
import com.dreamsoftware.blockchaingateway.persistence.exception.RepositoryException;
import java.util.List;

/**
 *
 * @author ssanchez
 */
public interface ITrustCertificationBlockchainRepository {

    /**
     * Issue Certificate
     *
     * @param issuerWallet
     * @param recipientAddress
     * @param certificateCourseId
     * @param qualification
     * @throws RepositoryException
     */
    void issueCertificate(final String issuerWallet,
            final String recipientAddress, final String certificateCourseId, final Long qualification) throws RepositoryException;

    /**
     * Renew Certificate
     *
     * @param ownerWallet
     * @param certificationId
     * @return
     * @throws RepositoryException
     */
    CertificateIssuedEntity renewCertificate(final String ownerWallet, final String certificationId) throws RepositoryException;

    /**
     * Enable Certification
     *
     * @param ownerWallet
     * @param certificationId
     * @return
     * @throws RepositoryException
     */
    CertificateIssuedEntity enable(final String ownerWallet, final String certificationId) throws RepositoryException;

    /**
     * Disable Certification
     *
     * @param ownerWallet
     * @param certificationId
     * @return
     * @throws RepositoryException
     */
    CertificateIssuedEntity disable(final String ownerWallet, final String certificationId) throws RepositoryException;

    /**
     * Update Certificate Visibility
     *
     * @param ownerWallet
     * @param certificationId
     * @param isVisible
     * @return
     * @throws RepositoryException
     */
    CertificateIssuedEntity updateCertificateVisibility(final String ownerWallet, final String certificationId, final Boolean isVisible) throws RepositoryException;

    /**
     * Is Certificate Valid
     *
     * @param wallet
     * @param certificationId
     * @return
     * @throws RepositoryException
     */
    Boolean isCertificateValid(final String wallet, final String certificationId) throws RepositoryException;

    /**
     * Get Certificate Detail
     *
     * @param wallet
     * @param certificationId
     * @return
     * @throws RepositoryException
     */
    CertificateIssuedEntity getCertificateDetail(final String wallet, final String certificationId) throws RepositoryException;

    /**
     * Get My Certificates as Recipient
     *
     * @param ownerWallet
     * @return
     * @throws RepositoryException
     */
    List<CertificateIssuedEntity> getMyCertificatesAsRecipient(final String ownerWallet) throws RepositoryException;

    /**
     * Get My Certificates as issuer
     *
     * @param ownerWallet
     * @return
     * @throws RepositoryException
     */
    List<CertificateIssuedEntity> getMyCertificatesAsIssuer(final String ownerWallet) throws RepositoryException;

}
