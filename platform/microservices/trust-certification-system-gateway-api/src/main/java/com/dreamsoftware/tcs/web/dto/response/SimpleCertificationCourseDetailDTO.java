package com.dreamsoftware.tcs.web.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 *
 * @author ssanchez
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleCertificationCourseDetailDTO {

    /**
     * Identity
     */
    @JsonProperty("identity")
    private String id;

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
     * Created At
     */
    @JsonProperty("created_at")
    private Date createdAt;

    /**
     * Status
     */
    @JsonProperty("status")
    private String status;

    /**
     * Certification Authority
     */
    @JsonProperty("certification_authority")
    private SimpleCertificationAuthorityDetailDTO ca;
}
