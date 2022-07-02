package com.dreamsoftware.blockchaingateway.services;

import com.dreamsoftware.blockchaingateway.web.dto.response.CertificateIssuedDTO;

/**
 *
 * @author ssanchez
 */
public interface ITrustCertificationService {

    /**
     *
     * @param ownerWallet
     * @param certificationId
     * @return
     * @throws Throwable
     */
    CertificateIssuedDTO enable(final String ownerWallet, final String certificationId) throws Throwable;

    /**
     *
     * @param ownerWallet
     * @param certificationId
     * @return
     * @throws Throwable
     */
    CertificateIssuedDTO disable(final String ownerWallet, final String certificationId) throws Throwable;

    /**
     * Update Visibility
     *
     * @param ownerWallet
     * @param certificationId
     * @param isVisible
     * @return
     * @throws Throwable
     */
    CertificateIssuedDTO updateVisibility(final String ownerWallet, final String certificationId, final Boolean isVisible) throws Throwable;

    /**
     *
     * @param ownerWallet
     * @param certificationId
     * @return
     * @throws Throwable
     */
    Boolean isCertificateValid(final String ownerWallet, final String certificationId) throws Throwable;

    /**
     *
     * @param ownerWallet
     * @param certificationId
     * @return
     * @throws Throwable
     */
    CertificateIssuedDTO getDetail(final String ownerWallet, final String certificationId) throws Throwable;

    /**
     *
     * @param ownerWallet
     * @return
     * @throws Throwable
     */
    Iterable<CertificateIssuedDTO> getMyCertificatesAsRecipient(final String ownerWallet) throws Throwable;

    /**
     *
     * @param ownerWallet
     * @return
     * @throws Throwable
     */
    Iterable<CertificateIssuedDTO> getMyCertificatesAsIssuer(final String ownerWallet) throws Throwable;

    /**
     * Issue Certificate
     *
     * @param issuerWallet
     * @param recipientAddress
     * @param certificateCourseId
     * @param qualification
     * @throws Throwable
     */
    void issueCertificate(final String issuerWallet,
            final String recipientAddress, final String certificateCourseId, final Long qualification) throws Throwable;

    /**
     * Renew Certificate
     *
     * @param ownerWallet
     * @param certificationId
     * @return
     * @throws Throwable
     */
    CertificateIssuedDTO renewCertificate(final String ownerWallet, final String certificationId) throws Throwable;
}
