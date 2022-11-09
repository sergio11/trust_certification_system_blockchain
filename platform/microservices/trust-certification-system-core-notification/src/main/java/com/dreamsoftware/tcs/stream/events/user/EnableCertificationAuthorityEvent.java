package com.dreamsoftware.tcs.stream.events.user;

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
@EntityAnnotation(entityClass = EnableCertificationAuthorityEvent.class)
public class EnableCertificationAuthorityEvent extends AbstractEvent {

    /**
     * Certification Authority Id
     */
    private String caId;

}
