package com.dreamsoftware.blockchaingateway.services.impl;

import com.dreamsoftware.blockchaingateway.mapper.CertificateIssuedMapper;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.ITrustCertificationBlockchainRepository;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.CertificateIssuedEntity;
import com.dreamsoftware.blockchaingateway.services.ITrustCertificationService;
import com.dreamsoftware.blockchaingateway.web.dto.response.CertificateIssuedDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 *
 * @author ssanchez
 */
@Service
@RequiredArgsConstructor
public class TrustCertificationServiceImpl implements ITrustCertificationService {

    private static final Logger logger = LoggerFactory.getLogger(TrustCertificationServiceImpl.class);

    private final CertificateIssuedMapper certificateIssuedMapper;
    private final ITrustCertificationBlockchainRepository trustCertificationRepository;

    /**
     *
     * @param ownerWallet
     * @param certificationId
     * @return
     * @throws Throwable
     */
    @Override
    public CertificateIssuedDTO enable(String ownerWallet, String certificationId) throws Throwable {
        Assert.notNull(ownerWallet, "ownerWallet can not be null");
        Assert.notNull(certificationId, "certificationId can not be null");
        final CertificateIssuedEntity certificate = trustCertificationRepository.enable(ownerWallet, certificationId);
        return certificateIssuedMapper.certificateIssuedEntityToCertificateIssued(certificate);
    }

    /**
     *
     * @param ownerWallet
     * @param certificationId
     * @return
     * @throws Throwable
     */
    @Override
    public CertificateIssuedDTO disable(String ownerWallet, String certificationId) throws Throwable {
        Assert.notNull(ownerWallet, "ownerWallet can not be null");
        Assert.notNull(certificationId, "certificationId can not be null");
        final CertificateIssuedEntity certificate = trustCertificationRepository.disable(ownerWallet, certificationId);
        return certificateIssuedMapper.certificateIssuedEntityToCertificateIssued(certificate);
    }

    /**
     *
     * @param ownerWallet
     * @param certificationId
     * @param isVisible
     * @return
     * @throws Throwable
     */
    @Override
    public CertificateIssuedDTO updateVisibility(String ownerWallet, String certificationId, Boolean isVisible) throws Throwable {
        Assert.notNull(ownerWallet, "ownerWallet can not be null");
        Assert.notNull(certificationId, "certificationId can not be null");
        final CertificateIssuedEntity certificate = trustCertificationRepository.updateCertificateVisibility(ownerWallet, certificationId, isVisible);
        return certificateIssuedMapper.certificateIssuedEntityToCertificateIssued(certificate);
    }

    /**
     *
     * @param ownerWallet
     * @param certificationId
     * @return
     * @throws Throwable
     */
    @Override
    public CertificateIssuedDTO getDetail(String ownerWallet, String certificationId) throws Throwable {
        Assert.notNull(ownerWallet, "ownerWallet can not be null");
        Assert.notNull(certificationId, "certificationId can not be null");
        final CertificateIssuedEntity certificate = trustCertificationRepository.getCertificateDetail(ownerWallet, certificationId);
        return certificateIssuedMapper.certificateIssuedEntityToCertificateIssued(certificate);
    }

    /**
     *
     * @param ownerWallet
     * @param certificationId
     * @return
     * @throws Throwable
     */
    @Override
    public Boolean isCertificateValid(String ownerWallet, String certificationId) throws Throwable {
        Assert.notNull(ownerWallet, "ownerWallet can not be null");
        Assert.notNull(certificationId, "certificationId can not be null");
        return trustCertificationRepository.isCertificateValid(ownerWallet, certificationId);
    }

    /**
     *
     * @param ownerWallet
     * @return
     * @throws Throwable
     */
    @Override
    public Iterable<CertificateIssuedDTO> getMyCertificatesAsRecipient(String ownerWallet) throws Throwable {
        Assert.notNull(ownerWallet, "ownerWallet can not be null");
        final List<CertificateIssuedEntity> myCertificates = trustCertificationRepository.getMyCertificatesAsRecipient(ownerWallet);
        return certificateIssuedMapper.certificateIssuedEntityToCertificateIssued(myCertificates);
    }

    /**
     *
     * @param ownerWallet
     * @return
     * @throws Throwable
     */
    @Override
    public Iterable<CertificateIssuedDTO> getMyCertificatesAsIssuer(String ownerWallet) throws Throwable {
        Assert.notNull(ownerWallet, "ownerWallet can not be null");
        final List<CertificateIssuedEntity> myCertificates = trustCertificationRepository.getMyCertificatesAsIssuer(ownerWallet);
        return certificateIssuedMapper.certificateIssuedEntityToCertificateIssued(myCertificates);
    }

    /**
     *
     * @param issuerWallet
     * @param recipientAddress
     * @param certificateCourseId
     * @param qualification
     * @throws Throwable
     */
    @Override
    public void issueCertificate(String issuerWallet, String recipientAddress, String certificateCourseId, Long qualification) throws Throwable {
        Assert.notNull(issuerWallet, "issuerWallet can not be null");
        Assert.notNull(recipientAddress, "recipientAddress can not be null");
        Assert.notNull(certificateCourseId, "certificateCourseId can not be null");
        Assert.notNull(qualification, "qualification can not be null");
        trustCertificationRepository.issueCertificate(issuerWallet, recipientAddress, certificateCourseId, qualification);
    }

    /**
     *
     * @param ownerWallet
     * @param certificationId
     * @return
     * @throws Throwable
     */
    @Override
    public CertificateIssuedDTO renewCertificate(String ownerWallet, String certificationId) throws Throwable {
        Assert.notNull(ownerWallet, "ownerWallet can not be null");
        Assert.notNull(certificationId, "certificationId can not be null");
        final CertificateIssuedEntity certificate = trustCertificationRepository.renewCertificate(ownerWallet, certificationId);
        return certificateIssuedMapper.certificateIssuedEntityToCertificateIssued(certificate);
    }
}
