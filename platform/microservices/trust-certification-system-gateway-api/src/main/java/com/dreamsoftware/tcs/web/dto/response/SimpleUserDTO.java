package com.dreamsoftware.tcs.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 *
 * @author ssanchez
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleUserDTO {

    public static String ACTIVATE_STATE = "ACTIVATE";
    public static String PENDING_DELETE_STATE = "PENDING_DELETE";
    public static String PENDING_ACTIVATE_STATE = "PENDING_ACTIVATE";

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

    /**
     * Auth Provider
     */
    @JsonProperty("auth_provider")
    private SimpleAuthenticationProviderDTO provider;

}
