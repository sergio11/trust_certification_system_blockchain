package com.dreamsoftware.tcs.processor.handlers.certificate;

import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.mail.model.certificate.CertificateRequestAcceptedMailRequestDTO;
import com.dreamsoftware.tcs.mail.service.IMailClientService;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificateIssuanceRequestRepository;
import com.dreamsoftware.tcs.processor.handlers.AbstractNotificationHandler;
import com.dreamsoftware.tcs.service.IDevicesManagementService;
import com.dreamsoftware.tcs.stream.events.notifications.certificate.CertificateRequestAcceptedNotificationEvent;

import java.util.Locale;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author ssanchez
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
@Slf4j
public class CertificateRequestAcceptedHandler extends AbstractNotificationHandler<CertificateRequestAcceptedNotificationEvent> {

    private final IMailClientService mailClientService;
    private final CertificateIssuanceRequestRepository certificateIssuanceRequestRepository;
    private final I18NService i18nService;
    private final IDevicesManagementService devicesManagementService;

    /**
     * @param notification
     */
    @Override
    public void onHandle(final CertificateRequestAcceptedNotificationEvent notification) {
        Assert.notNull(notification, "Notification can not be null");
        log.debug("CertificateRequestAcceptedEvent handled!");
        certificateIssuanceRequestRepository.findById(new ObjectId(notification.getId())).ifPresent((certificateRequest) -> {
            final UserEntity studentEntity = certificateRequest.getStudent();
            final Locale userLocale = i18nService.parseLocaleOrDefault(studentEntity.getLanguage());
            mailClientService.sendMail(CertificateRequestAcceptedMailRequestDTO
                    .builder()
                    .name(studentEntity.getFullName())
                    .email(studentEntity.getEmail())
                    .qualification(certificateRequest.getQualification())
                    .certificateId(certificateRequest.getId().toString())
                    .locale(userLocale)
                    .build());
            final String title = resolveString("notification_certificate_request_accepted_title", userLocale, new Object[]{
                    studentEntity.getFullName(), certificateRequest.getId().toString()});
            final String body = resolveString("notification_certificate_request_accepted_body", userLocale, new Object[]{
                    studentEntity.getFullName(), certificateRequest.getId().toString()});
            devicesManagementService.sendNotification(studentEntity.getId(), title, body);
        });
    }

}
