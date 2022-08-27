package com.dreamsoftware.tcs.fcm.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
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
public class DevicesGroupOperationDTO {

    /**
     * Operation Type
     */
    @JsonProperty("operation")
    private DevicesGroupOperationType operation;

    /**
     * Notification Key Name
     */
    @JsonProperty("notification_key_name")
    private String groupName;

    /**
     * Notification Key
     */
    @JsonProperty("notification_key")
    private String groupKey;

    /**
     * Registration Ids
     */
    @JsonProperty("registration_ids")
    private List<String> deviceTokens;

}
