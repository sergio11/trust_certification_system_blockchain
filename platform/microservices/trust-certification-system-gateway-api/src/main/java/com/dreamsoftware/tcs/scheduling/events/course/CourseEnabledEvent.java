package com.dreamsoftware.tcs.scheduling.events.course;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

/**
 * Course Enabled Event
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CourseEnabledEvent extends ApplicationEvent {

    private final String id;

    public CourseEnabledEvent(Object source, final String id) {
        super(source);
        this.id = id;
    }
}
