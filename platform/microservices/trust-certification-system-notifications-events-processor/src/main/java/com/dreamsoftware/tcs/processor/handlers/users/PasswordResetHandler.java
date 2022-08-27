package com.dreamsoftware.tcs.processor.handlers.users;

import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.mail.model.ResetPasswordMailRequestDTO;
import com.dreamsoftware.tcs.mail.service.IMailClientService;
import com.dreamsoftware.tcs.processor.handlers.AbstractNotificationHandler;
import com.dreamsoftware.tcs.stream.events.notifications.users.PasswordResetNotificationEvent;
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
public class PasswordResetHandler extends AbstractNotificationHandler<PasswordResetNotificationEvent> {

    private final IMailClientService mailClientService;
    private final I18NService i18nService;

    /**
     *
     * @param notification
     */
    @Override
    public void onHandle(final PasswordResetNotificationEvent notification) {
        Assert.notNull(notification, "Notification can not be null");
        log.debug("PasswordResetEvent handled!");
        mailClientService.sendMail(ResetPasswordMailRequestDTO.builder()
                .email(notification.getEmail())
                .name(notification.getName())
                .locale(i18nService.parseLocaleOrDefault(notification.getLocale()))
                .id(String.valueOf(notification.getId()))
                .token(notification.getToken())
                .build());
    }

}
