package com.dreamsoftware.tcs.web.uploads.strategy.impl.common;

import com.dreamsoftware.tcs.persistence.nosql.entity.ImageDataEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.ImageDataRepository;
import com.dreamsoftware.tcs.web.uploads.models.RequestUploadFile;
import com.dreamsoftware.tcs.web.uploads.models.UploadFileInfo;
import com.dreamsoftware.tcs.web.uploads.strategy.impl.support.SupportImagesFileSystemUploadStrategy;
import io.jsonwebtoken.lang.Assert;
import java.io.File;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;

/**
 * Commons Images File System Upload Strategy
 *
 * @author ssanchez
 */
@RequiredArgsConstructor
public class CommonImagesFileSystemUploadStrategy extends SupportImagesFileSystemUploadStrategy<RequestUploadFile, ImageDataEntity, String>
        implements ICommonImageUploadStrategy {

    /**
     *
     */
    private final ImageDataRepository imageDataRepository;

    /**
     *
     * @param fileinfo
     * @param fileSaved
     * @return
     */
    @Override
    protected ImageDataEntity onFileSaved(final RequestUploadFile fileinfo, final File fileSaved) {
        Assert.notNull(fileinfo, "fileinfo can not be null");
        Assert.notNull(fileSaved, "fileSaved can not be null");

        final String fileName = FilenameUtils.getBaseName(fileSaved.getName());
        final String contentType = fileinfo.getContentType();
        final String ext = FilenameUtils.getExtension(fileSaved.getName());

        final ImageDataEntity imageToSave = ImageDataEntity.builder()
                .name(fileName)
                .contentType(contentType)
                .size(fileSaved.length())
                .ext(ext)
                .build();

        return imageDataRepository.save(imageToSave);
    }

    /**
     *
     * @param output
     */
    @Override
    protected void onFileDeteled(final ImageDataEntity output) {
        Assert.notNull(output, "output can not be null");
        imageDataRepository.delete(output);
    }

    /**
     *
     * @param output
     * @return
     */
    @Override
    protected String getFileNameInFs(final ImageDataEntity output) {
        Assert.notNull(output, "output can not be null");
        return String.format("%s.%s", output.getName(), output.getExt());
    }

    /**
     *
     * @param id
     */
    @Override
    public void deleteById(final String id) {
        Assert.notNull(id, "Id can not be null");
        imageDataRepository.findByName(id)
                .ifPresent(calendarEventImage -> delete(calendarEventImage));
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public UploadFileInfo getById(final String id) {
        Assert.notNull(id, "Image can not be null");
        return imageDataRepository.findByName(id)
                .map(imageData -> get(imageData)).orElse(null);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public Boolean existsById(final String id) {
        Assert.notNull(id, "Id can not be null");
        return imageDataRepository.findByName(id)
                .map(imageData -> exists(imageData)).orElse(null);
    }

}
