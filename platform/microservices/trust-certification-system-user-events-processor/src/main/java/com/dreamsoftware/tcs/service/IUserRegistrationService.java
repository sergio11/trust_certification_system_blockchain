package com.dreamsoftware.tcs.service;

import com.dreamsoftware.tcs.stream.events.notifications.AbstractNotificationEvent;
import org.springframework.messaging.support.GenericMessage;

public interface IUserRegistrationService {

    /**
     *
     * @param event
     * @return
     * @throws Exception
     */
    AbstractNotificationEvent handle(final GenericMessage<String> event) throws Exception;
}
