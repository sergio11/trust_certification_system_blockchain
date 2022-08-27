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
public class StreamChannelsProperties {

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

    /**
     * New Tokens Order Approved
     */
    @Value("${channels.new-tokens-order-approved}")
    private String newTokensOrderApproved;

    /**
     * Notification Delivery Request
     */
    @Value("${channels.notification-delivery-request}")
    private String notificationDeliveryRequest;

    @PostConstruct
    public void onPostConstruct() {
        log.debug("newUserRegistration -> " + newUserRegistration);
        log.debug("certificationCourseRegistration -> " + certificationCourseRegistration);
        log.debug("newCertificationRequest -> " + newCertificationRequest);
        log.debug("newTokensOrderApproved -> " + newTokensOrderApproved);
        log.debug("notificationDeliveryRequest -> " + notificationDeliveryRequest);
    }
}
