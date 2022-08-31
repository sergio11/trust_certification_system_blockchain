package com.dreamsoftware.tcs.web.uploads.strategy.config;

import com.dreamsoftware.tcs.persistence.nosql.repository.ImageDataRepository;
import com.dreamsoftware.tcs.web.uploads.properties.UploadProperties;
import com.dreamsoftware.tcs.web.uploads.strategy.impl.common.CommonImagesFileSystemUploadStrategy;
import com.dreamsoftware.tcs.web.uploads.strategy.impl.common.ICommonImageUploadStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

/**
 *
 * @author ssanchez
 */
@Configuration
public class UploadStrategyConfig {

    /**
     * Provide Calendar Events Upload Strategy
     *
     * @param imageDataEntityRepository
     * @param uploadProperties
     * @return
     */
    @Bean("userImagesUploadStrategy")
    @Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ICommonImageUploadStrategy provideUserImagesUploadStrategy(
            final ImageDataRepository imageDataEntityRepository,
            final UploadProperties uploadProperties) {
        return buildCommonImageUploadStrategy(
                imageDataEntityRepository, uploadProperties.getUploadsDirectory(),
                uploadProperties.getUserImagesFolderName());
    }

    /**
     *
     * @param imageDataEntityRepository
     * @param uploadsDirname
     * @param dirname
     * @return
     */
    private ICommonImageUploadStrategy buildCommonImageUploadStrategy(
            final ImageDataRepository imageDataEntityRepository,
            final String uploadsDirname,
            final String dirname) {

        ICommonImageUploadStrategy strategy = new CommonImagesFileSystemUploadStrategy(imageDataEntityRepository);
        strategy.configureRealPathForUploads(uploadsDirname, dirname);
        return strategy;
    }
}
