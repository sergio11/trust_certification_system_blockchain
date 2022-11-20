package com.dreamsoftware.tcs.utils;

import com.dreamsoftware.tcs.stream.events.AbstractEvent;

public abstract class AbstractProcessAndReturnHandler<T> {

    /**
     *
     * @param event
     */
    public abstract AbstractEvent onHandle(final T event) throws Exception;
}
