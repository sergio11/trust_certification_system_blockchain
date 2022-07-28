package com.dreamsoftware.tcs.processor;

import com.dreamsoftware.tcs.model.events.OnUserRegisteredEvent;
import com.dreamsoftware.tcs.service.IUserService;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * User Registered Processor
 *
 * @author ssanchez
 */
@Component("userRegisteredProcessor")
@RequiredArgsConstructor
public class UserRegisteredProcessor implements Consumer<OnUserRegisteredEvent> {

    private final Logger logger = LoggerFactory.getLogger(UserRegisteredProcessor.class);

    /**
     * User Service
     */
    private final IUserService userService;

    @Override
    public void accept(OnUserRegisteredEvent event) {
        logger.debug("UserRegisteredProcessor CALLED!");
        userService.validate(event.getWalletHash());
    }
}
