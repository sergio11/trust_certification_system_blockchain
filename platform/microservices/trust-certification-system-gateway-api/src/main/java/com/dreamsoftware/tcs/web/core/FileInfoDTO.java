package com.dreamsoftware.tcs.web.core;

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
public class FileInfoDTO {

    /**
     * Size
     */
    private Long size;

    /**
     * Name
     */
    private String name;

    /**
     * Content Type
     */
    private String contentType;

    /**
     * Content
     */
    private byte[] content;

}
