package com.dreamsoftware.blockchaingateway.web.dto.request;

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
public class RegisterCertificationAuthorityDTO {

    /**
     * Name
     */
    @JsonProperty("name")
    private String name;

    /**
     * Password
     */
    @JsonProperty("password")
    private String password;

    /**
     * Default Cost Of Issuing Certificate
     */
    @JsonProperty("defaultCostOfIssuingCertificate")
    private Long defaultCostOfIssuingCertificate;

}
