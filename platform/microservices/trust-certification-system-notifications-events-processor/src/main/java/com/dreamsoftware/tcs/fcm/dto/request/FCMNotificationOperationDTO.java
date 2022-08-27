package com.dreamsoftware.tcs.fcm.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ssanchez
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FCMNotificationOperationDTO {

    /**
     * Notification Key
     */
    @JsonProperty("to")
    private String notificationKey;

    /**
     * Notification
     */
    @JsonProperty("notification")
    private FCMNotification notification;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FCMNotification {

        /**
         * Title
         */
        @JsonProperty("title")
        private String title;

        /**
         * Body
         */
        @JsonProperty("body")
        private String body;

    }

}
