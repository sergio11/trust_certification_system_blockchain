package com.dreamsoftware.blockchaingateway.scheduling.events.account.handler;

import com.dreamsoftware.blockchaingateway.services.mail.IMailClientService;
import com.dreamsoftware.blockchaingateway.scheduling.events.account.UserPendingValidationEvent;
import com.dreamsoftware.blockchaingateway.services.I18NService;
import lombok.RequiredArgsConstructor;
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

    private static Logger logger = LoggerFactory.getLogger(AccountsEventHandlers.class);

    private final IMailClientService mailClientService;
    private final I18NService i18nService;

    /**
     *
     * @param signUpUserDTO
     */
    @Async
    @EventListener
    void handle(final UserPendingValidationEvent event) {
        Assert.notNull(event.getUserId(), "User Id can not be null");

    }
}
