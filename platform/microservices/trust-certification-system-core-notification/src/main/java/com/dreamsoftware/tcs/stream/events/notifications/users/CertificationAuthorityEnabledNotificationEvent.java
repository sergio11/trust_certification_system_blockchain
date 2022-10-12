package com.dreamsoftware.tcs.stream.events.notifications.users;

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
@EntityAnnotation(entityClass = CertificationAuthorityEnabledNotificationEvent.class)
public class CertificationAuthorityEnabledNotificationEvent extends AbstractNotificationEvent {

    /**
     * Certification Authority Id
     */
    private String caId;
}
