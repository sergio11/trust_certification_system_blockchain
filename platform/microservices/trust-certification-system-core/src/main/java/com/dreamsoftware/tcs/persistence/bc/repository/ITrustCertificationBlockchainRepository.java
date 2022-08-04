package com.dreamsoftware.tcs.persistence.bc.repository;

import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificateIssuedEntity;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.TrustCertificationEventEntity;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import java.util.List;

/**
 *
 * @author ssanchez
 */
public interface ITrustCertificationBlockchainRepository extends IBlockchainEventRepository<TrustCertificationEventEntity> {

    /**
     * Issue Certificate
     *
     * @param issuerWalletHash
     * @param studentWalletHash
     * @param certificateCourseId
     * @param qualification
     * @param cid
     * @return
     * @throws RepositoryException
     */
    CertificateIssuedEntity issueCertificate(final String issuerWalletHash,
            final String studentWalletHash, final String certificateCourseId, final Long qualification, final String cid) throws RepositoryException;

    /**
     * Renew Certificate
     *
     * @param studentWalletHash
     * @param certificationId
     * @return
     * @throws RepositoryException
     */
    CertificateIssuedEntity renewCertificate(final String studentWalletHash, final String certificationId) throws RepositoryException;

    /**
     * Enable Certification
     *
     * @param studentWalletHash
     * @param certificationId
     * @return
     * @throws RepositoryException
     */
    CertificateIssuedEntity enable(final String studentWalletHash, final String certificationId) throws RepositoryException;

    /**
     * Disable Certification
     *
     * @param studentWalletHash
     * @param certificationId
     * @return
     * @throws RepositoryException
     */
    CertificateIssuedEntity disable(final String studentWalletHash, final String certificationId) throws RepositoryException;

    /**
     * Update Certificate Visibility
     *
     * @param studentWalletHash
     * @param certificationId
     * @param isVisible
     * @return
     * @throws RepositoryException
     */
    CertificateIssuedEntity updateCertificateVisibility(final String studentWalletHash, final String certificationId, final Boolean isVisible) throws RepositoryException;

    /**
     * Is Certificate Valid
     *
     * @param walletHash
     * @param certificationId
     * @return
     * @throws RepositoryException
     */
    Boolean isCertificateValid(final String walletHash, final String certificationId) throws RepositoryException;

    /**
     * Get Certificate Detail
     *
     * @param walletHash
     * @param certificationId
     * @return
     * @throws RepositoryException
     */
    CertificateIssuedEntity getCertificateDetail(final String walletHash, final String certificationId) throws RepositoryException;

    /**
     * Get My Certificates as Recipient
     *
     * @param walletHash
     * @return
     * @throws RepositoryException
     */
    List<CertificateIssuedEntity> getMyCertificatesAsRecipient(final String walletHash) throws RepositoryException;

    /**
     * Get My Certificates as issuer
     *
     * @param walletHash
     * @return
     * @throws RepositoryException
     */
    List<CertificateIssuedEntity> getMyCertificatesAsIssuer(final String walletHash) throws RepositoryException;

}
