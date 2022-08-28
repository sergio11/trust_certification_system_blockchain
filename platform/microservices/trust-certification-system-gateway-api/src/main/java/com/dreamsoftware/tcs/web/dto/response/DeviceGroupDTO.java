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
public class DeviceGroupDTO {

    /**
     * Identity
     */
    @JsonProperty("identity")
    private String identity;

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
     * Create At
     */
    @JsonProperty("created_at")
    private String createdAt;

    /**
     * Owner
     */
    @JsonProperty("owner")
    private String owner;
}
