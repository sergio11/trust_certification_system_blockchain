package com.dreamsoftware.tcs.web.dto.response;

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
public class DeviceDTO {

    /**
     * Device Id
     */
    @JsonProperty("device_id")
    private String deviceId;

    /**
     * Registration TOken
     */
    @JsonProperty("registration_token")
    private String registrationToken;

    /**
     * Type
     */
    @JsonProperty("type")
    private String type;

    /**
     * Create At
     */
    @JsonProperty("created_at")
    private String createdAt;

    /**
     * Notification Key Name
     */
    @JsonProperty("notification_key_name")
    private String notificationKeyName;

    /**
     * Notification Key
     */
    @JsonProperty("notification_key")
    private String notificationKey;

    /**
     * Owner
     */
    @JsonProperty("owner")
    private String owner;
}
