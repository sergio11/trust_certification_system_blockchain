package com.dreamsoftware.tcs.stream.events.certificate;

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
@EntityAnnotation(entityClass = OnNewCertificateRequestAcceptedEvent.class)
public class OnNewCertificateRequestAcceptedEvent extends AbstractEvent {

    /**
     * Certification Request Id
     */
    private String certificationRequestId;
}
