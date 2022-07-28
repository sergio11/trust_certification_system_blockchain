package com.dreamsoftware.tcs.web.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TranslationDTO {

    /**
     * Identity
     */
    @JsonProperty("identity")
    private String identity;

    /**
     * Name
     */
    @JsonProperty("name")
    private String name;

    /**
     * Value
     */
    @JsonProperty("value")
    private String value;

    /**
     * Language
     */
    @JsonProperty("language")
    private String language;
}
