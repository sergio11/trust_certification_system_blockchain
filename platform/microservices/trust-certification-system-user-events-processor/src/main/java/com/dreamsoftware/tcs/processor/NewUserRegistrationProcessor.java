package com.dreamsoftware.tcs.processor;

import com.dreamsoftware.tcs.model.events.OnUserRegisteredEvent;
import com.dreamsoftware.tcs.model.events.OnNewUserRegistrationEvent;
import com.dreamsoftware.tcs.service.IUserService;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * New User Registration Processor
 *
 * @author ssanchez
 */
@Component("newUserRegistrationProcessor")
@RequiredArgsConstructor
public class NewUserRegistrationProcessor implements Function<OnNewUserRegistrationEvent, OnUserRegisteredEvent> {

    private final Logger logger = LoggerFactory.getLogger(NewUserRegistrationProcessor.class);

    /**
     * User Service
     */
    private final IUserService userService;

    @Override
    public OnUserRegisteredEvent apply(OnNewUserRegistrationEvent event) {
        logger.debug("NewUserRegistrationProcessor CALLED!");
        OnUserRegisteredEvent registeredEvent = null;
        try {
            userService.register(event);
            registeredEvent = new OnUserRegisteredEvent(event.getWalletHash());
        } catch (final Exception ex) {
            logger.debug("Ex Message -> " + ex.getMessage());
        }
        return registeredEvent;
    }

}
