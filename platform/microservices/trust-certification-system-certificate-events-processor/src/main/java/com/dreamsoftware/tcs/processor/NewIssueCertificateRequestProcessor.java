package com.dreamsoftware.tcs.processor;

import com.dreamsoftware.tcs.stream.events.OnNewCertificateIssuedEvent;
import com.dreamsoftware.tcs.stream.events.OnNewIssueCertificateRequestEvent;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificateIssuedEntity;
import com.dreamsoftware.tcs.service.ITrustCertificateService;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * New Issue Certificate Request Processor
 *
 * @author ssanchez
 */
@Slf4j
@Component("newIssueCertificateRequestProcessor")
@RequiredArgsConstructor
public class NewIssueCertificateRequestProcessor implements Function<OnNewIssueCertificateRequestEvent, OnNewCertificateIssuedEvent> {

    /**
     * Trust Certification Service
     */
    private final ITrustCertificateService trustCertificateService;

    /**
     *
     * @param event
     * @return
     */
    @Override
    public OnNewCertificateIssuedEvent apply(final OnNewIssueCertificateRequestEvent event) {
        log.debug("NewIssueCertificateRequestProcessor CALLED!");
        OnNewCertificateIssuedEvent nextEvent = null;
        try {
            final CertificateIssuedEntity certificateIssuedEntity = trustCertificateService.issueCertificate(event);
            nextEvent = OnNewCertificateIssuedEvent
                    .builder()
                    .caWalletHash(event.getCaWalletHash())
                    .certificateId(certificateIssuedEntity.getId())
                    .studentWalletHash(event.getStudentWalletHash())
                    .build();
        } catch (Exception ex) {
            log.debug("Ex Message -> " + ex.getMessage());
        }
        return nextEvent;
    }

}
