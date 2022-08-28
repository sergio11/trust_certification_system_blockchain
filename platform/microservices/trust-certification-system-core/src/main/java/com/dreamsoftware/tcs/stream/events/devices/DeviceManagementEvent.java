package com.dreamsoftware.tcs.stream.events.devices;

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
public class DeviceManagementEvent {

    /**
     * Owner Id
     */
    private final String ownerObjectId;

    /**
     * Device Id
     */
    private final String deviceId;

    /**
     * Registration Token
     */
    private final String registrationToken;

    /**
     * Event Type
     */
    private final DeviceManagementEventTypeEnum type;
}
