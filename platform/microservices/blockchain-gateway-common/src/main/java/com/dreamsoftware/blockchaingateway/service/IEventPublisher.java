package com.dreamsoftware.blockchaingateway.service;

/**
 *
 * @author ssanchez
 * @param <T>
 */
public interface IEventPublisher<T> {

    /**
     * Publish Event
     *
     * @param event
     */
    void publish(final T event);
}
