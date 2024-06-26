package com.dreamsoftware.tcs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ssanchez
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletCredentials {

    private String name;
    private String secret;
    private String mnemonic;
}
