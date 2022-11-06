package com.dreamsoftware.tcs.processor.handlers.certificate;

import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.mail.model.CertificateGeneratedMailRequestDTO;
import com.dreamsoftware.tcs.mail.service.IMailClientService;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificateIssuanceRequestRepository;
import com.dreamsoftware.tcs.processor.handlers.AbstractNotificationHandler;
import com.dreamsoftware.tcs.service.IDevicesManagementService;
import com.dreamsoftware.tcs.service.INotificationService;
import com.dreamsoftware.tcs.stream.events.notifications.certificate.CertificateIssuedNotificationEvent;
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
public class CertificateIssuedHandler extends AbstractNotificationHandler<CertificateIssuedNotificationEvent> {

    private final IMailClientService mailClientService;
    private final CertificateIssuanceRequestRepository certificateIssuanceRequestRepository;
    private final I18NService i18nService;
    private final INotificationService notificationService;
    private final IDevicesManagementService devicesManagementService;

    /**
     *
     * @param notification
     */
    @Override
    public void onHandle(final CertificateIssuedNotificationEvent notification) {
        Assert.notNull(notification, "Notification can not be null");
        log.debug("CertificateIssuedNotificationEvent handled!");
        certificateIssuanceRequestRepository.findByCertificateId(notification.getId()).ifPresent((certificateIssuedSaved) -> {
            final UserEntity caUserEntity = certificateIssuedSaved.getCaMember();
            final UserEntity studentEntity = certificateIssuedSaved.getStudent();
            final Locale userLocale = i18nService.parseLocaleOrDefault(studentEntity.getLanguage());
            notificationService.onUserCertificateValidated(certificateIssuedSaved);
            mailClientService.sendMail(CertificateGeneratedMailRequestDTO.builder()
                    .certificateId(certificateIssuedSaved.getCertificateId())
                    .caName(caUserEntity.getFullName())
                    .email(studentEntity.getEmail())
                    .qualification(certificateIssuedSaved.getQualification())
                    .locale(userLocale)
                    .build());

            final String title = resolveString("notification_certificate_issued_title", userLocale, new Object[]{
                studentEntity.getFullName(), certificateIssuedSaved.getCertificateId()});
            final String body = resolveString("notification_certificate_issued_body", userLocale, new Object[]{
                studentEntity.getFullName(), certificateIssuedSaved.getCertificateId()});
            devicesManagementService.sendNotification(studentEntity.getId(), title, body);
        });

    }

}
