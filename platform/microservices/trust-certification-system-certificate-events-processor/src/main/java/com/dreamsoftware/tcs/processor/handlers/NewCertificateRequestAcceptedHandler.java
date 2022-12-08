package com.dreamsoftware.tcs.processor.handlers;

import com.dreamsoftware.tcs.model.CertificateGenerated;
import com.dreamsoftware.tcs.model.CertificationGenerationRequest;
import com.dreamsoftware.tcs.persistence.bc.repository.ITrustCertificationBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificateIssuedEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificateIssuanceRequestEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificateStatusEnum;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEditionEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificateIssuanceRequestRepository;
import com.dreamsoftware.tcs.service.ICertificateGeneratorService;
import com.dreamsoftware.tcs.service.IipfsGateway;
import com.dreamsoftware.tcs.stream.events.AbstractEvent;
import com.dreamsoftware.tcs.stream.events.certificate.OnNewCertificateRequestAcceptedEvent;
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
public class NewCertificateRequestAcceptedHandler extends AbstractProcessAndReturnHandler<OnNewCertificateRequestAcceptedEvent> {

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
    public AbstractEvent onHandle(final OnNewCertificateRequestAcceptedEvent event) throws Exception {
        Assert.notNull(event, "Event can not be null");
        log.debug("Certification Request Id " + event.getCertificationRequestId());
        final CertificateIssuanceRequestEntity certificateRequest = certificateIssuanceRequestRepository.findById(new ObjectId(event.getCertificationRequestId()))
                .orElseThrow(() -> new IllegalStateException("Certification Issuance Request can not be found"));

        final UserEntity studentEntity = certificateRequest.getStudent();
        final UserEntity caMemberEntity = certificateRequest.getCaMember();
        final CertificationCourseEditionEntity courseEditionEntity = certificateRequest.getCourseEdition();
        final String courseName = courseEditionEntity.getName().isBlank() ? courseEditionEntity.getCourse().getName() : courseEditionEntity.getName();
        final String executiveDirector = caMemberEntity.getCa().getExecutiveDirector();
        // Generate new certificate using the data provide by event
        final CertificateGenerated certificateGenerated = certificateGeneratorService.generate(CertificationGenerationRequest.builder()
                .caName(caMemberEntity.getCa().getName())
                .studentName(studentEntity.getFullName())
                .type(certificateRequest.getType())
                .courseName(courseName)
                .qualification(certificateRequest.getQualification())
                .executiveDirector(executiveDirector)
                .build());
        // Generate SHA 256 from file
        String fileCertificateHash = Files.asByteSource(certificateGenerated.getFile()).hash(Hashing.sha256()).toString();
        // Generate SHA 256 from image
        String imageCertificateHash = Files.asByteSource(certificateGenerated.getImage()).hash(Hashing.sha256()).toString();
        // Save Certificate in IPFS node
        final String fileCertificateCid = iipfsGateway.save(certificateGenerated.getFile(), true);
        // Save Certificate Image in IPFS node
        final String imageCertificateCid = iipfsGateway.save(certificateGenerated.getImage(), true);
        log.debug("fileCertificateHash -> " + fileCertificateHash);
        log.debug("imageCertificateHash -> " + imageCertificateHash);
        log.debug("fileCertificateCid -> " + fileCertificateCid);
        log.debug("imageCertificateCid -> " + imageCertificateCid);

        final CertificateIssuedEntity certificateIssuedEntity = trustCertificationBlockchainRepository.issueCertificate(
                certificateGenerated.getId(),
                caMemberEntity.getWalletHash(),
                studentEntity.getWalletHash(),
                courseEditionEntity.getId().toString(),
                certificateRequest.getQualification(),
                fileCertificateCid,
                fileCertificateHash,
                imageCertificateCid,
                imageCertificateHash);
        
        certificateRequest.setStatus(CertificateStatusEnum.VALIDATED);
        certificateRequest.setCertificateId(certificateIssuedEntity.getId());
        certificateRequest.setUpdatedAt(new Date());
        certificateIssuanceRequestRepository.save(certificateRequest);
        return CertificateIssuedNotificationEvent.builder()
                .id(certificateIssuedEntity.getId())
                .build();
    }
}
