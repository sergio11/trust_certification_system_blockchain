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
@EntityAnnotation(entityClass = NewCertificationCourseEditionRegistrationRequestEvent.class)
public class NewCertificationCourseEditionRegistrationRequestEvent extends AbstractCourseManagementEvent {

    /**
     *  Course Id
     */
    private String courseId;

    /**
     * Course Edition Id
     */
    private String courseEditionId;

    /**
     * Ca Wallet
     */
    private String caWalletHash;
}
