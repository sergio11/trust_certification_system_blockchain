package com.dreamsoftware.tcs.scheduling.events.course;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

/**
 * New Course Registration Requested Event
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NewCourseRegistrationRequestedEvent extends ApplicationEvent {

    private final String id;

    public NewCourseRegistrationRequestedEvent(Object source, final String id) {
        super(source);
        this.id = id;
    }
}
