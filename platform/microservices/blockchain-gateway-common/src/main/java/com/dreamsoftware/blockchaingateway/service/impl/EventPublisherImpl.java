package com.dreamsoftware.blockchaingateway.service.impl;

import com.dreamsoftware.blockchaingateway.service.IEventPublisher;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

/**
 *
 * @author ssanchez
 */
@Service
@RequiredArgsConstructor
public class EventPublisherImpl implements IEventPublisher {

    private final Logger logger = LoggerFactory.getLogger(EventPublisherImpl.class);

    /**
     * Stream Bridge
     */
    private final StreamBridge streamBridge;

    @Override
    public <T> void publish(T event) {
        logger.debug("publish CALLED!");
        streamBridge.send("eventsChannel-out-0", event);
    }
}
