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
@EntityAnnotation(entityClass = EnableCertificationAuthorityEvent.class)
public class EnableCertificationAuthorityEvent extends AbstractUserManagementEvent {

    /**
     * Certification Authority Id
     */
    private String caId;

}
