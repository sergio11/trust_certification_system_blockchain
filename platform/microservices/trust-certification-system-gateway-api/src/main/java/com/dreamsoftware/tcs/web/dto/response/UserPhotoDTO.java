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
public class UserPhotoDTO {

    /**
     * Name
     */
    @JsonProperty("name")
    private String name;

    /**
     * Size
     */
    @JsonProperty("size")
    private Long size;

    /**
     * Content Type
     */
    @JsonProperty("content_type")
    private String contentType;
}
