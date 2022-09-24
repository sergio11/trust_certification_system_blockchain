package com.dreamsoftware.tcs.service.impl;

import com.dreamsoftware.tcs.model.CertificateGenerated;
import com.dreamsoftware.tcs.model.CertificationGenerationRequest;
import com.dreamsoftware.tcs.stream.events.certificate.OnNewCertificateIssuedEvent;
import com.dreamsoftware.tcs.stream.events.certificate.OnNewIssueCertificateRequestEvent;
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
import com.dreamsoftware.tcs.service.ITrustCertificateService;
import com.dreamsoftware.tcs.service.IipfsGateway;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificateIssuanceRequestRepository;
import com.dreamsoftware.tcs.service.ICertificateGeneratorService;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author ssanchez
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TrustCertificateServiceImpl implements ITrustCertificateService {

    /**
     * Certificate Generator
     */
    private final ICertificateGeneratorService certificateGenerator;

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
     *
     * @param event
     * @return
     */
    @Override
    public CertificateIssuedEntity issueCertificate(OnNewIssueCertificateRequestEvent event) throws Exception {
        Assert.notNull(event, "Event can not be null");
        log.debug("issueCertificate - CA Wallet: " + event.getCaWalletHash());
        log.debug("issueCertificate - Student Wallet: " + event.getStudentWalletHash());
        log.debug("issueCertificate - Course ID: " + event.getCourseId());
        final CertificationCourseModelEntity certificationCourseModelEntity = certificationCourseBlockchainRepository.get(event.getCourseId());
        final CertificationAuthorityEntity certificationAuthorityEntity = certificationAuthorityBlockchainRepository.getDetail(event.getCaWalletHash());
        final String studentName = userRepository.findOneByWalletHash(event.getStudentWalletHash()).map(UserEntity::getFullName).orElseThrow(() -> new IllegalStateException("Student not found"));
        // Generate new certificate using the data provide by event
        final CertificateGenerated certificateGenerated = certificateGenerator.generate(CertificationGenerationRequest.builder()
                .caName(certificationAuthorityEntity.getName())
                .caWalletHash(event.getCaWalletHash())
                .studentName(studentName)
                .studentWalletHash(event.getStudentWalletHash())
                .courseName(certificationCourseModelEntity.getName())
                .courseId(event.getCourseId())
                .qualification(event.getQualification())
                .build());
        // Save Certificate in IPFS node
        final String fileCertificateCid = ipfsService.save(certificateGenerated.getFile(), true);
        // Save Certificate Image in IPFS node
        final String iamgeCertificateCid = ipfsService.save(certificateGenerated.getImage(), true);
        // Generate SHA 256 from file
        String fileCertificateHash = Files.asByteSource(certificateGenerated.getFile()).hash(Hashing.sha256()).toString();
        // Generate SHA 256 from image
        String imageCertificateHash = Files.asByteSource(certificateGenerated.getImage()).hash(Hashing.sha256()).toString();
        return trustCertificationBlockchainRepository.issueCertificate(certificateGenerated.getId(), event.getCaWalletHash(), event.getStudentWalletHash(), event.getCourseId(),
                event.getQualification(), fileCertificateCid, fileCertificateHash, iamgeCertificateCid, imageCertificateHash);
    }

    /**
     *
     * @param event
     */
    @Override
    public CertificateIssuanceRequestEntity saveCertificate(final OnNewCertificateIssuedEvent event) {
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
        return certificateIssuedRepository.save(certificateIssued);
    }

}
