package com.dreamsoftware.tcs.stream.events.notifications.course;

import com.dreamsoftware.tcs.stream.events.notifications.AbstractNotificationEvent;
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
@EntityAnnotation(entityClass = CertificationCourseEditionUpdatedNotificationEvent.class)
public class CertificationCourseEditionUpdatedNotificationEvent extends AbstractNotificationEvent {

    /**
     * Course Id
     */
    private String courseId;

    /**
     * Edition Id
     */
    private String editionId;

    /**
     * Course Name
     */
    private String courseName;


}
