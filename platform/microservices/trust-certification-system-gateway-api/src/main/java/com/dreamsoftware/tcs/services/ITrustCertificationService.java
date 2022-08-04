package com.dreamsoftware.tcs.services;

import com.dreamsoftware.tcs.web.dto.request.IssueCertificateRequestDTO;
import com.dreamsoftware.tcs.web.dto.response.CertificateIssuanceRequestDTO;
import com.dreamsoftware.tcs.web.dto.response.CertificateIssuedDTO;
import org.bson.types.ObjectId;

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
     * Issue Certificate Request
     *
     * @param issueCertificate
     * @return
     * @throws Throwable
     */
    CertificateIssuanceRequestDTO issueCertificateRequest(final IssueCertificateRequestDTO issueCertificate) throws Throwable;

    /**
     * Accept request for certificate issuance
     *
     * @param id
     * @return
     */
    CertificateIssuanceRequestDTO acceptCertificateRequest(final ObjectId id);

    /**
     * Reject request for certificate issuance
     *
     * @param id
     * @return
     */
    CertificateIssuanceRequestDTO rejectCertificateRequest(final ObjectId id);

    /**
     *
     * @param studentWalletHash
     * @return
     */
    Iterable<CertificateIssuanceRequestDTO> getCertificatesIssuanceRequestsFromStudent(final String studentWalletHash);

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
