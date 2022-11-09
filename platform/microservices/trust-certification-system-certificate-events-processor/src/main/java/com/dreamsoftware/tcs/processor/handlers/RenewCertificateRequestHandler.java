package com.dreamsoftware.tcs.processor.handlers;

import com.dreamsoftware.tcs.persistence.bc.repository.ITrustCertificationBlockchainRepository;
import com.dreamsoftware.tcs.stream.events.certificate.RenewCertificateRequestEvent;
import com.dreamsoftware.tcs.stream.events.notifications.certificate.CertificateRenewedNotificationEvent;
import com.dreamsoftware.tcs.utils.AbstractProcessAndReturnHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Renew Certificate Request Handler
 *
 * @author ssanchez
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
@Slf4j
public class RenewCertificateRequestHandler extends AbstractProcessAndReturnHandler<RenewCertificateRequestEvent, CertificateRenewedNotificationEvent> {

    /**
     * Trust Certification Blockchain Repository
     */
    private final ITrustCertificationBlockchainRepository trustCertificationBlockchainRepository;

    @Override
    public CertificateRenewedNotificationEvent onHandle(RenewCertificateRequestEvent event) throws Exception {
        Assert.notNull(event, "Event can not be null");
        log.debug("RenewCertificateRequestHandler CALLED!");
        trustCertificationBlockchainRepository.renewCertificate(event.getStudentWalletHash(), event.getCertificationId());
        log.debug("Certificate " + event.getCertificationId() + " RENEWED!");
        return CertificateRenewedNotificationEvent.builder()
                .id(event.getCertificationId())
                .build();
    }
}
