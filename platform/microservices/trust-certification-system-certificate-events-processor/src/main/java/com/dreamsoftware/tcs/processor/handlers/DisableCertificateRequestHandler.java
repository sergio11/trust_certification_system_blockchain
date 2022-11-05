package com.dreamsoftware.tcs.processor.handlers;

import com.dreamsoftware.tcs.persistence.bc.repository.ITrustCertificationBlockchainRepository;
import com.dreamsoftware.tcs.stream.events.certificate.DisableCertificateRequestEvent;
import com.dreamsoftware.tcs.stream.events.notifications.certificate.CertificateDisabledNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.certificate.CertificateEnabledNotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Disable Certificate Request Handler
 *
 * @author ssanchez
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
@Slf4j
public class DisableCertificateRequestHandler extends AbstractCertificateManagementHandler<DisableCertificateRequestEvent, CertificateDisabledNotificationEvent> {

    /**
     * Trust Certification Blockchain Repository
     */
    private final ITrustCertificationBlockchainRepository trustCertificationBlockchainRepository;

    @Override
    public CertificateDisabledNotificationEvent onHandle(final DisableCertificateRequestEvent event) throws Exception {
        Assert.notNull(event, "Event can not be null");
        log.debug("EnableCertificateRequestHandler CALLED!");
        trustCertificationBlockchainRepository.disable(event.getStudentWalletHash(), event.getCertificationId());
        log.debug("Certificate " + event.getCertificationId() + " DISABLED!");
        return CertificateDisabledNotificationEvent.builder()
                .id(event.getCertificationId())
                .build();
    }
}
