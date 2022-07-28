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
public class CertificationCourseDetailDTO {

    @JsonProperty("identity")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("costOfIssuingCertificate")
    private Long costOfIssuingCertificate;
    @JsonProperty("costOfRenewingCertificate")
    private Long costOfRenewingCertificate;
    @JsonProperty("certificationAuthority")
    private String certificationAuthority;
    @JsonProperty("durationInHours")
    private Long durationInHours;
    @JsonProperty("expirationInDays")
    private Long expirationInDays;
    @JsonProperty("canBeRenewed")
    private Boolean canBeRenewed;
    @JsonProperty("isEnabled")
    private Boolean isEnabled;
    @JsonProperty("isExist")
    private Boolean isExist;
}
