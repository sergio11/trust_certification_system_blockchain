package com.dreamsoftware.tcs.scheduling.events.course;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

/**
 * Course Deleted Event
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CourseDeletedEvent extends ApplicationEvent {

    private final String id;
    private final String name;

    public CourseDeletedEvent(Object source, final String id, final String name) {
        super(source);
        this.id = id;
        this.name = name;
    }
}
