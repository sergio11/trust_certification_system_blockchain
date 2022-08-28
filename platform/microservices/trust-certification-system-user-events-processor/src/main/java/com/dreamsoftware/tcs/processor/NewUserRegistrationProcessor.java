package com.dreamsoftware.tcs.processor;

import com.dreamsoftware.tcs.stream.events.user.OnUserRegisteredEvent;
import com.dreamsoftware.tcs.stream.events.user.OnNewUserRegistrationEvent;
import com.dreamsoftware.tcs.service.IUserService;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class NewUserRegistrationProcessor implements Function<OnNewUserRegistrationEvent, OnUserRegisteredEvent> {

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
    public OnUserRegisteredEvent apply(final OnNewUserRegistrationEvent event) {
        log.debug("NewUserRegistrationProcessor CALLED!");
        OnUserRegisteredEvent registeredEvent = null;
        try {
            userService.register(event);
            registeredEvent = new OnUserRegisteredEvent(event.getWalletHash());
        } catch (final Exception ex) {
            log.debug("Ex Message -> " + ex.getMessage());
        }
        return registeredEvent;
    }

}
