package com.dreamsoftware.blockchaingateway.web.dto.request;

import com.dreamsoftware.blockchaingateway.web.validation.constraints.IExtended;
import com.dreamsoftware.blockchaingateway.web.validation.constraints.IGlobal;
import com.dreamsoftware.blockchaingateway.web.validation.constraints.ValidTranslation;
import com.dreamsoftware.blockchaingateway.web.validation.constraints.ValidTranslationLanguage;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
@ValidTranslation(message = "{translation_duplicate}", groups = {IGlobal.class})
public class SaveTranslationDTO {

    /**
     * Name
     */
    @Schema(description = "Translation Key Name.", required = true)
    @JsonProperty("name")
    @NotBlank(message = "{translation_name_not_null}")
    private String name;

    /**
     * Value
     */
    @Schema(description = "Translation Value.", required = true)
    @JsonProperty("value")
    @NotBlank(message = "{translation_value_not_null}")
    private String value;

    /**
     * Language
     */
    @Schema(description = "Translation Language (es_ES, en_GB, de_DE, fr_FR, pt_PT, it_IT)", required = true)
    @JsonProperty("language")
    @NotBlank(message = "{translation_language_not_null}")
    @ValidTranslationLanguage(message = "{translation_language_invalid}", groups = {IExtended.class})
    private String language;
}
