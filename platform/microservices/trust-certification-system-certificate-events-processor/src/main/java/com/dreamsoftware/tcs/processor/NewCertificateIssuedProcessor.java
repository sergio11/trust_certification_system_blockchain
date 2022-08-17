package com.dreamsoftware.tcs.processor;

import com.dreamsoftware.tcs.model.events.OnNewCertificateIssuedEvent;
import com.dreamsoftware.tcs.service.ITrustCertificateService;
import java.util.function.Consumer;
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
public class NewCertificateIssuedProcessor implements Consumer<OnNewCertificateIssuedEvent> {

    /**
     * Trust Certification Service
     */
    private final ITrustCertificateService trustCertificateService;

    /**
     *
     * @param event
     */
    @Override
    public void accept(final OnNewCertificateIssuedEvent event) {
        log.debug("NewCertificateIssuedProcessor CALLED!");
        trustCertificateService.saveCertificate(event);
    }
}
