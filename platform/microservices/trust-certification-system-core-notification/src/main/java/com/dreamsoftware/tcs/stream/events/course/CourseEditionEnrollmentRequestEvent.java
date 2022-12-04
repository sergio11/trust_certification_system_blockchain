package com.dreamsoftware.tcs.stream.events.course;

import com.dreamsoftware.tcs.stream.events.AbstractEvent;
import com.dreamsoftware.tcs.utils.EntityAnnotation;
import lombok.*;

/**
 *
 * @author ssanchez
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityAnnotation(entityClass = CourseEditionEnrollmentRequestEvent.class)
public class CourseEditionEnrollmentRequestEvent extends AbstractEvent {

    /**
     * Certification Course Id
     */
    private String courseId;

    /**
     * Certification Course Edition Id
     */
    private String editionId;

    /**
     * Student Wallet Hash
     */
    private String studentWalletHash;

}
