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
@EntityAnnotation(entityClass = CourseDisabledNotificationEvent.class)
public class CourseDisabledNotificationEvent extends AbstractEvent {

    /**
     * Course Id
     */
    private String id;

}
