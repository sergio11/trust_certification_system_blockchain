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
public class CertificateIssuedDTO {

    @JsonProperty("identity")
    private String id;
    @JsonProperty("issuerAddress")
    private String issuerAddress;
    @JsonProperty("recipientAddress")
    private String recipientAddress;
    @JsonProperty("course")
    private String course;
    @JsonProperty("expirationDate")
    private Long expirationDate;
    @JsonProperty("qualification")
    private Long qualification;
    @JsonProperty("durationInHours")
    private Long durationInHours;
    @JsonProperty("expeditionDate")
    private Long expeditionDate;
    @JsonProperty("isVisible")
    private Boolean isVisible;
    @JsonProperty("isEnabled")
    private Boolean isEnabled;
    @JsonProperty("isExist")
    private Boolean isExist;

}
