package com.dreamsoftware.tcs.service;

import org.springframework.messaging.support.GenericMessage;

/**
 *
 * @author ssanchez
 */
public interface INotificationDeliveryRequestService {

    /**
     *
     * @param notification
     */
    void handle(final GenericMessage<String> notification);

}
