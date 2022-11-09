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
@EntityAnnotation(entityClass = OnNewIssueCertificateRequestEvent.class)
public class OnNewIssueCertificateRequestEvent extends AbstractEvent {

    /**
     * Student Wallet Hash
     */
    private String studentWalletHash;

    /**
     * CA Wallet Hash
     */
    private String caWalletHash;

    /**
     * Course Id
     */
    private String courseId;

    /**
     * Qualification
     */
    private Long qualification;

    /**
     * Certification Request Id
     */
    private String certificationRequestId;
}
