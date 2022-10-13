package com.dreamsoftware.tcs.web.dto.request;

import com.dreamsoftware.tcs.web.validation.constraints.UserShouldBeActive;
import com.dreamsoftware.tcs.web.validation.constraints.UserWithEmailShouldExists;
import com.dreamsoftware.tcs.web.validation.constraints.group.IGroups.IAccountShouldActive;
import com.dreamsoftware.tcs.web.validation.constraints.group.IGroups.IEmailShouldExist;
import com.dreamsoftware.tcs.web.validation.constraints.group.IGroups.IValidEmail;
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
public class ResetPasswordRequestDTO {

    @Schema(description = "The email of the user who wants to reset their password", required = true)
    @NotBlank(message = "{user_email_not_null}")
    @Email(message = "{user_email_invalid}", groups = IValidEmail.class)
    @UserWithEmailShouldExists(message = "{user_email_not_exists}", groups = IEmailShouldExist.class)
    @UserShouldBeActive(message = "{user_email_not_activate}", groups = IAccountShouldActive.class)
    @JsonProperty("email")
    private String email;

}
