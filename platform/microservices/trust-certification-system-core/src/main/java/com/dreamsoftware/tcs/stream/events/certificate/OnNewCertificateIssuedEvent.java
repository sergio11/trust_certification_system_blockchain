package com.dreamsoftware.tcs.stream.events.certificate;

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
public class OnNewCertificateIssuedEvent {

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
