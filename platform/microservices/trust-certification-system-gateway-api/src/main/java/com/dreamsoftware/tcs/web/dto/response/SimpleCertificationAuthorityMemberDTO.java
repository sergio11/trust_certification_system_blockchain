package com.dreamsoftware.tcs.web.dto.response;

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
public class SimpleCertificationAuthorityMemberDTO {

    /**
     * User Identity
     */
    @JsonProperty("identity")
    private String identity;

    /**
     * User name
     */
    @JsonProperty("name")
    private String name;

    /**
     * User Type
     */
    @JsonProperty("type")
    private String type;

    /**
     * User Email
     */
    @JsonProperty("email")
    private String email;

    /**
     * Wallet Hash
     */
    @JsonProperty("wallet_hash")
    private String walletHash;

    /**
     * User State
     */
    @JsonProperty("state")
    private String state;

}
