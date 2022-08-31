package com.dreamsoftware.tcs.services;

import com.dreamsoftware.tcs.web.dto.response.UserPhotoDTO;
import com.dreamsoftware.tcs.web.uploads.models.RequestUploadFile;
import com.dreamsoftware.tcs.web.uploads.models.UploadFileInfo;

/**
 *
 * @author ssanchez
 * @param <USER_ID>
 */
public interface IUploadImagesService<USER_ID> {

    /**
     * Upload
     *
     * @param userId
     * @param requestUploadFile
     * @return
     */
    UserPhotoDTO upload(final USER_ID userId, final RequestUploadFile requestUploadFile);

    /**
     * Upload From URL
     *
     * @param userId
     * @param imageUrl
     * @return
     */
    UserPhotoDTO uploadFromUrl(final USER_ID userId, final String imageUrl);

    /**
     *
     * @param id
     * @return
     */
    UploadFileInfo<Long> getInfoForUser(final USER_ID id);

    /**
     *
     * @param name
     * @return
     */
    UploadFileInfo<Long> getInfoByName(final String name);

    /**
     * Delete For User
     *
     * @param id
     */
    void deleteForUser(final USER_ID id);

    /**
     * Delete By Name
     *
     * @param name
     */
    void deleteByName(final String name);
}
