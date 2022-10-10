package com.dreamsoftware.tcs.service.impl;

import com.dreamsoftware.tcs.processor.handlers.AbstractRegistrationHandler;
import com.dreamsoftware.tcs.service.IUserRegistrationService;
import com.dreamsoftware.tcs.stream.events.user.registration.AbstractRegistrationEvent;
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
    public String handle(GenericMessage<String> event) throws Exception {
        log.debug("Event payload -> " + event.getPayload());
        final AbstractRegistrationEvent registrationEvent = objectMapper.readValue(event.getPayload(), AbstractRegistrationEvent.class);
        final AbstractRegistrationHandler eventHandler = getRegistrationHandler(registrationEvent.getEntityType());
        return eventHandler.onHandle(registrationEvent);
    }

    /**
     *
     * @param clazz
     * @return
     * @param <T>
     */
    private <T extends AbstractRegistrationEvent> AbstractRegistrationHandler<T> getRegistrationHandler(Class<T> clazz) {
        log.debug("getRegistrationHandler -> " + clazz.getCanonicalName());
        ResolvableType type = ResolvableType.forClassWithGenerics(AbstractRegistrationHandler.class, clazz);
        return applicationContext.<AbstractRegistrationHandler<T>>getBeanProvider(type).getObject();
    }
}
