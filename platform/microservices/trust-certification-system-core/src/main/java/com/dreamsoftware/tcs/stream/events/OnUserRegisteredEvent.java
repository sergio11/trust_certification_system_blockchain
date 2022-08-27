package com.dreamsoftware.tcs.stream.events;

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
public class OnUserRegisteredEvent {

    /**
     * Wallet Hash
     */
    private String walletHash;

}
