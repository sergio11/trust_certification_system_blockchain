package com.dreamsoftware.tcs.stream.events.course;

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
@EntityAnnotation(entityClass = DisableCertificationCourseEditionEvent.class)
public class DisableCertificationCourseEditionEvent extends AbstractCourseManagementEvent {

    /**
     * Certification Course Id
     */
    private String courseId;

    /**
     * Certification Course Edition Id
     */
    private String editionId;

    /**
     * CA Wallet Hash
     */
    private String caWalletHash;

}
