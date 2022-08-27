package com.dreamsoftware.tcs.stream.events.notifications.certificate;

import com.dreamsoftware.tcs.stream.events.notifications.AbstractNotificationEvent;
import com.dreamsoftware.tcs.utils.EntityAnnotation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@EntityAnnotation(entityClass = CertificateVisibilityChangedNotificationEvent.class)
public class CertificateVisibilityChangedNotificationEvent extends AbstractNotificationEvent {

    /**
     * Certification Id
     */
    private final String id;

    /**
     * Is Visible
     */
    private final Boolean isVisible;

}
