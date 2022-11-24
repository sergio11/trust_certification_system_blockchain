package com.dreamsoftware.tcs.web.dto.request;

import com.dreamsoftware.tcs.web.validation.constraints.EmailShouldBeUnique;
import com.dreamsoftware.tcs.web.validation.constraints.ShouldBeValidISOLanguage;
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
public class AddCaMemberDTO {

    /**
     * Name
     */
    @Schema(description = "CA member full name", required = true)
    @JsonProperty("fullName")
    @NotBlank(message = "{user_name_not_null}")
    @Size(min = 2, max = 45, message = "{user_name_size}")
    private String fullName;

    /**
     * Email
     */
    @Schema(description = "CA member email address", required = true)
    @JsonProperty("email")
    @NotBlank(message = "{user_email_not_null}")
    @Email(message = "{user_email_invalid}")
    @EmailShouldBeUnique(message = "{user_email_unique}")
    private String email;

    /**
     * User Language
     */
    @Schema(description = "CA member preferred language", required = true)
    @JsonProperty("language")
    @ShouldBeValidISOLanguage(message = "{user_language_invalid}")
    private String language;
}
