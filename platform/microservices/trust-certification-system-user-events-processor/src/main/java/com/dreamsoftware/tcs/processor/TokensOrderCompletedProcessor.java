package com.dreamsoftware.tcs.processor;

import com.dreamsoftware.tcs.persistence.nosql.entity.CreatedOrderEntity;
import com.dreamsoftware.tcs.stream.events.OnTokensOrderCompletedEvent;
import com.dreamsoftware.tcs.service.IUserService;
import com.dreamsoftware.tcs.stream.events.notifications.users.OrderCompletedNotificationEvent;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Tokens Order Completed Processor
 *
 * @author ssanchez
 */
@Component("tokensOrderCompletedProcessor")
@RequiredArgsConstructor
@Slf4j
public class TokensOrderCompletedProcessor implements Function<OnTokensOrderCompletedEvent, OrderCompletedNotificationEvent> {

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
    public OrderCompletedNotificationEvent apply(final OnTokensOrderCompletedEvent event) {
        log.debug("TokensOrderCompletedProcessor called!");
        final CreatedOrderEntity orderEntity = userService.completeOrder(event);
        return new OrderCompletedNotificationEvent(orderEntity.getId().toString());
    }

}
