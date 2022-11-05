package com.dreamsoftware.tcs.stream.events.certificate;

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
@EntityAnnotation(entityClass = OnNewCertificateIssuedEvent.class)
public class OnNewCertificateIssuedEvent extends AbstractCertificateManagementEvent {

    /**
     * Certificate Id
     */
    private String certificateId;

    /**
     * Student Wallet Hash
     */
    private String studentWalletHash;

    /**
     * CA Wallet Hash
     */
    private String caWalletHash;

}
