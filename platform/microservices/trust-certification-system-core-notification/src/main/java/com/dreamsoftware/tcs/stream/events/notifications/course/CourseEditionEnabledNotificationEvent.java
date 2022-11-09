package com.dreamsoftware.tcs.stream.events.notifications.course;

import com.dreamsoftware.tcs.stream.events.AbstractEvent;
import com.dreamsoftware.tcs.utils.EntityAnnotation;
import lombok.*;

/**
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityAnnotation(entityClass = CourseEditionEnabledNotificationEvent.class)
public class CourseEditionEnabledNotificationEvent extends AbstractEvent {

    /**
     * Course Id
     */
    private String courseId;

    /**
     * Course Edition Id
     */
    private String editionId;

    /**
     * Course Name
     */
    private String name;

}
