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
public class UpdateCertificationCourseDTO {

    /**
     * Name
     */
    @JsonProperty("name")
    private String name;

    /**
     * Description
     */
    @JsonProperty("description")
    private String description;

    /**
     * Cost of Issuing Certificate
     */
    @JsonProperty("costOfIssuingCertificate")
    private Long costOfIssuingCertificate;

    /**
     * Cost of renewing certificate
     */
    @JsonProperty("costOfRenewingCertificate")
    private Long costOfRenewingCertificate;

}
