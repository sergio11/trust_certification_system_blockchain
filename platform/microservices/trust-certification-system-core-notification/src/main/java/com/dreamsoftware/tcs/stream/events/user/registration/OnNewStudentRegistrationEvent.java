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
public class OnNewStudentRegistrationEvent extends AbstractRegistrationEvent {

    /**
     * Wallet Hash
     */
    private String walletHash;

}
