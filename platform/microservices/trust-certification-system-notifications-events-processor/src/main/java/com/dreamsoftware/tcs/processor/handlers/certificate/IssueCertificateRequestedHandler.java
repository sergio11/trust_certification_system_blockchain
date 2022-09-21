package com.dreamsoftware.tcs.processor.handlers.certificate;

import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.mail.model.IssueCertificateRequestMailRequestDTO;
import com.dreamsoftware.tcs.mail.service.IMailClientService;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificateIssuanceRequestRepository;
import com.dreamsoftware.tcs.processor.handlers.AbstractNotificationHandler;
import com.dreamsoftware.tcs.service.IDevicesManagementService;
import com.dreamsoftware.tcs.stream.events.notifications.certificate.IssueCertificateRequestedNotificationEvent;
import java.util.Locale;
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
    private final IDevicesManagementService devicesManagementService;

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
            final Locale studentLocale = i18nService.parseLocaleOrDefault(studentEntity.getLanguage());
            mailClientService.sendMail(IssueCertificateRequestMailRequestDTO
                    .builder()
                    .email(studentEntity.getEmail())
                    .qualification(qualification)
                    .certificateId(certificationId)
                    .locale(studentLocale)
                    .build());
            final String studentNotificationTitle = resolveString("notification_student_issue_certificate_requested_title", studentLocale, new Object[]{
                studentEntity.getFullName()});
            final String studentNotificationBody = resolveString("notification_student_issue_certificate_requested_body", studentLocale, new Object[]{
                studentEntity.getFullName()});
            devicesManagementService.sendNotification(studentEntity.getId(), studentNotificationTitle, studentNotificationBody);
            final UserEntity ca = certificateRequest.getCa();
            final Locale caLocale = i18nService.parseLocaleOrDefault(ca.getLanguage());
            mailClientService.sendMail(IssueCertificateRequestMailRequestDTO
                    .builder()
                    .email(ca.getEmail())
                    .qualification(qualification)
                    .certificateId(certificationId)
                    .locale(caLocale)
                    .build());
            final String caNotificationTitle = resolveString("notification_ca_issue_certificate_requested_title", caLocale, new Object[]{
                studentEntity.getFullName()});
            final String caNotificationBody = resolveString("notification_ca_issue_certificate_requested_body", caLocale, new Object[]{
                studentEntity.getFullName()});
            devicesManagementService.sendNotification(ca.getId(), caNotificationTitle, caNotificationBody);

        });
    }

}
