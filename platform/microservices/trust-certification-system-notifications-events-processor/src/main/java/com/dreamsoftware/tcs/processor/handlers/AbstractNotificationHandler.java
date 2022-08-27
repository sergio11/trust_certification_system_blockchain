package com.dreamsoftware.tcs.processor.handlers;

import com.dreamsoftware.tcs.stream.events.notifications.AbstractNotificationEvent;

/**
 *
 * @author ssanchez
 * @param <T>
 */
public abstract class AbstractNotificationHandler<T extends AbstractNotificationEvent> {

    /**
     *
     * @param notification
     */
    public abstract void onHandle(T notification);

}
