package com.dreamsoftware.tcs.service;

import com.dreamsoftware.tcs.stream.events.notifications.AbstractNotificationEvent;
import org.springframework.messaging.support.GenericMessage;

/**
 *
 * @author ssanchez
 */
public interface ICertificateCourseService {

    /**
     *
     * @param event
     * @return
     * @throws Exception
     */
    AbstractNotificationEvent handle(final GenericMessage<String> event) throws Exception;
}
