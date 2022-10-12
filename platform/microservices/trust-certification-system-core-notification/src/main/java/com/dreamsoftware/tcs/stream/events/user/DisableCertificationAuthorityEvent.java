package com.dreamsoftware.tcs.stream.events.user;

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
@EntityAnnotation(entityClass = DisableCertificationAuthorityEvent.class)
public class DisableCertificationAuthorityEvent extends AbstractUserManagementEvent {

    /**
     * Certification Authority Id
     */
    private String caId;

}
