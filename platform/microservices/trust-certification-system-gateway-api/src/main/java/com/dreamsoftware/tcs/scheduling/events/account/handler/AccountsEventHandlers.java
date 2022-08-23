package com.dreamsoftware.tcs.scheduling.events.account.handler;

import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.mail.model.ResetPasswordMailRequestDTO;
import com.dreamsoftware.tcs.mail.model.UserPendingValidationMailRequestDTO;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.mail.service.IMailClientService;
import com.dreamsoftware.tcs.scheduling.events.account.PasswordResetEvent;
import com.dreamsoftware.tcs.scheduling.events.account.UserPendingValidationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.Assert;

/**
 *
 * @author ssanchez
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AccountsEventHandlers {

    private final IMailClientService mailClientService;
    private final UserRepository userRepository;
    private final I18NService i18nService;

    /**
     *
     * @param passwordResetEvent
     */
    @Async
    @EventListener
    void handle(final PasswordResetEvent event) {
        Assert.notNull(event.getResetPasswordToken(), "Reset Password Token can not be null");
        log.debug("PasswordResetEvent handled!");
        mailClientService.sendMail(ResetPasswordMailRequestDTO.builder()
                .email(event.getResetPasswordToken().getEmail())
                .name(event.getResetPasswordToken().getName())
                .locale(i18nService.parseLocaleOrDefault(event.getResetPasswordToken().getLocale()))
                .id(String.valueOf(event.getResetPasswordToken().getId()))
                .token(event.getResetPasswordToken().getToken())
                .build());

    }

    /**
     *
     * @param signUpUserDTO
     */
    @Async
    @EventListener
    void handle(final UserPendingValidationEvent event) {
        Assert.notNull(event.getUserId(), "User Id can not be null");
        log.debug("UserPendingValidationEvent handled!");
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
}
