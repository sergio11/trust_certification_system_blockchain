package com.dreamsoftware.blockchaingateway.scheduling.events.account.handler;

import com.dreamsoftware.blockchaingateway.scheduling.events.account.UserActivatedEvent;
import com.dreamsoftware.blockchaingateway.services.mail.IMailClientService;
import com.dreamsoftware.blockchaingateway.scheduling.events.account.UserPendingValidationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.Assert;
import com.dreamsoftware.blockchaingateway.services.IBlockchainProcessor;

/**
 *
 * @author ssanchez
 */
@Component
@RequiredArgsConstructor
public class AccountsEventHandlers {

    private static Logger logger = LoggerFactory.getLogger(AccountsEventHandlers.class);

    private final IMailClientService mailClientService;
    private final IBlockchainProcessor blockchainProcessor;

    /**
     *
     * @param signUpUserDTO
     */
    @Async
    @EventListener
    void handle(final UserPendingValidationEvent event) {
        Assert.notNull(event.getUserId(), "User Id can not be null");
        mailClientService.sendMailForActivateAccount(event.getUserId());
    }

    /**
     *
     * @param event
     */
    @Async
    @EventListener
    void handle(final UserActivatedEvent event) {
        Assert.notNull(event.getUserId(), "User Id can not be null");
        blockchainProcessor.onUserActivated(event.getUserId());
    }
}