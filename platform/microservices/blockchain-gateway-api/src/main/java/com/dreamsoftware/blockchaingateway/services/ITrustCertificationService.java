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
}
