package com.dreamsoftware.tcs.stream.events.user.registration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ssanchez
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OnNewCertificationAuthorityMemberRegistrationEvent extends AbstractRegistrationEvent {

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
