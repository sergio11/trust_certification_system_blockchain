package com.dreamsoftware.tcs.stream.events.notifications.ca;

import com.dreamsoftware.tcs.stream.events.notifications.AbstractNotificationEvent;
import com.dreamsoftware.tcs.utils.EntityAnnotation;
import lombok.*;

/**
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityAnnotation(entityClass = CertificationAuthorityDisabledNotificationEvent.class)
public class CertificationAuthorityDisabledNotificationEvent extends AbstractNotificationEvent {

    /**
     * Certification Authority Id
     */
    private String caId;
}
