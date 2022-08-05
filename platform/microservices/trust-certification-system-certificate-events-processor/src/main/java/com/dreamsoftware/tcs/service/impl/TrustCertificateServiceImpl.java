package com.dreamsoftware.tcs.service.impl;

import com.dreamsoftware.tcs.model.events.OnNewCertificateIssuedEvent;
import com.dreamsoftware.tcs.model.events.OnNewIssueCertificateRequestEvent;
import com.dreamsoftware.tcs.persistence.bc.repository.ICertificationAuthorityBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.ICertificationCourseBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.ITrustCertificationBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificateIssuedEntity;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationAuthorityEntity;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationCourseModelEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificateIssuanceRequestEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificateStatusEnum;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.service.ICertificateGenerator;
import com.dreamsoftware.tcs.service.ITrustCertificateService;
import com.dreamsoftware.tcs.service.IipfsGateway;
import java.io.File;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificateIssuanceRequestRepository;
import com.dreamsoftware.tcs.service.INotificationService;

/**
 *
 * @author ssanchez
 */
@Service
@RequiredArgsConstructor
public class TrustCertificateServiceImpl implements ITrustCertificateService {

    private static final Logger logger = LoggerFactory.getLogger(TrustCertificateServiceImpl.class);

    /**
     * Certificate Generator
     */
    private final ICertificateGenerator certificateGenerator;

    /**
     * Certification Course Blockchain Repository
     */
    private final ICertificationCourseBlockchainRepository certificationCourseBlockchainRepository;

    /**
     * Certification Authority Blockchain Repository
     */
    private final ICertificationAuthorityBlockchainRepository certificationAuthorityBlockchainRepository;

    /**
     * User Repository
     */
    private final UserRepository userRepository;

    /**
     * IPFS Service
     */
    private final IipfsGateway ipfsService;

    /**
     * Trust Certification Blockchain Repository
     */
    private final ITrustCertificationBlockchainRepository trustCertificationBlockchainRepository;

    /**
     * Certificate Issued Repository
     */
    private final CertificateIssuanceRequestRepository certificateIssuedRepository;

    /**
     * Notification Service
     */
    private final INotificationService notificationService;

    /**
     *
     * @param event
     * @return
     */
    @Override
    public CertificateIssuedEntity issueCertificate(OnNewIssueCertificateRequestEvent event) throws Exception {
        Assert.notNull(event, "Event can not be null");
        logger.debug("issueCertificate - CA Wallet: " + event.getCaWalletHash());
        logger.debug("issueCertificate - Student Wallet: " + event.getStudentWalletHash());
        logger.debug("issueCertificate - Course ID: " + event.getCourseId());
        final CertificationCourseModelEntity certificationCourseModelEntity = certificationCourseBlockchainRepository.get(event.getCourseId());
        final CertificationAuthorityEntity certificationAuthorityEntity = certificationAuthorityBlockchainRepository.getDetail(certificationCourseModelEntity.getCertificationAuthority());
        final String studentName = userRepository.findOneByWalletHash(event.getStudentWalletHash()).map(UserEntity::getName).orElseThrow(() -> new IllegalStateException("Student not found"));
        // Generate new certificate using the data provide by event
        final File certificateFile = certificateGenerator.generate(certificationAuthorityEntity.getName(), studentName, certificationCourseModelEntity.getName(), event.getQualification());
        // Save Certificate in IPFS node
        final String cid = ipfsService.save(certificateFile, true);
        return trustCertificationBlockchainRepository.issueCertificate(event.getCaWalletHash(), event.getStudentWalletHash(), event.getCourseId(), event.getQualification(), cid);
    }

    /**
     *
     * @param event
     */
    @Override
    public void saveCertificate(final OnNewCertificateIssuedEvent event) {
        Assert.notNull(event, "Event can not be null");
        final UserEntity caUserEntity = userRepository.findOneByWalletHash(event.getCaWalletHash()).orElseThrow(() -> new IllegalStateException("CA Wallet Hash not found"));
        final UserEntity studentEntity = userRepository.findOneByWalletHash(event.getStudentWalletHash()).orElseThrow(() -> new IllegalStateException("Student Wallet Hash not found"));
        final CertificateIssuanceRequestEntity certificateIssued = CertificateIssuanceRequestEntity.builder()
                .ca(caUserEntity)
                .student(studentEntity)
                .status(CertificateStatusEnum.VALIDATED)
                .certificateId(event.getCertificateId())
                .createdAt(new Date())
                .build();
        final CertificateIssuanceRequestEntity certificateIssuedSaved = certificateIssuedRepository.save(certificateIssued);
        notificationService.onUserCertificateValidated(certificateIssuedSaved);
    }

}
