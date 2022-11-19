package com.dreamsoftware.tcs.processor.handlers.ca;

import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.mail.model.ca.CertificationAuthorityMemberDisabledMailRequestDTO;
import com.dreamsoftware.tcs.mail.service.IMailClientService;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.processor.handlers.AbstractNotificationHandler;
import com.dreamsoftware.tcs.service.INotificationService;
import com.dreamsoftware.tcs.stream.events.notifications.ca.CertificationAuthorityMemberDisabledNotificationEvent;
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
public class CertificationAuthorityMemberDisabledHandler extends AbstractNotificationHandler<CertificationAuthorityMemberDisabledNotificationEvent> {

    private final IMailClientService mailClientService;
    private final UserRepository userRepository;
    private final I18NService i18nService;
    private final INotificationService notificationService;

    /**
     * @param notification
     */
    @Override
    public void onHandle(final CertificationAuthorityMemberDisabledNotificationEvent notification) {
        Assert.notNull(notification, "Notification can not be null");
        log.debug("CertificationAuthorityMemberDisabledHandler handled!");
        userRepository.findOneByWalletHash(notification.getCaMemberWalletHash()).ifPresent((userEntity -> {
            notificationService.saveNotification("ca_member_disabled_title", "ca_member_disabled_message", userEntity);
            mailClientService.sendMail(CertificationAuthorityMemberDisabledMailRequestDTO
                    .builder()
                    .email(userEntity.getEmail())
                    .id(userEntity.getId().toString())
                    .name(userEntity.getFullName())
                    .locale(i18nService.parseLocaleOrDefault(userEntity.getLanguage()))
                    .build());
            final UserEntity caAdmin = userEntity.getCa().getAdmin();
            notificationService.saveNotification("ca_member_disabled_title", "ca_member_disabled_message", caAdmin);
        }));
    }

}
