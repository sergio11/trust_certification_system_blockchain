package com.dreamsoftware.tcs.service.impl;

import com.dreamsoftware.tcs.service.IDispatcherEventHandlerService;
import com.dreamsoftware.tcs.stream.events.AbstractEvent;
import com.dreamsoftware.tcs.utils.AbstractOnlyProcessHandler;
import com.dreamsoftware.tcs.utils.AbstractProcessAndReturnHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Slf4j
@Service
@RequiredArgsConstructor
public class DispatcherEventHandlerServiceImpl implements IDispatcherEventHandlerService {

    private final ObjectMapper objectMapper;
    private final ApplicationContext applicationContext;

    /**
     *
     * @param eventPayload
     */
    @Override
    public void onlyProcessEvent(final String eventPayload) {
        Assert.notNull(eventPayload, "Event can not be null");
        try {
            log.debug("Event payload -> " + eventPayload);
            final AbstractEvent eventData = objectMapper.readValue(eventPayload, AbstractEvent.class);
            final AbstractOnlyProcessHandler eventHandler = getOnlyProcessEventHandler(eventData.getEntityType());
            eventHandler.onHandle(eventData);
        } catch (JsonProcessingException ex) {
            log.debug("DispatcherEventHandler ex -> " + ex.getMessage());
        }
    }

    /**
     *
     * @param eventPayload
     * @return
     * @throws Exception
     */
    @Override
    public AbstractEvent processEventAndGetResult(final String eventPayload) throws Exception {
        Assert.notNull(eventPayload, "Event can not be null");
        AbstractEvent result = null;
        try {
            log.debug("Event payload -> " + eventPayload);
            final AbstractEvent eventData = objectMapper.readValue(eventPayload, AbstractEvent.class);
            final AbstractProcessAndReturnHandler eventHandler = getProcessAndReturnResultHandler(eventData.getEntityType());
            result = eventHandler.onHandle(eventData);
        } catch (JsonProcessingException ex) {
            log.debug("DispatcherEventHandler ex -> " + ex.getMessage());
        }
        return result;
    }

    /**
     *
     * @param clazz
     * @return
     * @param <T>
     */
    private <T extends AbstractEvent> AbstractOnlyProcessHandler<T> getOnlyProcessEventHandler(Class<T> clazz) {
        log.debug("getOnlyProcessEventHandler for -> " + clazz.getCanonicalName());
        ResolvableType type = ResolvableType.forClassWithGenerics(AbstractOnlyProcessHandler.class, clazz);
        return applicationContext.<AbstractOnlyProcessHandler<T>>getBeanProvider(type).getObject();
    }

    /**
     *
     * @param clazz
     * @return
     * @param <T>
     */
    private <T extends AbstractEvent> AbstractProcessAndReturnHandler<T> getProcessAndReturnResultHandler(Class<T> clazz) {
        log.debug("getProcessAndReturnResultHandler for -> " + clazz.getCanonicalName());
        ResolvableType type = ResolvableType.forClassWithGenerics(AbstractProcessAndReturnHandler.class, clazz);
        return applicationContext.<AbstractProcessAndReturnHandler<T>>getBeanProvider(type).getObject();
    }
}
