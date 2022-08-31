package com.dreamsoftware.tcs.web.uploads.models;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Upload File Info
 *
 * @author Sergio Sánchez Sánchez
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileInfo<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identity
     */
    private T identity;

    /**
     * Size
     */
    private Long size;

    /**
     * Content Type
     */
    private String contentType;

    /**
     * Content
     */
    private byte[] content;

}
