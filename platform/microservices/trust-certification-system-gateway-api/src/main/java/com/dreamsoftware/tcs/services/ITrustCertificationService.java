package com.dreamsoftware.tcs.services;

import com.dreamsoftware.tcs.web.core.FileInfoDTO;
import com.dreamsoftware.tcs.web.dto.request.AcceptCertificateRequestDTO;
import com.dreamsoftware.tcs.web.dto.request.IssueCertificateRequestDTO;
import com.dreamsoftware.tcs.web.dto.response.CertificateIssuanceRequestDTO;
import com.dreamsoftware.tcs.web.dto.response.CertificateIssuedDetailDTO;
import com.dreamsoftware.tcs.web.dto.response.SimpleCertificateIssuedDTO;

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
    void enable(final String ownerWallet, final String certificationId) throws Throwable;

    /**
     *
     * @param ownerWallet
     * @param certificationId
     * @return
     * @throws Throwable
     */
    void disable(final String ownerWallet, final String certificationId) throws Throwable;

    /**
     * Update Visibility
     *
     * @param ownerWallet
     * @param certificationId
     * @param isVisible
     * @return
     * @throws Throwable
     */
    void updateVisibility(final String ownerWallet, final String certificationId, final Boolean isVisible) throws Throwable;


    /**
     *
     * @param certificationId
     * @return
     * @throws Throwable
     */
    CertificateIssuedDetailDTO getDetail(final String certificationId) throws Throwable;

    /**
     *
     * @param ownerId
     * @return
     * @throws Throwable
     */
    Iterable<SimpleCertificateIssuedDTO> getMyCertificatesAsRecipient(final String ownerId) throws Throwable;

    /**
     *
     * @param ownerId
     * @return
     * @throws Throwable
     */
    Iterable<SimpleCertificateIssuedDTO> getMyCertificatesAsIssuer(final String ownerId) throws Throwable;

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
     * @param acceptCertificateRequestDTO
     * @return
     */
    CertificateIssuanceRequestDTO acceptCertificateRequest(final AcceptCertificateRequestDTO acceptCertificateRequestDTO);

    /**
     * Reject request for certificate issuance
     *
     * @param id
     * @return
     */
    CertificateIssuanceRequestDTO rejectCertificateRequest(final String id);

    /**
     *
     * @param studentId
     * @return
     */
    Iterable<CertificateIssuanceRequestDTO> getCertificatesIssuanceRequestsFromStudent(final String studentId);

    /**
     *
     * @param caWalletHash
     * @return
     */
    Iterable<CertificateIssuanceRequestDTO> getCertificatesIssuanceRequestsFromCa(final String caWalletHash);

    /**
     * Renew Certificate
     *
     * @param ownerWallet
     * @param certificationId
     * @return
     * @throws Throwable
     */
    CertificateIssuedDetailDTO renewCertificate(final String ownerWallet, final String certificationId) throws Throwable;

    /**
     *
     * @param certificateId
     * @return
     * @throws Exception
     */
    FileInfoDTO getCertificateFile(final String certificateId) throws Exception;

    /**
     *
     * @param certificateId
     * @param width
     * @param height
     * @return
     * @throws Exception
     */
    FileInfoDTO generateCertificateQr(final String certificateId, final Integer width, final Integer height) throws Exception;

    /**
     *
     * @param certificateId
     * @return
     * @throws Exception
     */
    FileInfoDTO getCertificateImage(final String certificateId) throws Exception;

    /**
     *
     * @param certificatePayload
     * @return
     * @throws Exception
     */
    CertificateIssuedDetailDTO validateCertificate(final String certificatePayload) throws Exception;
}
