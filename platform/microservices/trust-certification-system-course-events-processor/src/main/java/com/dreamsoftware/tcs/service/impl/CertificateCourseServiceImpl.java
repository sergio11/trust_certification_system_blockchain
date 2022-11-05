package com.dreamsoftware.tcs.service.impl;

import com.dreamsoftware.tcs.processor.handlers.AbstractCourseManagementHandler;
import com.dreamsoftware.tcs.service.ICertificateCourseService;
import com.dreamsoftware.tcs.stream.events.course.AbstractCourseManagementEvent;
import com.dreamsoftware.tcs.stream.events.notifications.AbstractNotificationEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 *
 * @author ssanchez
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CertificateCourseServiceImpl implements ICertificateCourseService {

    private final ObjectMapper objectMapper;
    private final ApplicationContext applicationContext;

    /**
     *
     * @param event
     * @return
     * @throws Exception
     */
    @Override
    public AbstractNotificationEvent handle(final GenericMessage<String> event) throws Exception {
        Assert.notNull(event, "Event can not be null");
        log.debug("Event payload -> " + event.getPayload());
        final AbstractCourseManagementEvent courseManagementEvent = objectMapper.readValue(event.getPayload(), AbstractCourseManagementEvent.class);
        final AbstractCourseManagementHandler eventHandler = getHandler(courseManagementEvent.getEntityType());
        return eventHandler.onHandle(courseManagementEvent);
    }

    /**
     *
     * @param clazz
     * @return
     * @param <T>
     */
    private <T extends AbstractCourseManagementEvent> AbstractCourseManagementHandler<T, ? extends AbstractNotificationEvent> getHandler(Class<T> clazz) {
        log.debug("getHandler -> " + clazz.getCanonicalName());
        ResolvableType type = ResolvableType.forClassWithGenerics(AbstractCourseManagementHandler.class, clazz);
        return applicationContext.<AbstractCourseManagementHandler<T, ? extends AbstractNotificationEvent>>getBeanProvider(type).getObject();
    }
}
