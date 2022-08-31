package com.dreamsoftware.tcs.services.core;

import com.dreamsoftware.tcs.mapper.UserPhotoMapper;
import com.dreamsoftware.tcs.services.IUploadImagesService;
import com.dreamsoftware.tcs.web.dto.response.UserPhotoDTO;
import com.dreamsoftware.tcs.web.uploads.models.RequestUploadFile;
import com.dreamsoftware.tcs.web.uploads.models.UploadFileInfo;
import com.dreamsoftware.tcs.web.uploads.strategy.impl.common.ICommonImageUploadStrategy;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;

/**
 *
 * @author ssanchez
 * @param <USER_ID>
 */
@Slf4j
public abstract class SupportUploadImagesService<USER_ID> implements IUploadImagesService<USER_ID> {

    @Autowired
    @Qualifier("userImagesUploadStrategy")
    protected ICommonImageUploadStrategy imageUploadStrategy;

    @Autowired
    protected UserPhotoMapper userPhotoMapper;

    /**
     *
     * @param userId
     * @param imageUrl
     * @return
     */
    @Override
    public UserPhotoDTO uploadFromUrl(final USER_ID userId, final String imageUrl) {
        Assert.notNull(userId, "User Id can not be null");
        Assert.notNull(imageUrl, "Image URL can not be null");
        Assert.hasLength(imageUrl, "Image URL can not be empty");

        try {
            URL url = new URL(imageUrl);
            byte[] content = IOUtils.toByteArray(url.openStream());

            final RequestUploadFile requestUpload = RequestUploadFile.builder()
                    .contentType(MediaType.IMAGE_PNG_VALUE)
                    .originalName("image" + userId.toString())
                    .bytes(content)
                    .build();
            return upload(userId, requestUpload);
        } catch (MalformedURLException ex) {
            log.debug(imageUrl);
            log.error("MalformedURLException for user " + userId.toString());
        } catch (IOException ex) {
            log.error("IOException");
        }
        return null;
    }

    /**
     * Get Avatar File Info by name
     *
     * @param name
     * @return
     */
    @Override
    public UploadFileInfo<Long> getInfoByName(String name) {
        Assert.notNull(name, "Name can not be null");
        log.debug("Get Upload File Info for name -> " + name);
        return imageUploadStrategy.getById(name);
    }

    /**
     * Delete By Name
     *
     * @param name
     */
    @Override
    public void deleteByName(String name) {
        Assert.notNull(name, "Name can not be null");
        imageUploadStrategy.deleteById(name);
    }
}
