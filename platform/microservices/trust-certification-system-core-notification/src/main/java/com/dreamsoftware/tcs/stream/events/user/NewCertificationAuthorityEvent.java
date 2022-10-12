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
@EntityAnnotation(entityClass = NewCertificationAuthorityEvent.class)
public class NewCertificationAuthorityEvent extends AbstractUserManagementEvent {

    /**
     * Certification Authority Id
     */
    private String caId;

    /**
     * Wallet Hash
     */
    private String walletHash;
}
