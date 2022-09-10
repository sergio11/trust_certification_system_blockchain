package com.dreamsoftware.tcs.fcm.properties;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 *
 * @author ssanchez
 */
@Slf4j
@Data
@Component
@RefreshScope
public class FCMCustomProperties implements Serializable {

    /**
     * App Server Key
     */
    @Value("${fcm.app.server.key}")
    private String appServerKey;

    /**
     * Group Prefix
     */
    @Value("${fcm.app.group.prefix}")
    private String groupPrefix;

    /**
     * Notification Groups Url
     */
    @Value("${fcm.notification.groups.url}")
    private String notificationGroupsUrl;

    /**
     * Notification Send Url
     */
    @Value("${fcm.notification.send.url}")
    private String notificationSendUrl;

    /**
     * Sender Id
     */
    @Value("${fcm.sender.id}")
    private String senderId;

    /**
     *
     */
    @PostConstruct
    protected void onPostConstruct() {
        log.debug("appServerKey: " + appServerKey);
        log.debug("notificationGroupsUrl: " + notificationGroupsUrl);
        log.debug("notificationSendUrl: " + notificationSendUrl);
        log.debug("senderId: " + senderId);
        log.debug("groupPrefix: " + groupPrefix);
    }

}
