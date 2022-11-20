package com.dreamsoftware.tcs.web.dto.request;

import com.dreamsoftware.tcs.web.validation.constraints.EmailShouldBeUnique;
import com.dreamsoftware.tcs.web.validation.constraints.ShouldBeValidISOLanguage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
public class SignUpAsCaAdminDTO {

    /**
     * Certification Authority Name
     */
    @Schema(name = "caName", description = "Certification Authority Name", required = true)
    @JsonProperty("caName")
    @NotBlank(message = "{certification_authority_name_not_blank}")
    private String caName;

    /**
     * Certification Authority Location
     */
    @Schema(name = "caLocation", description = "Certification Authority Location", required = true)
    @JsonProperty("caLocation")
    @NotBlank(message = "{certification_authority_location_not_blank}")
    private String caLocation;

    /**
     * Certification Authority Executive Director
     */
    @Schema(name = "caExecutiveDirector", description = "Certification Authority Executive Director", required = true)
    @JsonProperty("caExecutiveDirector")
    @NotBlank(message = "{certification_authority_executive_director_not_blank}")
    private String caExecutiveDirector;

    /**
     * Default Cost of issuing certificate
     */
    @Schema(name = "defaultCostOfIssuingCertificate", description = "Certification Authority Default Cost of issuing certificate", required = true)
    @JsonProperty("defaultCostOfIssuingCertificate")
    private Integer defaultCostOfIssuingCertificate;

    /**
     * Email
     */
    @Schema(name = "caSupportMail", description = "Certification Authority Contact email", required = true)
    @JsonProperty("caSupportMail")
    @NotBlank(message = "{support_mail_email_not_null}")
    @Email(message = "{support_mail_email_invalid}")
    @EmailShouldBeUnique(message = "{support_mail_email_unique}")
    private String caSupportMail;

    /**
     * Name
     */
    @Schema(name = "caAdminFullName", description = "CA Admin full name", required = true)
    @JsonProperty("caAdminFullName")
    @NotBlank(message = "{user_name_not_null}")
    @Size(min = 2, max = 45, message = "{user_name_size}")
    private String caAdminFullName;

    /**
     * Email
     */
    @Schema(name = "caAdminEmail", description = "CA Admin email", required = true)
    @JsonProperty("caAdminEmail")
    @NotBlank(message = "{user_email_not_null}")
    @Email(message = "{user_email_invalid}")
    @EmailShouldBeUnique(message = "{user_email_unique}")
    private String caAdminEmail;

    /**
     * Password Clear
     */
    @JsonProperty("caAdminPasswordClear")
    @NotBlank(message = "{user_password_not_null}")
    @Size(min = 8, max = 25, message = "{user_password_size}")
    protected String caAdminPasswordClear;

    /**
     * Confirm Password
     */
    @JsonProperty("caAdminConfirmPassword")
    @NotBlank(message = "{user_confirm_password_not_null}")
    protected String caAdminConfirmPassword;

    /**
     * User Agent
     */
    @JsonIgnore
    private String userAgent;

    /**
     * User Language
     */
    @JsonProperty("language")
    @ShouldBeValidISOLanguage(message = "{user_language_invalid}")
    private String language;

}
