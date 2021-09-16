package com.dreamsoftware.blockchaingateway.service.impl;

import com.dreamsoftware.blockchaingateway.service.IEventPublisher;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;

/**
 *
 * @author ssanchez
 * @param <T>
 */
public class EventPublisherImpl<T> implements IEventPublisher<T> {

    private final Logger logger = LoggerFactory.getLogger(EventPublisherImpl.class);
    private final Many<T> eventSink = Sinks.many().multicast().onBackpressureBuffer();

    @Bean
    protected Supplier<Flux<T>> eventsChannel() {
        return () -> eventSink.asFlux();
    }

    @Override
    public void publish(T event) {
        logger.debug("publish event CALLED!");
        while (eventSink.tryEmitNext(event).isFailure()) {
            LockSupport.parkNanos(10);
        }
    }
}
