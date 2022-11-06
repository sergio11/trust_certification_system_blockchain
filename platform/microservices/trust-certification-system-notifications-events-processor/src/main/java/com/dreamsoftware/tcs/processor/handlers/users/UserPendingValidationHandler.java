package com.dreamsoftware.tcs.processor.handlers.users;

import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.mail.model.user.UserPendingValidationMailRequestDTO;
import com.dreamsoftware.tcs.mail.service.IMailClientService;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.processor.handlers.AbstractNotificationHandler;
import com.dreamsoftware.tcs.stream.events.notifications.users.UserPendingValidationNotificationEvent;
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
public class UserPendingValidationHandler extends AbstractNotificationHandler<UserPendingValidationNotificationEvent> {

    private final IMailClientService mailClientService;
    private final UserRepository userRepository;
    private final I18NService i18nService;

    /**
     *
     * @param notification
     */
    @Override
    public void onHandle(final UserPendingValidationNotificationEvent notification) {
        Assert.notNull(notification, "Notification can not be null");
        log.debug("UserPendingValidationEvent handled!");
        userRepository.findById(new ObjectId(notification.getUserId())).ifPresent((user) -> {
            mailClientService.sendMail(UserPendingValidationMailRequestDTO.builder()
                    .email(user.getEmail())
                    .name(user.getFullName())
                    .locale(i18nService.parseLocaleOrDefault(user.getLanguage()))
                    .id(notification.getUserId())
                    .confirmationToken(user.getConfirmationToken())
                    .build());
        });
    }

}
