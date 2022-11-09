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
@EntityAnnotation(entityClass = DisableCertificateRequestEvent.class)
public class DisableCertificateRequestEvent extends AbstractEvent {

    /**
     * Student Wallet Hash
     */
    private String studentWalletHash;

    /**
     * Certificate Id
     */
    private String certificationId;
}
