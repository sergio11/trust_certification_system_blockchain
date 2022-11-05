package com.dreamsoftware.tcs.service.impl;

import com.dreamsoftware.tcs.processor.handlers.AbstractCertificateManagementHandler;
import com.dreamsoftware.tcs.service.ITrustCertificateService;
import com.dreamsoftware.tcs.stream.events.certificate.AbstractCertificateManagementEvent;
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
public class TrustCertificateServiceImpl implements ITrustCertificateService {

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
        final AbstractCertificateManagementEvent certificateManagementEvent = objectMapper.readValue(event.getPayload(), AbstractCertificateManagementEvent.class);
        final AbstractCertificateManagementHandler eventHandler = getHandler(certificateManagementEvent.getEntityType());
        return eventHandler.onHandle(certificateManagementEvent);
    }

    /**
     *
     * @param clazz
     * @return
     * @param <T>
     */
    private <T extends AbstractCertificateManagementEvent> AbstractCertificateManagementHandler<T, ? extends AbstractNotificationEvent> getHandler(Class<T> clazz) {
        log.debug("getHandler -> " + clazz.getCanonicalName());
        ResolvableType type = ResolvableType.forClassWithGenerics(AbstractCertificateManagementHandler.class, clazz);
        return applicationContext.<AbstractCertificateManagementHandler<T, ? extends AbstractNotificationEvent>>getBeanProvider(type).getObject();
    }

}
