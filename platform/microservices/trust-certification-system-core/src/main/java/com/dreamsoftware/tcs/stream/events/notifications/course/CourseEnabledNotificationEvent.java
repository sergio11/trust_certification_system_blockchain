package com.dreamsoftware.tcs.stream.events.notifications.course;

import com.dreamsoftware.tcs.stream.events.notifications.AbstractNotificationEvent;
import com.dreamsoftware.tcs.utils.EntityAnnotation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@EntityAnnotation(entityClass = CourseEnabledNotificationEvent.class)
public class CourseEnabledNotificationEvent extends AbstractNotificationEvent {

    private final String id;
    private final String name;

}
