package com.dreamsoftware.tcs.processor.handlers.ca;

import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.mail.model.ca.CertificationAuthorityEnabledMailRequestDTO;
import com.dreamsoftware.tcs.mail.service.IMailClientService;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.processor.handlers.AbstractNotificationHandler;
import com.dreamsoftware.tcs.service.INotificationService;
import com.dreamsoftware.tcs.stream.events.notifications.ca.CertificationAuthorityEnabledNotificationEvent;
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
public class CertificationAuthorityEnabledHandler extends AbstractNotificationHandler<CertificationAuthorityEnabledNotificationEvent> {

    private final IMailClientService mailClientService;
    private final UserRepository userRepository;
    private final I18NService i18nService;
    private final INotificationService notificationService;

    /**
     *
     * @param notification
     */
    @Override
    public void onHandle(final CertificationAuthorityEnabledNotificationEvent notification) {
        Assert.notNull(notification, "Notification can not be null");
        log.debug("CertificationAuthorityEnabledNotificationEvent handled!");
        userRepository.findAllByCaId(new ObjectId(notification.getCaId())).forEach((caUserEntity) -> {
            notificationService.saveNotification("ca_enabled_title", "ca_enabled_message", caUserEntity);
            mailClientService.sendMail(CertificationAuthorityEnabledMailRequestDTO
                    .builder()
                    .email(caUserEntity.getEmail())
                    .id(caUserEntity.getId().toString())
                    .name(caUserEntity.getFullName())
                    .locale(i18nService.parseLocaleOrDefault(caUserEntity.getLanguage()))
                    .build());
        });
    }

}
