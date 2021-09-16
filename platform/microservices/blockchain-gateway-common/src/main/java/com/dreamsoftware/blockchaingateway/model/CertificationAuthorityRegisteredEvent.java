package com.dreamsoftware.blockchaingateway.model;

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
public class CertificationAuthorityRegisteredEvent {

    private String walletHash;

}
