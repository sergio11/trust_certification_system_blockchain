package com.dreamsoftware.tcs.services.impl;

import com.dreamsoftware.tcs.config.properties.StreamChannelsProperties;
import com.dreamsoftware.tcs.mapper.CertificateIssuedMapper;
import com.dreamsoftware.tcs.model.OnNewIssueCertificateRequestEvent;
import com.dreamsoftware.tcs.persistence.bc.repository.ITrustCertificationBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificateIssuedEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.services.ITrustCertificationService;
import com.dreamsoftware.tcs.web.dto.request.IssueCertificateDTO;
import com.dreamsoftware.tcs.web.dto.response.CertificateIssuedDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
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
    private final UserRepository userRepository;
    private final ITrustCertificationBlockchainRepository trustCertificationRepository;
    private final StreamBridge streamBridge;
    private final StreamChannelsProperties streamChannelsProperties;

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
     * @param issueCertificate
     * @throws Throwable
     */
    @Override
    public void issueCertificate(IssueCertificateDTO issueCertificate) throws Throwable {
        Assert.notNull(issueCertificate.getStudentWalletHash(), "Student Wallet Hash can not be null");
        Assert.notNull(issueCertificate.getCertificateCourseId(), "certificateCourseId can not be null");
        Assert.notNull(issueCertificate.getQualification(), "qualification can not be null");
        final OnNewIssueCertificateRequestEvent event = OnNewIssueCertificateRequestEvent
                .builder()
                .courseId(issueCertificate.getCertificateCourseId())
                .qualification(issueCertificate.getQualification())
                .studentWalletHash(issueCertificate.getStudentWalletHash())
                .build();
        streamBridge.send(streamChannelsProperties.getNewCertificationRequest(), event);
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
