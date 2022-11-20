package com.dreamsoftware.tcs.processor.handlers;

import com.dreamsoftware.tcs.model.CertificateGenerated;
import com.dreamsoftware.tcs.model.CertificationGenerationRequest;
import com.dreamsoftware.tcs.persistence.bc.repository.ITrustCertificationBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificateIssuedEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.*;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificateIssuanceRequestRepository;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseRepository;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.service.ICertificateGeneratorService;
import com.dreamsoftware.tcs.service.IipfsGateway;
import com.dreamsoftware.tcs.stream.events.AbstractEvent;
import com.dreamsoftware.tcs.stream.events.certificate.OnNewIssueCertificateRequestEvent;
import com.dreamsoftware.tcs.stream.events.notifications.certificate.CertificateIssuedNotificationEvent;
import com.dreamsoftware.tcs.utils.AbstractProcessAndReturnHandler;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import java.util.Date;

/**
 * New Issue Certificate Request Handler
 *
 * @author ssanchez
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
@Slf4j
public class NewIssueCertificateRequestHandler extends AbstractProcessAndReturnHandler<OnNewIssueCertificateRequestEvent> {

    /**
     * Certification Course Repository
     */
    private final CertificationCourseRepository certificationCourseRepository;

    /**
     * User Repository
     */
    private final UserRepository userRepository;

    /**
     * Certificate Generator Service
     */
    private final ICertificateGeneratorService certificateGeneratorService;

    /**
     * IPFS Gateway
     */
    private final IipfsGateway iipfsGateway;

    /**
     * Trust Certification Blockchain Repository
     */
    private final ITrustCertificationBlockchainRepository trustCertificationBlockchainRepository;

    private final CertificateIssuanceRequestRepository certificateIssuanceRequestRepository;

    @Override
    public AbstractEvent onHandle(final OnNewIssueCertificateRequestEvent event) throws Exception {
        Assert.notNull(event, "Event can not be null");
        log.debug("issueCertificate - CA Wallet: " + event.getCaWalletHash());
        log.debug("issueCertificate - Student Wallet: " + event.getStudentWalletHash());
        log.debug("issueCertificate - Course ID: " + event.getCourseId());

        final CertificationCourseEntity certificationCourseEntity = certificationCourseRepository.findById(new ObjectId(event.getCourseId()))
                .orElseThrow(() -> new IllegalStateException("Course not found"));
        final CertificationAuthorityEntity certificationAuthorityEntity = certificationCourseEntity.getCa();
        final String studentName = userRepository.findOneByWalletHash(event.getStudentWalletHash())
                .map(UserEntity::getFullName).orElseThrow(() -> new IllegalStateException("Student not found"));
        // Generate new certificate using the data provide by event
        final CertificateGenerated certificateGenerated = certificateGeneratorService.generate(CertificationGenerationRequest.builder()
                .caName(certificationAuthorityEntity.getName())
                .caWalletHash(event.getCaWalletHash())
                .studentName(studentName)
                .studentWalletHash(event.getStudentWalletHash())
                .courseName(certificationCourseEntity.getName())
                .courseId(event.getCourseId())
                .qualification(event.getQualification())
                .build());
        // Save Certificate in IPFS node
        final String fileCertificateCid = iipfsGateway.save(certificateGenerated.getFile(), true);
        // Save Certificate Image in IPFS node
        final String imageCertificateCid = iipfsGateway.save(certificateGenerated.getImage(), true);
        // Generate SHA 256 from file
        String fileCertificateHash = Files.asByteSource(certificateGenerated.getFile()).hash(Hashing.sha256()).toString();
        // Generate SHA 256 from image
        String imageCertificateHash = Files.asByteSource(certificateGenerated.getImage()).hash(Hashing.sha256()).toString();
        log.debug("fileCertificateHash -> " + fileCertificateHash);
        log.debug("imageCertificateHash -> " + imageCertificateHash);

        final CertificateIssuedEntity certificateIssuedEntity = trustCertificationBlockchainRepository.issueCertificate(certificateGenerated.getId(), event.getCaWalletHash(), event.getStudentWalletHash(), event.getCourseId(),
                event.getQualification(), fileCertificateCid, fileCertificateHash, imageCertificateCid, imageCertificateHash);

        final CertificateIssuanceRequestEntity certificateRequest = certificateIssuanceRequestRepository.findById(new ObjectId(event.getCertificationRequestId()))
                .orElseThrow(() -> new IllegalStateException("Certification Issuance Request can not be found"));
        certificateRequest.setStatus(CertificateStatusEnum.VALIDATED);
        certificateRequest.setCertificateId(certificateIssuedEntity.getId());
        certificateRequest.setUpdatedAt(new Date());
        certificateIssuanceRequestRepository.save(certificateRequest);
        return CertificateIssuedNotificationEvent.builder()
                .id(certificateIssuedEntity.getId())
                .build();
    }
}
