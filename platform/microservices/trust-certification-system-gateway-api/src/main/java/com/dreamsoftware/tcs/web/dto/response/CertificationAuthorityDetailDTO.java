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
public class CertificationAuthorityDetailDTO {

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

    /**
     * Support Mail
     */
    @JsonProperty("support_mail")
    private String supportMail;

    /**
     * Default Cost Of Issuing Certificate
     */
    @JsonProperty("defaultCostOfIssuingCertificate")
    private Integer defaultCostOfIssuingCertificate;

    /**
     * Created Date
     */
    @JsonProperty("created_date")
    private Date createdDate;

    /**
     * Members
     */
    @JsonProperty("members")
    private Iterable<SimpleCertificationAuthorityMemberDTO> members;


}
