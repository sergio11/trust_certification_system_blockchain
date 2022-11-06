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
public class CertificateIssuedDetailDTO {


    /**
     * certificate
     */
    @JsonProperty("certificate")
    private SimpleCertificateIssuedDTO certificate;

    /**
     * Student
     */
    @JsonProperty("student")
    private SimpleUserDTO student;

    /**
     * Ca Member
     */
    @JsonProperty("ca_member")
    private SimpleUserDTO caMember;

    /**
     * Course
     */
    @JsonProperty("course_edition")
    private CertificationCourseEditionDetailDTO course;

}
