package com.dreamsoftware.tcs.processor;

import com.dreamsoftware.tcs.service.INotificationDeliveryRequestService;
import com.dreamsoftware.tcs.stream.events.notifications.AbstractNotificationEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

/**
 * Notification Delivery Request Processor
 *
 * @author ssanchez
 */
@Slf4j
@Component("notificationDeliveryRequestProcessor")
@RequiredArgsConstructor
public class NotificationDeliveryRequestProcessor implements Consumer<GenericMessage<String>> {

    private final INotificationDeliveryRequestService notificationDeliveryRequestService;

    /**
     *
     * @param notificationEvent
     */
    @Override
    public void accept(final GenericMessage<String> notificationEvent) {
        log.debug("NotificationDeliveryRequestProcessor CALLED!");
        notificationDeliveryRequestService.handle(notificationEvent);
    }

}
