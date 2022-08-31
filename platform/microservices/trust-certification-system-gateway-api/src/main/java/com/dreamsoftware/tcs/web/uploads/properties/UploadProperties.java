package com.dreamsoftware.tcs.web.uploads.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Upload Properties
 *
 * @author ssanchez
 */
@Component
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class UploadProperties implements Serializable {

    private static final long serialVersionUID = 1L;

    @Value("${uploads.directory}")
    private String uploadsDirectory;
    /**
     * User Images Folder Name
     */
    @Value("${uploads.user.images.folder.name}")
    private String userImagesFolderName;

    /**
     * User Images Avatar Desired Width
     */
    @Value("${uploads.user.images.avatar.desired.width}")
    private Integer userImagesAvatarDesiredWidth;

    /**
     * User Images Avatar Desired Height
     */
    @Value("${uploads.user.images.avatar.desired.height}")
    private Integer userImagesAvatarDesiredHeight;
}
