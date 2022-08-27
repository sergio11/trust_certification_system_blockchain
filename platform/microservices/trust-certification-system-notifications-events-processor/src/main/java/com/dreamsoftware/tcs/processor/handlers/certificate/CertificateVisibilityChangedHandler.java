package com.dreamsoftware.tcs.processor.handlers.certificate;

import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.mail.model.CertificateVisibilityChangedMailRequestDTO;
import com.dreamsoftware.tcs.mail.service.IMailClientService;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificateIssuanceRequestRepository;
import com.dreamsoftware.tcs.processor.handlers.AbstractNotificationHandler;
import com.dreamsoftware.tcs.stream.events.notifications.certificate.CertificateVisibilityChangedNotificationEvent;
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
public class CertificateVisibilityChangedHandler extends AbstractNotificationHandler<CertificateVisibilityChangedNotificationEvent> {

    private final IMailClientService mailClientService;
    private final CertificateIssuanceRequestRepository certificateIssuanceRequestRepository;
    private final I18NService i18nService;

    /**
     *
     * @param notification
     */
    @Override
    public void onHandle(final CertificateVisibilityChangedNotificationEvent notification) {
        Assert.notNull(notification, "Notification can not be null");
        log.debug("CertificateVisibilityChangedEvent handled!");
        certificateIssuanceRequestRepository.findByCertificateId(notification.getId()).ifPresent((certificateRequest) -> {
            final UserEntity studentEntity = certificateRequest.getStudent();
            mailClientService.sendMail(CertificateVisibilityChangedMailRequestDTO
                    .builder()
                    .email(studentEntity.getEmail())
                    .isVisible(notification.getIsVisible())
                    .certificateId(certificateRequest.getId().toString())
                    .locale(i18nService.parseLocaleOrDefault(studentEntity.getLanguage()))
                    .build());
        });
    }

}
