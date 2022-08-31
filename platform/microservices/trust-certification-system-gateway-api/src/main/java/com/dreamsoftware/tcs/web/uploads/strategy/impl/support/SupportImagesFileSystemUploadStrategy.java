package com.dreamsoftware.tcs.web.uploads.strategy.impl.support;

import com.dreamsoftware.tcs.web.uploads.exception.FileNotFoundException;
import com.dreamsoftware.tcs.web.uploads.exception.UploadFailException;
import com.dreamsoftware.tcs.web.uploads.models.RequestUploadFile;
import com.dreamsoftware.tcs.web.uploads.models.UploadFileInfo;
import com.dreamsoftware.tcs.web.uploads.strategy.IUploadStrategy;
import com.dreamsoftware.tcs.web.uploads.utils.ImageUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * @param <INPUT>
 * @param <OUTPUT>
 * @param <ID>
 * @author ssanchez
 */
@Slf4j
public abstract class SupportImagesFileSystemUploadStrategy<INPUT extends RequestUploadFile, OUTPUT, ID> implements IUploadStrategy<INPUT, OUTPUT, ID> {

    /**
     * Real Path to uploads
     */
    private String realPathtoUploads;

    /**
     *
     * @param fileinfo
     * @return
     */
    @Override
    public OUTPUT save(INPUT fileinfo) {
        try {
            // get new file to save bytes
            File fileToSave = getFileToSave(fileinfo.getContentType());

            Files.write(
                    fileToSave.toPath(),
                    fileinfo.getBytes(),
                    StandardOpenOption.CREATE);

            ImageUtils.resizeImageFile(fileToSave,
                    fileinfo.getDesiredWidth(), fileinfo.getDesiredHeight(),
                    true);

            log.debug("File to save PATH -> " + fileToSave.toPath());

            return onFileSaved(fileinfo, fileToSave);

        } catch (IOException ex) {
            throw new UploadFailException();
        }
    }

    /**
     *
     * @param model
     * @return
     */
    @Override
    public Boolean exists(final OUTPUT model) {
        Assert.notNull(model, "model can not be null");
        File file = new File(realPathtoUploads, getFileNameInFs(model));
        return file.exists() && file.canRead();
    }

    /**
     *
     * @param model
     */
    @Override
    public void delete(final OUTPUT model) {
        Assert.notNull(model, "model can not be null");
        File file = new File(realPathtoUploads, getFileNameInFs(model));
        if (file.exists()) {
            file.delete();
        }
        onFileDeteled(model);
    }

    /**
     *
     * @param model
     * @return
     */
    @Override
    public UploadFileInfo get(final OUTPUT model) {
        Assert.notNull(model, "model can not be null");

        UploadFileInfo info = null;
        try {

            File file = new File(realPathtoUploads, getFileNameInFs(model));
            if (file.exists() && file.canRead()) {
                String contentType = Files.probeContentType(file.toPath());
                byte[] content = Files.readAllBytes(file.toPath());
                info = new UploadFileInfo<>(file.getName(), file.length(), contentType, content);
            } else {
                log.debug("File not exists or can't be read");
                if (!file.canRead()) {
                    file.delete();
                }
                onFileDeteled(model);
                throw new FileNotFoundException();
            }
        } catch (IOException ex) {
            log.error(ex.getMessage());
            throw new FileNotFoundException();
        }
        return info;
    }

    /**
     *
     * @param uploadsDir
     * @param dirname
     */
    @Override
    public void configureRealPathForUploads(final String uploadsDir, final String dirname) {
        Assert.notNull(dirname, "dirname can not be null");

        final File uploadsDirectory = new File(uploadsDir, dirname);
        if (!uploadsDirectory.exists()) {
            uploadsDirectory.mkdirs();
        }
        log.debug("Uploads Directory -> " + uploadsDirectory.getAbsolutePath());
        this.realPathtoUploads = uploadsDirectory.getAbsolutePath();
    }

    /**
     *
     * @param fileinfo
     * @param fileSaved
     * @return
     */
    protected abstract OUTPUT onFileSaved(final INPUT fileinfo, final File fileSaved);

    /**
     *
     * @param output
     */
    protected abstract void onFileDeteled(final OUTPUT output);

    /**
     *
     * @param output
     * @return
     */
    protected abstract String getFileNameInFs(final OUTPUT output);

    /**
     * Get File To Save
     *
     * @param contentType
     * @return
     */
    private File getFileToSave(String contentType) {
        String name = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(50),
                contentType.replace("image/", ""));
        return new File(realPathtoUploads, name);
    }

}
