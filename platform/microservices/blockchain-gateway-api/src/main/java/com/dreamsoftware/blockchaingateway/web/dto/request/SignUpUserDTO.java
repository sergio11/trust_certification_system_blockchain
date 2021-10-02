package com.dreamsoftware.blockchaingateway.web.dto.request;

import com.dreamsoftware.blockchaingateway.web.validation.constraints.EmailShouldBeUnique;
import com.dreamsoftware.blockchaingateway.web.validation.constraints.FieldMatch;
import com.dreamsoftware.blockchaingateway.web.validation.constraints.IExtended;
import com.dreamsoftware.blockchaingateway.web.validation.constraints.ShouldBeValidISOLanguage;
import com.dreamsoftware.blockchaingateway.web.validation.constraints.ValidUserType;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@FieldMatch(first = "passwordClear", second = "confirmPassword",
        message = "{user_password_not_match}")
public class SignUpUserDTO {

    /**
     * Name
     */
    @NotBlank(message = "{user_name_not_null}")
    @Size(min = 2, max = 45, message = "{user_name_size}")
    @JsonProperty("name")
    private String name;

    /**
     * Email
     */
    @NotBlank(message = "{user_email_not_null}")
    @Email(message = "{user_email_invalid}")
    @EmailShouldBeUnique(message = "{user_email_unique}")
    @JsonProperty("email")
    private String email;

    /**
     * Password Clear
     */
    @NotBlank(message = "{user_password_not_null}")
    @Size(min = 8, max = 25, message = "{user_password_size}")
    @JsonProperty("password_clear")
    protected String passwordClear;

    /**
     * Confirm Password
     */
    @NotBlank(message = "{user_confirm_password_not_null}")
    @JsonProperty("confirm_password")
    protected String confirmPassword;

    /**
     * Type
     */
    @JsonProperty("type")
    @NotBlank(message = "{user_type_not_null}")
    @ValidUserType(message = "{user_type_invalid}", groups = {IExtended.class})
    private String type;

    /**
     * User Agent
     */
    private String userAgent;

    /**
     * User Language
     */
    @JsonProperty("language")
    @ShouldBeValidISOLanguage(message = "{user_language_invalid}")
    private String language;

}
