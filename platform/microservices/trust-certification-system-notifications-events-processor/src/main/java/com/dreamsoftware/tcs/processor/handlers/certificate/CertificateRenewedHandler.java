package com.dreamsoftware.tcs.processor.handlers.certificate;

import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.mail.model.CertificateRenewedMailRequestDTO;
import com.dreamsoftware.tcs.mail.service.IMailClientService;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificateIssuanceRequestRepository;
import com.dreamsoftware.tcs.processor.handlers.AbstractNotificationHandler;
import com.dreamsoftware.tcs.service.IDevicesManagementService;
import com.dreamsoftware.tcs.stream.events.notifications.certificate.CertificateRenewedNotificationEvent;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class CertificateRenewedHandler extends AbstractNotificationHandler<CertificateRenewedNotificationEvent> {

    private final IMailClientService mailClientService;
    private final CertificateIssuanceRequestRepository certificateIssuanceRequestRepository;
    private final I18NService i18nService;
    private final IDevicesManagementService devicesManagementService;

    /**
     *
     * @param notification
     */
    @Override
    public void onHandle(final CertificateRenewedNotificationEvent notification) {
        Assert.notNull(notification, "Notification can not be null");
        log.debug("CertificateRenewedEvent handled!");
        certificateIssuanceRequestRepository.findByCertificateId(notification.getId()).ifPresent((certificateRequest) -> {
            final UserEntity studentEntity = certificateRequest.getStudent();
            final Locale userLocale = i18nService.parseLocaleOrDefault(studentEntity.getLanguage());
            mailClientService.sendMail(CertificateRenewedMailRequestDTO
                    .builder()
                    .email(studentEntity.getEmail())
                    .certificateId(certificateRequest.getId().toString())
                    .locale(userLocale)
                    .build());
            final String title = resolveString("notification_certificate_renewed_title", userLocale, new Object[]{
                studentEntity.getFullName(), certificateRequest.getId().toString()});
            final String body = resolveString("notification_certificate_renewed_body", userLocale, new Object[]{
                studentEntity.getFullName(), certificateRequest.getId().toString()});
            devicesManagementService.sendNotification(studentEntity.getId(), title, body);
        });
    }

}
