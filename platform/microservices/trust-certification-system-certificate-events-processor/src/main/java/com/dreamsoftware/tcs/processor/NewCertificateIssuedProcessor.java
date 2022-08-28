package com.dreamsoftware.tcs.processor;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificateIssuanceRequestEntity;
import com.dreamsoftware.tcs.stream.events.certificate.OnNewCertificateIssuedEvent;
import com.dreamsoftware.tcs.service.ITrustCertificateService;
import com.dreamsoftware.tcs.stream.events.notifications.certificate.CertificateIssuedNotificationEvent;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * New Certificate Issued Processor
 *
 * @author ssanchez
 */
@Slf4j
@Component("newCertificateIssuedProcessor")
@RequiredArgsConstructor
public class NewCertificateIssuedProcessor implements Function<OnNewCertificateIssuedEvent, CertificateIssuedNotificationEvent> {

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
    public CertificateIssuedNotificationEvent apply(final OnNewCertificateIssuedEvent event) {
        log.debug("NewCertificateIssuedProcessor CALLED!");
        final CertificateIssuanceRequestEntity certificateIssuanceRequestEntity = trustCertificateService.saveCertificate(event);
        return new CertificateIssuedNotificationEvent(certificateIssuanceRequestEntity.getCertificateId());
    }
}
