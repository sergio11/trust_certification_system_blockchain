package com.dreamsoftware.tcs.service.impl;

import com.dreamsoftware.tcs.processor.handlers.AbstractUserManagementHandler;
import com.dreamsoftware.tcs.service.IUserRegistrationService;
import com.dreamsoftware.tcs.stream.events.notifications.AbstractNotificationEvent;
import com.dreamsoftware.tcs.stream.events.user.AbstractUserManagementEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRegistrationServiceImpl implements IUserRegistrationService {

    private final ObjectMapper objectMapper;
    private final ApplicationContext applicationContext;

    /**
     *
     * @param event
     * @return
     * @throws Exception
     */
    @Override
    public AbstractNotificationEvent handle(GenericMessage<String> event) throws Exception {
        log.debug("Event payload -> " + event.getPayload());
        final AbstractUserManagementEvent registrationEvent = objectMapper.readValue(event.getPayload(), AbstractUserManagementEvent.class);
        final AbstractUserManagementHandler eventHandler = getRegistrationHandler(registrationEvent.getEntityType());
        return eventHandler.onHandle(registrationEvent);
    }

    /**
     *
     * @param clazz
     * @return
     * @param <T>
     */
    private <T extends AbstractUserManagementEvent> AbstractUserManagementHandler<T> getRegistrationHandler(Class<T> clazz) {
        log.debug("getRegistrationHandler -> " + clazz.getCanonicalName());
        ResolvableType type = ResolvableType.forClassWithGenerics(AbstractUserManagementHandler.class, clazz);
        return applicationContext.<AbstractUserManagementHandler<T>>getBeanProvider(type).getObject();
    }
}
