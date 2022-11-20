package com.dreamsoftware.tcs.processor.handlers;

import com.dreamsoftware.tcs.persistence.bc.repository.ITrustCertificationBlockchainRepository;
import com.dreamsoftware.tcs.stream.events.AbstractEvent;
import com.dreamsoftware.tcs.stream.events.certificate.UpdateCertificateVisibilityRequestEvent;
import com.dreamsoftware.tcs.stream.events.notifications.certificate.CertificateVisibilityChangedNotificationEvent;
import com.dreamsoftware.tcs.utils.AbstractProcessAndReturnHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Update Certificate Visibility Request Handler
 *
 * @author ssanchez
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
@Slf4j
public class UpdateCertificateVisibilityRequestHandler extends AbstractProcessAndReturnHandler<UpdateCertificateVisibilityRequestEvent> {

    /**
     * Trust Certification Blockchain Repository
     */
    private final ITrustCertificationBlockchainRepository trustCertificationBlockchainRepository;

    @Override
    public AbstractEvent onHandle(UpdateCertificateVisibilityRequestEvent event) throws Exception {
        Assert.notNull(event, "Event can not be null");
        log.debug("UpdateCertificateVisibilityRequestHandler CALLED!");
        trustCertificationBlockchainRepository.updateCertificateVisibility(event.getStudentWalletHash(), event.getCertificationId(), event.getIsVisible());
        log.debug("Certificate " + event.getCertificationId() + " Visibility Updated!");
        return CertificateVisibilityChangedNotificationEvent.builder()
                .id(event.getCertificationId())
                .isVisible(event.getIsVisible())
                .build();
    }
}
