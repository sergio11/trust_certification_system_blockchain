package com.dreamsoftware.tcs.processor;

import com.dreamsoftware.tcs.stream.events.user.NewTokensOrderApprovedEvent;
import com.dreamsoftware.tcs.stream.events.user.OnTokensOrderCompletedEvent;
import com.dreamsoftware.tcs.service.IUserService;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * New Tokens Order Approved Processor
 *
 * @author ssanchez
 */
@Component("newTokensOrderApprovedProcessor")
@RequiredArgsConstructor
@Slf4j
public class NewTokensOrderApprovedProcessor implements Function<NewTokensOrderApprovedEvent, OnTokensOrderCompletedEvent> {

    /**
     * User Service
     */
    private final IUserService userService;

    /**
     *
     * @param event
     * @return
     */
    @Override
    public OnTokensOrderCompletedEvent apply(final NewTokensOrderApprovedEvent event) {
        log.debug("NewTokensOrderApprovedEvent CALLED!");
        OnTokensOrderCompletedEvent nextEvent = null;
        try {
            userService.addTokensToWallet(event);
            nextEvent = new OnTokensOrderCompletedEvent(event.getOrderId());
        } catch (final Exception ex) {
            log.debug("Ex Message -> " + ex.getMessage());
        }
        return nextEvent;
    }

}
