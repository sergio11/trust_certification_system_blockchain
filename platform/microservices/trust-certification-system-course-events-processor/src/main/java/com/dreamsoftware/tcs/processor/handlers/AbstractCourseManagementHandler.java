package com.dreamsoftware.tcs.processor.handlers;

import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.stream.events.course.AbstractCourseManagementEvent;
import com.dreamsoftware.tcs.stream.events.notifications.AbstractNotificationEvent;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author ssanchez
 * @param <T>
 */
@Slf4j
public abstract class AbstractCourseManagementHandler<T extends AbstractCourseManagementEvent, E extends AbstractNotificationEvent> {

    /**
     * @param event
     * @return
     */
    public abstract E onHandle(T event) throws RepositoryException;

}
