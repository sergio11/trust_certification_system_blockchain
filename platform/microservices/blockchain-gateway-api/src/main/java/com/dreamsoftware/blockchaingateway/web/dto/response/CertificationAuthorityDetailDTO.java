package com.dreamsoftware.blockchaingateway.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class CertificationAuthorityDetailDTO {

    @JsonProperty("identity")
    private String id;

    /**
     * Name
     */
    @JsonProperty("name")
    private String name;

    /**
     * State
     */
    @JsonProperty("state")
    private String state;

    /**
     * Type
     */
    @JsonProperty("type")
    private String type;

    /**
     * Wallet Hash
     */
    @JsonProperty("wallet_hash")
    private String walletHash;

    /**
     * Email
     */
    @JsonProperty("email")
    private String email;

    /**
     * Default Cost Of Issuing Certificate
     */
    @JsonProperty("defaultCostOfIssuingCertificate")
    private Integer defaultCostOfIssuingCertificate;

    /**
     * Is Enabled
     */
    @JsonProperty("isEnabled")
    private Boolean isEnabled;

}
