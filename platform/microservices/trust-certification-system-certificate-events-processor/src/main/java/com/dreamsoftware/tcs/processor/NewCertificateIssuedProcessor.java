package com.dreamsoftware.tcs.processor;

import com.dreamsoftware.tcs.model.events.OnNewCertificateIssuedEvent;
import com.dreamsoftware.tcs.service.ITrustCertificateService;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * New Certificate Issued Processor
 *
 * @author ssanchez
 */
@Component("newCertificateIssuedProcessor")
@RequiredArgsConstructor
public class NewCertificateIssuedProcessor implements Consumer<OnNewCertificateIssuedEvent> {

    private final Logger logger = LoggerFactory.getLogger(NewCertificateIssuedProcessor.class);

    /**
     * Trust Certification Service
     */
    private final ITrustCertificateService trustCertificateService;

    @Override
    public void accept(OnNewCertificateIssuedEvent event) {
        logger.debug("NewCertificateIssuedProcessor CALLED!");
        trustCertificateService.saveCertificate(event);
    }
}
