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
public class CertificateIssuanceRequestDTO {

    /**
     * Identity
     */
    @JsonProperty("identity")
    private String id;

    /**
     * Status
     */
    @JsonProperty("status")
    private String status;

    /**
     * Qualification
     */
    @JsonProperty("qualification")
    private Long qualification;

    /**
     * Created At
     */
    @JsonProperty("created_at")
    private Date createdAt;

    /**
     * Updated At
     */
    @JsonProperty("updated_at")
    private Date updatedAt;

    /**
     * Certificate Id
     */
    @JsonProperty("certificate_id")
    private String certificateId;

    /**
     * Course
     */
    @JsonProperty("course")
    private CertificationCourseEditionDetailDTO course;

    /**
     * Student
     */
    @JsonProperty("student")
    private SimpleUserDTO student;

    /**
     * CA Member
     */
    @JsonProperty("ca_member")
    private SimpleUserDTO caMember;

}
