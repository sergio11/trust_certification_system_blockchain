package com.dreamsoftware.tcs.utils;

public abstract class AbstractOnlyProcessHandler<T> {

    /**
     *
     * @param event
     */
    public abstract void onHandle(final T event);
}
