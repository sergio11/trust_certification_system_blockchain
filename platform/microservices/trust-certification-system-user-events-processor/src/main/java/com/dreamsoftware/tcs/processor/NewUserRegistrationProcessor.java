package com.dreamsoftware.tcs.processor;

import com.dreamsoftware.tcs.service.IUserRegistrationService;
import com.dreamsoftware.tcs.stream.events.user.OnUserRegisteredEvent;
import com.dreamsoftware.tcs.service.IUserService;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

/**
 * New User Registration Processor
 *
 * @author ssanchez
 */
@Component("newUserRegistrationProcessor")
@RequiredArgsConstructor
@Slf4j
public class NewUserRegistrationProcessor implements Function<GenericMessage<String>, OnUserRegisteredEvent> {

    /**
     * User Registration Service
     */
    private final IUserRegistrationService userRegistrationService;

    /**
     *
     * @param event
     * @return
     */
    @Override
    public OnUserRegisteredEvent apply(final GenericMessage<String> event) {
        log.debug("NewUserRegistrationProcessor CALLED!");
        OnUserRegisteredEvent registeredEvent = null;
        try {
            final String userWalletHash = userRegistrationService.handle(event);
            registeredEvent = new OnUserRegisteredEvent(userWalletHash);
        } catch (final Exception ex) {
            log.debug("Ex Message -> " + ex.getMessage());
        }
        return registeredEvent;
    }

}
