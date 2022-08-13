package com.dreamsoftware.tcs.scheduling.events.ca.handler;

import com.dreamsoftware.tcs.service.IMailClientService;
import com.dreamsoftware.tcs.scheduling.events.ca.CADisabledEvent;
import com.dreamsoftware.tcs.scheduling.events.ca.CAEnabledEvent;
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
public class CAEventHandlers {

    private static final Logger logger = LoggerFactory.getLogger(CAEventHandlers.class);

    /**
     * Mail Client Service
     */
    private final IMailClientService mailClientService;

    /**
     *
     * @param event
     */
    @Async
    @EventListener
    void handle(final CAEnabledEvent event) {
        Assert.notNull(event.getWalletHash(), "Wallet Hash can not be null");
        logger.debug("CAEnabledEvent handled!");

    }

    /**
     *
     * @param event
     */
    @Async
    @EventListener
    void handle(final CADisabledEvent event) {
        Assert.notNull(event.getWalletHash(), "Wallet Hash can not be null");
        logger.debug("CADisabledEvent handled!");
    }
}
