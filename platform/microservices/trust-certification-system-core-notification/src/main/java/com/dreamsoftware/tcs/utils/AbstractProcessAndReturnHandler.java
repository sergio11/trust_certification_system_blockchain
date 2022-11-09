package com.dreamsoftware.tcs.utils;

public abstract class AbstractProcessAndReturnHandler<T, E> {

    /**
     *
     * @param event
     */
    public abstract E onHandle(final T event) throws Exception;
}
