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
@EntityAnnotation(entityClass = UpdateCertificateVisibilityRequestEvent.class)
public class UpdateCertificateVisibilityRequestEvent extends AbstractCertificateManagementEvent {

    /**
     * Student Wallet Hash
     */
    private String studentWalletHash;

    /**
     * Certificate Id
     */
    private String certificationId;

    /**
     * Is Visible
     */
    private Boolean isVisible;
}
