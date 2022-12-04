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
@EntityAnnotation(entityClass = CourseEditionEnrolledNotificationEvent.class)
public class CourseEditionEnrolledNotificationEvent extends AbstractEvent {

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

    /**
     * Student Member Wallet Hash
     */
    private String studentWalletHash;


}
