package com.dreamsoftware.tcs.web.dto.request;

import com.dreamsoftware.tcs.web.validation.constraints.EmailShouldBeUnique;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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
public class SignupAsCaAdminDTO {

    /**
     * Certification Authority Name
     */
    @Schema(name = "name", description = "Certification Authority Name", required = true)
    @JsonProperty("name")
    @NotBlank(message = "{certification_authority_name_not_blank}")
    private String name;

    /**
     * Certification Authority Location
     */
    @Schema(name = "location", description = "Certification Authority Location", required = true)
    @JsonProperty("location")
    @NotBlank(message = "{certification_authority_location_not_blank}")
    private String location;

    /**
     * Certification Authority Executive Director
     */
    @Schema(name = "executive_director", description = "Certification Authority Executive Director", required = true)
    @JsonProperty("executive_director")
    @NotBlank(message = "{certification_authority_location_not_blank}")
    private String executiveDirector;

    /**
     * Default Cost of issuing certificate
     */
    @Schema(name = "default_cost_of_issuing_certificate", description = "Certification Authority Default Cost of issuing certificate", required = true)
    @JsonProperty("default_cost_of_issuing_certificate")
    private Integer defaultCostOfIssuingCertificate;

    /**
     * Email
     */
    @JsonProperty("support_mail")
    @NotBlank(message = "{support_mail_email_not_null}")
    @Email(message = "{support_mail_email_invalid}")
    @EmailShouldBeUnique(message = "{support_mail_email_unique}")
    private String supportMail;

    /**
     * Certification Authority Admin
     */
    @Schema(name = "admin", description = "Certification Authority Admin Full name", required = true)
    @JsonProperty("admin")
    private SignUpUserDTO admin;

}
