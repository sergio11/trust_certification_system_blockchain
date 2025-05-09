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
public class SimpleCertificationAuthorityDetailDTO {

    /**
     * Id
     */
    @JsonProperty("identity")
    private String id;

    /**
     * Name
     */
    @JsonProperty("name")
    private String name;

    /**
     * Location
     */
    @JsonProperty("location")
    private String location;

    /**
     * Executive Director
     */
    @JsonProperty("executive_director")
    private String executiveDirector;
}
