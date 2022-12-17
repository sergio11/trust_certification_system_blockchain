package com.dreamsoftware.tcs.processor.handlers.certificate;

import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.mail.model.certificate.CertificateVisibilityChangedMailRequestDTO;
import com.dreamsoftware.tcs.mail.service.IMailClientService;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificateIssuanceRequestRepository;
import com.dreamsoftware.tcs.processor.handlers.AbstractNotificationHandler;
import com.dreamsoftware.tcs.service.IDevicesManagementService;
import com.dreamsoftware.tcs.stream.events.notifications.certificate.CertificateVisibilityChangedNotificationEvent;

import java.util.Locale;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class CertificateVisibilityChangedHandler extends AbstractNotificationHandler<CertificateVisibilityChangedNotificationEvent> {

    private final IMailClientService mailClientService;
    private final CertificateIssuanceRequestRepository certificateIssuanceRequestRepository;
    private final I18NService i18nService;
    private final IDevicesManagementService devicesManagementService;

    /**
     * @param notification
     */
    @Override
    public void onHandle(final CertificateVisibilityChangedNotificationEvent notification) {
        Assert.notNull(notification, "Notification can not be null");
        log.debug("CertificateVisibilityChangedEvent handled!");
        certificateIssuanceRequestRepository.findByCertificateId(notification.getId()).ifPresent((certificateRequest) -> {
            final UserEntity studentEntity = certificateRequest.getStudent();
            final Locale userLocale = i18nService.parseLocaleOrDefault(studentEntity.getLanguage());
            mailClientService.sendMail(CertificateVisibilityChangedMailRequestDTO
                    .builder()
                    .name(studentEntity.getFullName())
                    .email(studentEntity.getEmail())
                    .isVisible(notification.getIsVisible())
                    .certificateId(certificateRequest.getId().toString())
                    .locale(userLocale)
                    .build());
            final String title = resolveString("notification_certificate_visibility_changed_title", userLocale, new Object[]{
                    studentEntity.getFullName()});
            final String body = resolveString(notification.getIsVisible()
                    ? "notification_certificate_changed_to_visible_body" : "notification_certificate_changed_to_invisible_body", userLocale, new Object[]{
                    studentEntity.getFullName(), certificateRequest.getId().toString()});
            devicesManagementService.sendNotification(studentEntity.getId(), title, body);
        });
    }

}
