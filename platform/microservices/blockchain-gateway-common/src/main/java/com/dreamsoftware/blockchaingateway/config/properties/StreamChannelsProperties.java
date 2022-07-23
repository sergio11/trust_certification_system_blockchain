package com.dreamsoftware.blockchaingateway.config.properties;

import javax.annotation.PostConstruct;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class StreamChannelsProperties {

    private Logger logger = LoggerFactory.getLogger(StreamChannelsProperties.class);

    /**
     * Channels Initial Funds
     */
    @Value("${channels.new-user-registration}")
    private String newUserRegistration;

    /**
     * Certification Course Registration
     */
    @Value("${channels.certification-course-registration}")
    private String certificationCourseRegistration;

    /**
     * New Certification Request
     */
    @Value("${channels.new-certification-request}")
    private String newCertificationRequest;

    @PostConstruct
    public void onPostConstruct() {
        logger.debug("newUserRegistration -> " + newUserRegistration);
        logger.debug("certificationCourseRegistration -> " + certificationCourseRegistration);
    }
}
