package com.dreamsoftware.blockchaingateway.service;

/**
 *
 * @author ssanchez
 */
public interface IEventPublisher {

    /**
     * Publish Event
     *
     * @param <T>
     * @param event
     */
    <T> void publish(final T event);
}
