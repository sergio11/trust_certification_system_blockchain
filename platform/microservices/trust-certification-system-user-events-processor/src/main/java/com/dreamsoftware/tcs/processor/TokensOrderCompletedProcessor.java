package com.dreamsoftware.tcs.processor;

import com.dreamsoftware.tcs.model.events.NewTokensOrderApprovedEvent;
import com.dreamsoftware.tcs.model.events.OnTokensOrderCompletedEvent;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.service.IUserService;
import java.util.function.Consumer;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Tokens Order Completed Processor
 *
 * @author ssanchez
 */
@Component("tokensOrderCompletedProcessor")
@RequiredArgsConstructor
public class TokensOrderCompletedProcessor implements Consumer<OnTokensOrderCompletedEvent> {

    private final Logger logger = LoggerFactory.getLogger(TokensOrderCompletedProcessor.class);

    /**
     * User Service
     */
    private final IUserService userService;

    @Override
    public void accept(OnTokensOrderCompletedEvent event) {
        logger.debug("TokensOrderCompletedProcessor called!");
        userService.completeOrder(event);
    }

}
