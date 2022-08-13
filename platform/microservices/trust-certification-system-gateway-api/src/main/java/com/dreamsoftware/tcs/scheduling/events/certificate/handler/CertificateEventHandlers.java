package com.dreamsoftware.tcs.scheduling.events.certificate.handler;

import com.dreamsoftware.tcs.mail.model.service.IMailClientService;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificateIssuanceRequestRepository;
import com.dreamsoftware.tcs.scheduling.events.certificate.CertificateDisabledEvent;
import com.dreamsoftware.tcs.scheduling.events.certificate.CertificateEnabledEvent;
import com.dreamsoftware.tcs.scheduling.events.certificate.CertificateRenewedEvent;
import com.dreamsoftware.tcs.scheduling.events.certificate.CertificateRequestAcceptedEvent;
import com.dreamsoftware.tcs.scheduling.events.certificate.CertificateRequestRejectedEvent;
import com.dreamsoftware.tcs.scheduling.events.certificate.CertificateVisibilityChangedEvent;
import com.dreamsoftware.tcs.scheduling.events.certificate.IssueCertificateRequestedEvent;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.Assert;

/**
 *
 * @author ssanchez
 */
@Component
@RequiredArgsConstructor
public class CertificateEventHandlers {

    private static final Logger logger = LoggerFactory.getLogger(CertificateEventHandlers.class);

    private final IMailClientService mailClientService;
    private final CertificateIssuanceRequestRepository certificateIssuanceRequestRepository;

    /**
     *
     * @param event
     */
    @Async
    @EventListener
    void handle(final CertificateDisabledEvent event) {
        Assert.notNull(event.getId(), "Id can not be null");
        logger.debug("CertificateDisabledEvent handled!");
        certificateIssuanceRequestRepository.findByCertificateId(event.getId()).ifPresent((certificateRequest) -> {

        });
    }

    /**
     *
     * @param event
     */
    @Async
    @EventListener
    void handle(final CertificateEnabledEvent event) {
        Assert.notNull(event.getId(), "Id can not be null");
        logger.debug("CertificateEnabledEvent handled!");
        certificateIssuanceRequestRepository.findByCertificateId(event.getId()).ifPresent((certificateRequest) -> {

        });
    }

    /**
     *
     * @param event
     */
    @Async
    @EventListener
    void handle(final CertificateRenewedEvent event) {
        Assert.notNull(event.getId(), "Id can not be null");
        logger.debug("CertificateRenewedEvent handled!");
        certificateIssuanceRequestRepository.findByCertificateId(event.getId()).ifPresent((certificateRequest) -> {

        });
    }

    /**
     *
     * @param event
     */
    @Async
    @EventListener
    void handle(final CertificateRequestAcceptedEvent event) {
        Assert.notNull(event.getId(), "Id can not be null");
        logger.debug("CertificateRequestAcceptedEvent handled!");
        certificateIssuanceRequestRepository.findById(new ObjectId(event.getId())).ifPresent((certificateRequest) -> {

        });
    }

    /**
     *
     * @param event
     */
    @Async
    @EventListener
    void handle(final CertificateRequestRejectedEvent event) {
        Assert.notNull(event.getId(), "Id can not be null");
        logger.debug("CertificateRequestRejectedEvent handled!");
        certificateIssuanceRequestRepository.findById(new ObjectId(event.getId())).ifPresent((certificateRequest) -> {

        });
    }

    /**
     *
     * @param event
     */
    @Async
    @EventListener
    void handle(final CertificateVisibilityChangedEvent event) {
        Assert.notNull(event.getId(), "Id can not be null");
        logger.debug("CertificateVisibilityChangedEvent handled!");
        certificateIssuanceRequestRepository.findByCertificateId(event.getId()).ifPresent((certificateRequest) -> {

        });
    }

    /**
     *
     * @param event
     */
    @Async
    @EventListener
    void handle(final IssueCertificateRequestedEvent event) {
        Assert.notNull(event.getId(), "Id can not be null");
        logger.debug("IssueCertificateRequestedEvent handled!");
        certificateIssuanceRequestRepository.findById(new ObjectId(event.getId())).ifPresent((certificateRequest) -> {

        });
    }
}
