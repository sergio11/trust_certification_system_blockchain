package com.dreamsoftware.tcs.scheduling.events.certificate.handler;

import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.mail.model.CertificateDisabledMailRequestDTO;
import com.dreamsoftware.tcs.mail.model.CertificateEnabledMailRequestDTO;
import com.dreamsoftware.tcs.mail.model.CertificateRenewedMailRequestDTO;
import com.dreamsoftware.tcs.mail.model.CertificateRequestAcceptedMailRequestDTO;
import com.dreamsoftware.tcs.mail.model.CertificateRequestRejectedMailRequestDTO;
import com.dreamsoftware.tcs.mail.model.CertificateVisibilityChangedMailRequestDTO;
import com.dreamsoftware.tcs.mail.model.IssueCertificateRequestMailRequestDTO;
import com.dreamsoftware.tcs.mail.model.service.IMailClientService;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
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
    private final I18NService i18nService;

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
            final String certificateId = certificateRequest.getId().toString();
            final UserEntity studentEntity = certificateRequest.getStudent();
            mailClientService.sendMail(CertificateDisabledMailRequestDTO
                    .builder()
                    .email(studentEntity.getEmail())
                    .certificateId(certificateId)
                    .locale(i18nService.parseLocaleOrDefault(studentEntity.getLanguage()))
                    .build());
            final UserEntity ca = certificateRequest.getCa();
            mailClientService.sendMail(CertificateDisabledMailRequestDTO
                    .builder()
                    .email(ca.getEmail())
                    .certificateId(certificateId)
                    .locale(i18nService.parseLocaleOrDefault(ca.getLanguage()))
                    .build());
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
            final String certificateId = certificateRequest.getId().toString();
            final UserEntity studentEntity = certificateRequest.getStudent();
            mailClientService.sendMail(CertificateEnabledMailRequestDTO
                    .builder()
                    .email(studentEntity.getEmail())
                    .certificateId(certificateId)
                    .locale(i18nService.parseLocaleOrDefault(studentEntity.getLanguage()))
                    .build());
            final UserEntity ca = certificateRequest.getCa();
            mailClientService.sendMail(CertificateEnabledMailRequestDTO
                    .builder()
                    .email(ca.getEmail())
                    .certificateId(certificateId)
                    .locale(i18nService.parseLocaleOrDefault(ca.getLanguage()))
                    .build());
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
            final UserEntity studentEntity = certificateRequest.getStudent();
            mailClientService.sendMail(CertificateRenewedMailRequestDTO
                    .builder()
                    .email(studentEntity.getEmail())
                    .certificateId(certificateRequest.getId().toString())
                    .locale(i18nService.parseLocaleOrDefault(studentEntity.getLanguage()))
                    .build());
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
            final UserEntity studentEntity = certificateRequest.getStudent();
            mailClientService.sendMail(CertificateRequestAcceptedMailRequestDTO
                    .builder()
                    .email(studentEntity.getEmail())
                    .qualification(certificateRequest.getQualification())
                    .certificateId(certificateRequest.getId().toString())
                    .locale(i18nService.parseLocaleOrDefault(studentEntity.getLanguage()))
                    .build());
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
            final UserEntity studentEntity = certificateRequest.getStudent();
            mailClientService.sendMail(CertificateRequestRejectedMailRequestDTO
                    .builder()
                    .email(studentEntity.getEmail())
                    .qualification(certificateRequest.getQualification())
                    .certificateId(certificateRequest.getId().toString())
                    .locale(i18nService.parseLocaleOrDefault(studentEntity.getLanguage()))
                    .build());
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
            final UserEntity studentEntity = certificateRequest.getStudent();
            mailClientService.sendMail(CertificateVisibilityChangedMailRequestDTO
                    .builder()
                    .email(studentEntity.getEmail())
                    .isVisible(event.getIsVisible())
                    .certificateId(certificateRequest.getId().toString())
                    .locale(i18nService.parseLocaleOrDefault(studentEntity.getLanguage()))
                    .build());
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
            final Long qualification = certificateRequest.getQualification();
            final String certificationId = certificateRequest.getId().toString();
            final UserEntity studentEntity = certificateRequest.getStudent();
            mailClientService.sendMail(IssueCertificateRequestMailRequestDTO
                    .builder()
                    .email(studentEntity.getEmail())
                    .qualification(qualification)
                    .certificateId(certificationId)
                    .locale(i18nService.parseLocaleOrDefault(studentEntity.getLanguage()))
                    .build());
            final UserEntity ca = certificateRequest.getCa();
            mailClientService.sendMail(IssueCertificateRequestMailRequestDTO
                    .builder()
                    .email(ca.getEmail())
                    .qualification(qualification)
                    .certificateId(certificationId)
                    .locale(i18nService.parseLocaleOrDefault(ca.getLanguage()))
                    .build());
        });
    }
}
