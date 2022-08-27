package com.dreamsoftware.tcs.processor.handlers.certificate;

import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.mail.model.IssueCertificateRequestMailRequestDTO;
import com.dreamsoftware.tcs.mail.service.IMailClientService;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificateIssuanceRequestRepository;
import com.dreamsoftware.tcs.processor.handlers.AbstractNotificationHandler;
import com.dreamsoftware.tcs.stream.events.notifications.certificate.IssueCertificateRequestedNotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 *
 * @author ssanchez
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
@Slf4j
public class IssueCertificateRequestedHandler extends AbstractNotificationHandler<IssueCertificateRequestedNotificationEvent> {

    private final IMailClientService mailClientService;
    private final CertificateIssuanceRequestRepository certificateIssuanceRequestRepository;
    private final I18NService i18nService;

    /**
     *
     * @param notification
     */
    @Override
    public void onHandle(final IssueCertificateRequestedNotificationEvent notification) {
        Assert.notNull(notification, "Notification can not be null");
        log.debug("IssueCertificateRequestedEvent handled!");
        certificateIssuanceRequestRepository.findById(new ObjectId(notification.getId())).ifPresent((certificateRequest) -> {
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
