package com.dreamsoftware.tcs.processor.handlers.certificate;

import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.mail.model.CertificateDisabledMailRequestDTO;
import com.dreamsoftware.tcs.mail.service.IMailClientService;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificateIssuanceRequestRepository;
import com.dreamsoftware.tcs.processor.handlers.AbstractNotificationHandler;
import com.dreamsoftware.tcs.stream.events.notifications.certificate.CertificateDisabledNotificationEvent;
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
public class CertificateDisabledHandler extends AbstractNotificationHandler<CertificateDisabledNotificationEvent> {

    private final IMailClientService mailClientService;
    private final CertificateIssuanceRequestRepository certificateIssuanceRequestRepository;
    private final I18NService i18nService;

    /**
     *
     * @param notification
     */
    @Override
    public void onHandle(final CertificateDisabledNotificationEvent notification) {
        Assert.notNull(notification, "Notification can not be null");
        log.debug("CertificateDisabledEvent handled!");
        certificateIssuanceRequestRepository.findByCertificateId(notification.getId()).ifPresent((certificateRequest) -> {
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

}
