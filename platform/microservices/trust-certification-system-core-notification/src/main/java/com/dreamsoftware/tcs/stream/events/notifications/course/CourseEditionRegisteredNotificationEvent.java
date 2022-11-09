package com.dreamsoftware.tcs.stream.events.notifications.course;

import com.dreamsoftware.tcs.stream.events.AbstractEvent;
import com.dreamsoftware.tcs.utils.EntityAnnotation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityAnnotation(entityClass = CourseEditionRegisteredNotificationEvent.class)
public class CourseEditionRegisteredNotificationEvent extends AbstractEvent {

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
     * CA Member Wallet Hash
     */
    private String caWalletHash;


}
