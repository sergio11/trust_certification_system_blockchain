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
public class UserDetailDTO {

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
    @JsonProperty("full_name")
    private String fullName;

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
     * User Language
     */
    @JsonProperty("language")
    private String language;

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

    /**
     * Certification Authority
     */
    @JsonProperty("ca")
    private SimpleCertificationAuthorityDetailDTO ca;

}
