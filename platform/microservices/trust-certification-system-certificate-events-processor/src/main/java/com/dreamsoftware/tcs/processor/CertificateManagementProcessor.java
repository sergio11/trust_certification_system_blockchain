package com.dreamsoftware.tcs.processor;

import com.dreamsoftware.tcs.service.ITrustCertificateService;
import com.dreamsoftware.tcs.stream.events.notifications.AbstractNotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * Certificate Management Processor
 *
 * @author ssanchez
 */
@Component("certificateManagementProcessor")
@RequiredArgsConstructor
@Slf4j
public class CertificateManagementProcessor implements Function<GenericMessage<String>, AbstractNotificationEvent> {

    /**
     * Trust certificate service
     */
    private final ITrustCertificateService trustCertificateService;

    /**
     * @param event
     * @return
     */
    @Override
    public AbstractNotificationEvent apply(final GenericMessage<String> event) {
        log.debug("CertificateManagementProcessor CALLED!");
        AbstractNotificationEvent notificationEvent = null;
        try {
            notificationEvent = trustCertificateService.handle(event);
        } catch (Exception e) {
            log.error("CertificateManagementProcessor ex -> " + e.getMessage());
        }
        return notificationEvent;
    }
}
