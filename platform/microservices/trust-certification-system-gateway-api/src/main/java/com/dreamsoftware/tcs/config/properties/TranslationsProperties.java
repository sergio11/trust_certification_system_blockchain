package com.dreamsoftware.tcs.config.properties;

import org.springframework.context.annotation.Configuration;
import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 *
 * @author ssanchez
 */
@Slf4j
@Configuration
@Data
@RefreshScope
public class TranslationsProperties {

    /**
     * Default File Location
     */
    @Value("${translations.default-file-location}")
    private String defaultFileLocation;

    @PostConstruct
    public void onPostConstruct() {
        log.debug("translations defaultFileLocation : " + defaultFileLocation);
    }
}
