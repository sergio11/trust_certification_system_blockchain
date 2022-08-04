package com.dreamsoftware.tcs.processor;

import com.dreamsoftware.tcs.model.events.NewTokensOrderApprovedEvent;
import com.dreamsoftware.tcs.model.events.OnTokensOrderCompletedEvent;
import com.dreamsoftware.tcs.service.IUserService;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * New Tokens Order Approved Processor
 *
 * @author ssanchez
 */
@Component("newTokensOrderApprovedProcessor")
@RequiredArgsConstructor
public class NewTokensOrderApprovedProcessor implements Function<NewTokensOrderApprovedEvent, OnTokensOrderCompletedEvent> {

    private final Logger logger = LoggerFactory.getLogger(NewTokensOrderApprovedProcessor.class);

    /**
     * User Service
     */
    private final IUserService userService;

    @Override
    public OnTokensOrderCompletedEvent apply(NewTokensOrderApprovedEvent event) {
        logger.debug("NewTokensOrderApprovedEvent CALLED!");
        OnTokensOrderCompletedEvent nextEvent = null;
        try {
            userService.addTokensToWallet(event);
            nextEvent = new OnTokensOrderCompletedEvent(event.getOrderId());
        } catch (final Exception ex) {
            logger.debug("Ex Message -> " + ex.getMessage());
        }
        return nextEvent;
    }

}
