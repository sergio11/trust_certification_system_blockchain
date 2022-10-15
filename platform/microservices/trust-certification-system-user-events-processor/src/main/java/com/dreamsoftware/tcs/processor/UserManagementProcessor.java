package com.dreamsoftware.tcs.processor;

import com.dreamsoftware.tcs.service.IUserService;
import com.dreamsoftware.tcs.stream.events.notifications.AbstractNotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import java.util.function.Function;

/**
 * User Management Processor
 *
 * @author ssanchez
 */
@Component("userManagementProcessor")
@RequiredArgsConstructor
@Slf4j
public class UserManagementProcessor implements Function<GenericMessage<String>, AbstractNotificationEvent> {


    private final IUserService userRegistrationService;

    /**
     * @param event
     * @return
     */
    @Override
    public AbstractNotificationEvent apply(final GenericMessage<String> event) {
        log.debug("UserManagementProcessor CALLED!");
        AbstractNotificationEvent notificationEvent = null;
        try {
            notificationEvent = userRegistrationService.handle(event);
        } catch (Exception e) {
            log.error("UserManagementProcessor ex -> " + e.getMessage());
        }
        return notificationEvent;
    }
}
