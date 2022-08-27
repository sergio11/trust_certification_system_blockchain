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
@EntityAnnotation(entityClass = CertificationCourseRegisteredNotificationEvent.class)
public class CertificationCourseRegisteredNotificationEvent extends AbstractNotificationEvent {

    /**
     * Course Id
     */
    private final String courseId;

    /**
     * Course Name
     */
    private final String courseName;

}
