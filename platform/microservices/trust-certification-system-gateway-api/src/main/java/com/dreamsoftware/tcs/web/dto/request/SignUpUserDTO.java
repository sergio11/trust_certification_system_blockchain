package com.dreamsoftware.tcs.web.dto.request;

import com.dreamsoftware.tcs.web.validation.constraints.EmailShouldBeUnique;
import com.dreamsoftware.tcs.web.validation.constraints.FieldMatch;
import com.dreamsoftware.tcs.web.validation.constraints.ShouldBeValidISOLanguage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
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
@FieldMatch(first = "passwordClear", second = "confirmPassword",
        message = "{user_password_not_match}")
public class SignUpUserDTO {

    /**
     * Name
     */
    @Schema(description = "User full name", required = true)
    @JsonProperty("fullName")
    @NotBlank(message = "{user_name_not_null}")
    @Size(min = 2, max = 45, message = "{user_name_size}")
    private String fullName;

    /**
     * Email
     */
    @Schema(description = "User email", required = true)
    @JsonProperty("email")
    @NotBlank(message = "{user_email_not_null}")
    @Email(message = "{user_email_invalid}")
    @EmailShouldBeUnique(message = "{user_email_unique}")
    private String email;

    /**
     * Password Clear
     */
    @Schema(description = "Password clear", required = true)
    @JsonProperty("passwordClear")
    @NotBlank(message = "{user_password_not_null}")
    @Size(min = 8, max = 25, message = "{user_password_size}")
    protected String passwordClear;

    /**
     * Confirm Password
     */
    @Schema(description = "Confirm clear", required = true)
    @JsonProperty("confirmPassword")
    @NotBlank(message = "{user_confirm_password_not_null}")
    protected String confirmPassword;

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
