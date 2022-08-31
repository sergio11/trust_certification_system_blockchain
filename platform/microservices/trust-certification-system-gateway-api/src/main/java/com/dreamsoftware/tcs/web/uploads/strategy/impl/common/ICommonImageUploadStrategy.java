package com.dreamsoftware.tcs.web.uploads.strategy.impl.common;

import com.dreamsoftware.tcs.persistence.nosql.entity.ImageDataEntity;
import com.dreamsoftware.tcs.web.uploads.models.RequestUploadFile;
import com.dreamsoftware.tcs.web.uploads.strategy.IUploadStrategy;

/**
 *
 * @author ssanchez
 */
public interface ICommonImageUploadStrategy extends IUploadStrategy<RequestUploadFile, ImageDataEntity, String> {

}
