package com.dreamsoftware.tcs.scheduling.events.course;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

/**
 * Course Disabled Event
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CourseDisabledEvent extends ApplicationEvent {

    private final String id;

    public CourseDisabledEvent(Object source, final String id) {
        super(source);
        this.id = id;
    }
}