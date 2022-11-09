package com.dreamsoftware.tcs.processor.handlers.ca;

import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.mail.model.ca.CertificationAuthorityRemovedMailRequestDTO;
import com.dreamsoftware.tcs.mail.service.IMailClientService;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.processor.handlers.AbstractNotificationHandler;
import com.dreamsoftware.tcs.service.INotificationService;
import com.dreamsoftware.tcs.stream.events.notifications.ca.CertificationAuthorityRemovedNotificationEvent;
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
public class CertificationAuthorityRemovedHandler extends AbstractNotificationHandler<CertificationAuthorityRemovedNotificationEvent> {

    private final IMailClientService mailClientService;
    private final UserRepository userRepository;
    private final I18NService i18nService;
    private final INotificationService notificationService;

    /**
     *
     * @param notification
     */
    @Override
    public void onHandle(final CertificationAuthorityRemovedNotificationEvent notification) {
        Assert.notNull(notification, "Notification can not be null");
        log.debug("CertificationAuthorityRemovedHandler handled!");
        userRepository.findAllByCaId(new ObjectId(notification.getCaId())).forEach((caUserEntity) -> {
            notificationService.saveNotification("ca_removed_title", "ca_removed_message", caUserEntity);
            mailClientService.sendMail(CertificationAuthorityRemovedMailRequestDTO
                    .builder()
                    .email(caUserEntity.getEmail())
                    .id(caUserEntity.getId().toString())
                    .name(caUserEntity.getFullName())
                    .locale(i18nService.parseLocaleOrDefault(caUserEntity.getLanguage()))
                    .build());
        });
    }

}
