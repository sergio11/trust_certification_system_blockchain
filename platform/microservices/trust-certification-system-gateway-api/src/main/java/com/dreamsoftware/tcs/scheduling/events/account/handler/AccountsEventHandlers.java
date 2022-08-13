package com.dreamsoftware.tcs.scheduling.events.account.handler;

import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.model.mail.UserPendingValidationMailRequestDTO;
import com.dreamsoftware.tcs.model.mail.UserActivatedEventMailRequestDTO;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.scheduling.events.account.UserActivatedEvent;
import com.dreamsoftware.tcs.service.IMailClientService;
import com.dreamsoftware.tcs.scheduling.events.account.UserPendingValidationEvent;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.Assert;

/**
 *
 * @author ssanchez
 */
@Component
@RequiredArgsConstructor
public class AccountsEventHandlers {

    private static final Logger logger = LoggerFactory.getLogger(AccountsEventHandlers.class);

    private final IMailClientService mailClientService;
    private final UserRepository userRepository;
    private final I18NService i18nService;

    /**
     *
     * @param signUpUserDTO
     */
    @Async
    @EventListener
    void handle(final UserPendingValidationEvent event) {
        Assert.notNull(event.getUserId(), "User Id can not be null");
        logger.debug("UserPendingValidationEvent handled!");
        userRepository.findById(new ObjectId(event.getUserId())).ifPresent((user) -> {
            mailClientService.sendMail(UserPendingValidationMailRequestDTO.builder()
                    .email(user.getEmail())
                    .name(user.getName())
                    .locale(i18nService.parseLocaleOrDefault(user.getLanguage()))
                    .id(event.getUserId())
                    .confirmationToken(user.getConfirmationToken())
                    .build());
        });
    }

    /**
     *
     * @param event
     */
    @Async
    @EventListener
    void handle(final UserActivatedEvent event) {
        Assert.notNull(event.getUserId(), "User Id can not be null");
        logger.debug("UserActivatedEvent handled!");
        userRepository.findById(new ObjectId(event.getUserId())).ifPresent((user) -> {
            mailClientService.sendMail(UserActivatedEventMailRequestDTO.builder()
                    .email(user.getEmail())
                    .name(user.getName())
                    .locale(i18nService.parseLocaleOrDefault(user.getLanguage()))
                    .id(event.getUserId())
                    .build());
        });
    }
}
