package com.dreamsoftware.tcs.processor;

import com.dreamsoftware.tcs.model.events.OnNewCertificateIssuedEvent;
import com.dreamsoftware.tcs.model.events.OnNewIssueCertificateRequestEvent;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificateIssuedBcEntity;
import com.dreamsoftware.tcs.service.ITrustCertificateService;
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

    /**
     * Trust Certification Service
     */
    private final ITrustCertificateService trustCertificateService;

    @Override
    public OnNewCertificateIssuedEvent apply(OnNewIssueCertificateRequestEvent event) {
        logger.debug("NewIssueCertificateRequestProcessor CALLED!");
        OnNewCertificateIssuedEvent nextEvent = null;
        try {
            final CertificateIssuedBcEntity certificateIssuedEntity = trustCertificateService.issueCertificate(event);
            nextEvent = OnNewCertificateIssuedEvent
                    .builder()
                    .caWalletHash(event.getCaWalletHash())
                    .certificateId(certificateIssuedEntity.getId())
                    .studentWalletHash(event.getStudentWalletHash())
                    .build();
        } catch (Exception ex) {
            logger.debug("Ex Message -> " + ex.getMessage());
        }
        return nextEvent;
    }

}
