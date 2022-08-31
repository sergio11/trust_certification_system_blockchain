package com.dreamsoftware.tcs.web.uploads.strategy;

import com.dreamsoftware.tcs.web.uploads.models.RequestUploadFile;
import com.dreamsoftware.tcs.web.uploads.models.UploadFileInfo;

/**
 * Upload Strategy
 *
 * @param <INPUT>
 * @param <OUTPUT>
 * @param <ID>
 */
public interface IUploadStrategy<INPUT extends RequestUploadFile, OUTPUT, ID> {

    /**
     * Save File input
     *
     * @param fileinfo
     * @return
     */
    OUTPUT save(INPUT fileinfo);

    /**
     * Delete
     *
     * @param model
     */
    void delete(OUTPUT model);

    /**
     * Delete by id
     *
     * @param id
     */
    void deleteById(ID id);

    /**
     * Get Uploaded File info by id
     *
     * @param id
     * @return
     */
    UploadFileInfo getById(ID id);

    /**
     * Get Uploaded File info
     *
     * @param model
     * @return
     */
    UploadFileInfo get(OUTPUT model);

    /**
     * Check if exists file by id
     *
     * @param id
     * @return
     */
    Boolean existsById(ID id);

    /**
     * Check if exists
     *
     * @param model
     * @return
     */
    Boolean exists(OUTPUT model);

    /**
     *
     * @param uploadsDir
     * @param dirname
     */
    void configureRealPathForUploads(final String uploadsDir, final String dirname);
}
