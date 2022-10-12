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
@EntityAnnotation(entityClass = RemoveCertificationAuthorityEvent.class)
public class RemoveCertificationAuthorityEvent extends AbstractUserManagementEvent {

    /**
     * Certification Authority Id
     */
    private String caId;

}
