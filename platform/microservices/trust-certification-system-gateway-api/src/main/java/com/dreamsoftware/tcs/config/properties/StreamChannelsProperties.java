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
     * User Management Channel
     */
    @Value("${channels.user-management}")
    private String userManagement;

    /**
     * Course Management Channel
     */
    @Value("${channels.course-management}")
    private String courseManagement;

    /**
     * Certification management Channel
     */
    @Value("${channels.certification-management}")
    private String certificationManagement;

    /**
     * New Tokens Order Approved Channel
     */
    @Value("${channels.new-tokens-order-approved}")
    private String newTokensOrderApproved;

    /**
     * Notification Delivery Request Channel
     */
    @Value("${channels.notification-delivery-request}")
    private String notificationDeliveryRequest;

    /**
     * Device Management
     */
    @Value("${channels.device-management}")
    private String deviceManagement;

    @PostConstruct
    public void onPostConstruct() {
        log.debug("userManagement -> " + userManagement);
        log.debug("courseManagement -> " + courseManagement);
        log.debug("certificationManagement -> " + certificationManagement);
        log.debug("newTokensOrderApproved -> " + newTokensOrderApproved);
        log.debug("notificationDeliveryRequest -> " + notificationDeliveryRequest);
        log.debug("deviceManagement -> " + deviceManagement);
    }
}
