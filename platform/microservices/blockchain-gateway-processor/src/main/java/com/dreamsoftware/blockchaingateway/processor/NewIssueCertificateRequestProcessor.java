package com.dreamsoftware.blockchaingateway.processor;

import com.dreamsoftware.blockchaingateway.model.OnNewCertificateIssuedEvent;
import com.dreamsoftware.blockchaingateway.model.OnNewIssueCertificateRequestEvent;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.ITrustCertificationBlockchainRepository;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.CertificateIssuedEntity;
import com.dreamsoftware.blockchaingateway.persistence.exception.RepositoryException;
import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.CertificationCourseEntity;
import com.dreamsoftware.blockchaingateway.persistence.nosql.repository.CertificationCourseRepository;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * New Issue Certificate Request Processor
 *
 * @author ssanchez
 */
@Component("newIssueCertificateRequestProcessor")
@RequiredArgsConstructor
public class NewIssueCertificateRequestProcessor implements Function<OnNewIssueCertificateRequestEvent, OnNewCertificateIssuedEvent> {

    private final Logger logger = LoggerFactory.getLogger(NewIssueCertificateRequestProcessor.class);

    private final CertificationCourseRepository certificationCourseRepository;
    private final ITrustCertificationBlockchainRepository trustCertificationBlockchainRepository;

    @Override
    public OnNewCertificateIssuedEvent apply(OnNewIssueCertificateRequestEvent event) {
        logger.debug("NewIssueCertificateRequestProcessor CALLED!");
        OnNewCertificateIssuedEvent nextEvent = null;
        try {
            final CertificationCourseEntity certificationCourseEntity = certificationCourseRepository.findOneByCourseId(event.getCourseId()).orElseThrow(() -> new IllegalStateException("Certification Course not found"));
            final String caWalletHash = certificationCourseEntity.getCa().getWalletHash();
            final CertificateIssuedEntity certificateIssuedEntity = trustCertificationBlockchainRepository.issueCertificate(caWalletHash, event.getStudentWalletHash(), event.getCourseId(), event.getQualification());
            nextEvent = OnNewCertificateIssuedEvent
                    .builder()
                    .caWalletHash(caWalletHash)
                    .certificateId(certificateIssuedEntity.getId())
                    .studentWalletHash(event.getStudentWalletHash())
                    .build();
        } catch (RepositoryException ex) {
            logger.debug("Ex Message -> " + ex.getMessage());
        }
        return nextEvent;
    }

}