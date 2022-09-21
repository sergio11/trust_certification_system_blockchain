package com.dreamsoftware.tcs.processor.handlers.ca;

import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.mail.model.CADisabledMailRequestDTO;
import com.dreamsoftware.tcs.mail.service.IMailClientService;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.processor.handlers.AbstractNotificationHandler;
import com.dreamsoftware.tcs.stream.events.notifications.ca.CADisabledNotificationEvent;
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
public class CADisabledHandler extends AbstractNotificationHandler<CADisabledNotificationEvent> {

    private final IMailClientService mailClientService;
    private final UserRepository userRepository;
    private final I18NService i18nService;

    /**
     *
     * @param notification
     */
    @Override
    public void onHandle(final CADisabledNotificationEvent notification) {
        Assert.notNull(notification, "Notification can not be null");
        log.debug("CADisabledEvent handled!");
        userRepository.findOneByWalletHash(notification.getWalletHash()).ifPresent((user) -> {
            mailClientService.sendMail(CADisabledMailRequestDTO
                    .builder()
                    .email(user.getEmail())
                    .id(user.getId().toString())
                    .name(user.getFullName())
                    .locale(i18nService.parseLocaleOrDefault(user.getLanguage()))
                    .build());
        });
    }

}
