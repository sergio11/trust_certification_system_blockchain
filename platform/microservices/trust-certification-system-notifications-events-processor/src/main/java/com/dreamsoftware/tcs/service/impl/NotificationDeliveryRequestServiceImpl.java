package com.dreamsoftware.tcs.service.impl;

import com.dreamsoftware.tcs.processor.handlers.AbstractNotificationHandler;
import com.dreamsoftware.tcs.service.INotificationDeliveryRequestService;
import com.dreamsoftware.tcs.stream.events.notifications.AbstractNotificationEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

/**
 *
 * @author ssanchez
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationDeliveryRequestServiceImpl implements INotificationDeliveryRequestService {

    private final ObjectMapper objectMapper;
    private final ApplicationContext applicationContext;

    /**
     *
     * @param notification
     */
    @Override
    public void handle(GenericMessage<String> notification) {
        try {
            final AbstractNotificationEvent notificationEvent = objectMapper.readValue(notification.getPayload(), AbstractNotificationEvent.class);
            final AbstractNotificationHandler notificationHandler = getNotificationHandler(notificationEvent.getEntityType());
            notificationHandler.onHandle(notificationEvent);
        } catch (JsonProcessingException ex) {
            log.debug("NotificationDeliveryRequest handle ex -> " + ex.getMessage());
        }
    }

    /**
     *
     * @param <T>
     * @param clazz
     * @return
     */
    private <T extends AbstractNotificationEvent> AbstractNotificationHandler<T> getNotificationHandler(Class<T> clazz) {
        ResolvableType type = ResolvableType.forClassWithGenerics(AbstractNotificationEvent.class, clazz);
        return (AbstractNotificationHandler<T>) applicationContext.getBeanProvider(type).getObject();
    }

}
