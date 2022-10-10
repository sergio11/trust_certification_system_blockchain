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
public class OnNewCertificationAuthorityRegistrationEvent extends AbstractRegistrationEvent {

    /**
     * Certification Authority Id
     */
    private String caId;

    /**
     * Wallet Hash
     */
    private String walletHash;
}
