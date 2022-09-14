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
     * Admin UID
     */
    @Schema(description = "UID", example = "admin-tcs", required = true)
    @NotEmpty(message = "{admin_uid_not_empty}")
    @JsonProperty("uid")
    private String uid;

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
