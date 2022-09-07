package com.dreamsoftware.tcs.web.dto.response;

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
public class SimpleAuthenticationProviderDTO {

    /**
     * User Identity
     */
    @JsonProperty("identity")
    private String identity;

    /**
     * Key
     */
    @JsonProperty("key")
    private String key;

    /**
     *
     */
    @JsonProperty("type")
    private String type;
}
