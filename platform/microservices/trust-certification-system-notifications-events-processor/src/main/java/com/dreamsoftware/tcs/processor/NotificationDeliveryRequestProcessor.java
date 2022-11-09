package com.dreamsoftware.tcs.processor;

import java.util.function.Consumer;

import com.dreamsoftware.tcs.service.IDispatcherEventHandlerService;
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

    private final IDispatcherEventHandlerService dispatcherEventHandlerService;

    /**
     *
     * @param notificationEvent
     */
    @Override
    public void accept(final GenericMessage<String> notificationEvent) {
        log.debug("NotificationDeliveryRequestProcessor CALLED!");
        dispatcherEventHandlerService.onlyProcessEvent(notificationEvent.getPayload());
    }

}
