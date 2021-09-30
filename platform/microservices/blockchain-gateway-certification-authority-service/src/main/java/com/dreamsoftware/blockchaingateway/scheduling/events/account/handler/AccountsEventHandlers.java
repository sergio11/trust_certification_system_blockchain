package com.dreamsoftware.blockchaingateway.scheduling.events.account.handler;

import com.dreamsoftware.blockchaingateway.scheduling.events.account.UserPendingValidationEvent;
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

    /**
     *
     * @param signUpUserDTO
     */
    @Async
    @EventListener
    void handle(final UserPendingValidationEvent event) {
        Assert.notNull(event.getUserDTO(), "Simple User DTO can not be null");
    }
}
