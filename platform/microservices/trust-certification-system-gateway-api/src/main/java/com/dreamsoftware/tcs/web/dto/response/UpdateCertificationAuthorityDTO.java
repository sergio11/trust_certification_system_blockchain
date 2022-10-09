package com.dreamsoftware.tcs.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigInteger;
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
public class UpdateCertificationAuthorityDTO {

    /**
     * Certification Authority Name
     */
    @JsonProperty("name")
    private String name;

    /**
     *
     */
    @JsonProperty("defaultCostOfIssuingCertificate")
    private BigInteger defaultCostOfIssuingCertificate;

    /**
     * Certification Authority Location
     */
    @JsonProperty("location")
    private String location;

    /**
     * Certification Authority Director
     */
    @JsonProperty("executive_director")
    private String executiveDirector;

    /**
     * Support Mail
     */
    @JsonProperty("support_mail")
    private String supportMail;

}
