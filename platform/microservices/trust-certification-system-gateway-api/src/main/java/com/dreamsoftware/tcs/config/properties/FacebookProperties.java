package com.dreamsoftware.tcs.config.properties;

import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author ssanchez
 */
@Configuration
@Data
@RefreshScope
@Slf4j
public class FacebookProperties {

    /**
     * Facebook App Key
     */
    @Value("${facebook.app.key}")
    private String appKey;

    /**
     * Facebook App Secret
     */
    @Value("${facebook.app.secret}")
    private String appSecret;

    /**
     * Facebook Picture Width
     */
    @Value("${facebook.picture.width}")
    private Integer pictureWidth;

    /**
     * Facebook Picture Height
     */
    @Value("${facebook.picture.height}")
    private Integer pictureHeight;

    @PostConstruct
    public void onPostConstruct() {
        log.debug("FacebookProperties appKey: " + appKey);
        log.debug("FacebookProperties appSecret: " + appSecret);
        log.debug("FacebookProperties pictureWidth: " + pictureWidth);
        log.debug("FacebookProperties pictureHeight: " + pictureHeight);
    }

}
