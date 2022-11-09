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
@EntityAnnotation(entityClass = DisableCertificationCourseEvent.class)
public class DisableCertificationCourseEvent extends AbstractEvent {

    /**
     * Certification Course Id
     */
    private String courseId;

    /**
     * CA Wallet Hash
     */
    private String caWalletHash;

}
