package com.dreamsoftware.tcs.processor.handlers;

import com.dreamsoftware.tcs.persistence.bc.repository.ITrustCertificationBlockchainRepository;
import com.dreamsoftware.tcs.stream.events.certificate.EnableCertificateRequestEvent;
import com.dreamsoftware.tcs.stream.events.notifications.certificate.CertificateEnabledNotificationEvent;
import com.dreamsoftware.tcs.utils.AbstractProcessAndReturnHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Enable Certificate Request Handler
 *
 * @author ssanchez
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
@Slf4j
public class EnableCertificateRequestHandler extends AbstractProcessAndReturnHandler<EnableCertificateRequestEvent, CertificateEnabledNotificationEvent> {

    /**
     * Trust Certification Blockchain Repository
     */
    private final ITrustCertificationBlockchainRepository trustCertificationBlockchainRepository;

    @Override
    public CertificateEnabledNotificationEvent onHandle(final EnableCertificateRequestEvent event) throws Exception {
        Assert.notNull(event, "Event can not be null");
        log.debug("EnableCertificateRequestHandler CALLED!");
        trustCertificationBlockchainRepository.enable(event.getStudentWalletHash(), event.getCertificationId());
        log.debug("Certificate " + event.getCertificationId() + " ENABLED!");
        return CertificateEnabledNotificationEvent.builder()
                .id(event.getCertificationId())
                .build();
    }
}
