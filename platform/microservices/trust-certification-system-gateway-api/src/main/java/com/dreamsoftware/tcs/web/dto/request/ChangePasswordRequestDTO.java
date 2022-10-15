package com.dreamsoftware.tcs.web.dto.request;

import com.dreamsoftware.tcs.web.validation.constraints.FieldMatch;
import com.dreamsoftware.tcs.web.validation.constraints.UserShouldBeActive;
import com.dreamsoftware.tcs.web.validation.constraints.UserWithEmailShouldExists;
import com.dreamsoftware.tcs.web.validation.constraints.group.IGroups.IAccountShouldActive;
import com.dreamsoftware.tcs.web.validation.constraints.group.IGroups.IEmailShouldExist;
import com.dreamsoftware.tcs.web.validation.constraints.group.IGroups.IValidEmail;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
public class ChangePasswordRequestDTO {

    /**
     * Password Clear
     */
    @JsonProperty("passwordClear")
    @NotBlank(message = "{user_password_not_null}")
    @Size(min = 8, max = 25, message = "{user_password_size}")
    private String passwordClear;

    /**
     * Confirm Password
     */
    @JsonProperty("confirmPassword")
    @NotBlank(message = "{user_confirm_password_not_null}")
    private String confirmPassword;

    /**
     * Confirmation Token
     */
    @JsonProperty("confirmationToken")
    @NotBlank(message = "{confirmation_token_not_null}")
    private String confirmationToken;

}
