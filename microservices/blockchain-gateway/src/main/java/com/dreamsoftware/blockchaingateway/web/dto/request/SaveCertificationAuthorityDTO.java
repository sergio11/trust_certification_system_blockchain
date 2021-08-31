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
public class SaveCertificationAuthorityDTO {

    /**
     * Name
     */
    @JsonProperty("name")
    private String name;

    /**
     * Secret
     */
    @JsonProperty("secret")
    private String secret;

    /**
     * Default Cost Of Issuing Certificate
     */
    @JsonProperty("defaultCostOfIssuingCertificate")
    private Integer defaultCostOfIssuingCertificate;

}
