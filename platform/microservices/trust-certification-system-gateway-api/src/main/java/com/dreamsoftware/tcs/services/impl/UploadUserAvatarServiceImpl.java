package com.dreamsoftware.tcs.services.impl;

import com.dreamsoftware.tcs.persistence.nosql.entity.ImageDataEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.services.core.SupportUploadImagesService;
import com.dreamsoftware.tcs.web.dto.response.UserPhotoDTO;
import com.dreamsoftware.tcs.web.uploads.models.RequestUploadFile;
import com.dreamsoftware.tcs.web.uploads.models.UploadFileInfo;
import com.dreamsoftware.tcs.web.uploads.properties.UploadProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 *
 * @author ssanchez
 */
@Slf4j
@Service("UploadUserAvatarService")
@RequiredArgsConstructor
public class UploadUserAvatarServiceImpl extends SupportUploadImagesService<ObjectId> {

    private final UserRepository userRepository;
    private final UploadProperties uploadProperties;

    /**
     * Upload Image
     *
     * @param userId
     * @param requestUploadFile
     * @return
     */
    @Override
    public UserPhotoDTO upload(final ObjectId userId, final RequestUploadFile requestUploadFile) {
        Assert.notNull(userId, "User Id can not be null");
        Assert.notNull(requestUploadFile, "Request Upload File can not be null");

        //Configure desire size
        requestUploadFile.setDesiredWidth(uploadProperties.getUserImagesAvatarDesiredWidth());
        requestUploadFile.setDesiredHeight(uploadProperties.getUserImagesAvatarDesiredHeight());

        // Get User Entity
        return userRepository.findById(userId).map(userEntity -> {
            final ImageDataEntity userPhoto = imageUploadStrategy.save(requestUploadFile);
            // Remove Previous file
            if (userEntity.getAvatar() != null) {
                imageUploadStrategy.delete(userEntity.getAvatar());
            }
            // Set current image
            userEntity.setAvatar(userPhoto);
            // Save User
            userRepository.save(userEntity);
            return userPhotoMapper.entityToDTO(userPhoto);
        }).orElse(null);

    }

    /**
     * Get Avatar File Info for user
     *
     * @param id
     * @return
     */
    @Override
    public UploadFileInfo<Long> getInfoForUser(final ObjectId id) {
        Assert.notNull(id, "User id can not be null");
        return userRepository.findById(id).map(user
                -> getInfoByName(user.getAvatar().getName())).orElse(null);
    }

    /**
     *
     * @param id
     */
    @Override
    public void deleteForUser(final ObjectId id) {
        Assert.notNull(id, "User id can not be null");
        userRepository.findById(id).ifPresent(
                user -> {
                    imageUploadStrategy.delete(user.getAvatar());
                    user.setAvatar(null);
                    userRepository.save(user);
                });

    }
}
