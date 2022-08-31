package com.dreamsoftware.tcs.web.uploads.models;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request Upload File
 *
 * @author Sergio Sánchez Sánchez
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestUploadFile implements Serializable {

    public final static String ORIGINAL_NAME = "originalName";
    public final static String CONTENT_TYPE = "contentType";

    private static final long serialVersionUID = 1L;

    /**
     * Bytes
     */
    protected byte[] bytes;

    /**
     * Content Type
     */
    protected String contentType;

    /**
     * Original Name
     */
    protected String originalName;

    /**
     * Desired With
     */
    protected int desiredWidth;

    /**
     * Desired Height
     */
    protected int desiredHeight;

}
