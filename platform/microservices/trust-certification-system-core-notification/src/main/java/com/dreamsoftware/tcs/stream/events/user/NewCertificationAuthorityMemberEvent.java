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
@EntityAnnotation(entityClass = NewCertificationAuthorityMemberEvent.class)
public class NewCertificationAuthorityMemberEvent extends AbstractUserManagementEvent {

    /**
     * Certification Authority ID
     */
    private String caId;

    /**
     * CA Admin Wallet Hash
     */
    private String adminWalletHash;

    /**
     * Member Wallet Hash
     */
    private String memberWalletHash;
}
