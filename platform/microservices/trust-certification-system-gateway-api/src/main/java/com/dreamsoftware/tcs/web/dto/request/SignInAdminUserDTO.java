package com.dreamsoftware.tcs.web.dto.request;

import com.dreamsoftware.tcs.web.validation.constraints.IExtended;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
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
public class SignInAdminUserDTO {

    /**
     * User Email
     */
    @Schema(description = "User Email.", example = "user_example@gmail.com", required = true)
    @NotEmpty(message = "{user_email_not_empty}")
    @Pattern(regexp = "\\S+@\\S+\\.\\S+", message = "{user_email_invalid}", groups = {IExtended.class})
    @JsonProperty("email")
    private String email;

    /**
     * User Password
     */
    @Schema(description = "User Password.",
            example = "PASSWORD2020", required = true)
    @NotBlank(message = "{user_password_not_null}")
    @Size(min = 8, max = 25, message = "{user_password_size}", groups = {IExtended.class})
    @JsonProperty("password")
    private String password;

}
