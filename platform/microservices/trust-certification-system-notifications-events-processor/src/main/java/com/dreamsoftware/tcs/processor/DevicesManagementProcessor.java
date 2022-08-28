package com.dreamsoftware.tcs.processor;

import com.dreamsoftware.tcs.service.IDevicesManagementService;
import com.dreamsoftware.tcs.stream.events.devices.DeviceManagementEvent;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Devices Management Processor
 *
 * @author ssanchez
 */
@Slf4j
@Component("devicesManagementProcessor")
@RequiredArgsConstructor
public class DevicesManagementProcessor implements Consumer<DeviceManagementEvent> {

    private final IDevicesManagementService devicesManagementService;

    /**
     *
     * @param event
     */
    @Override
    public void accept(final DeviceManagementEvent event) {
        log.debug("DevicesManagementProcessor CALLED!");
        switch (event.getType()) {
            case ADD_DEVICE:
                devicesManagementService.addDeviceToGroup(event.getOwnerObjectId(), event.getDeviceId(), event.getRegistrationToken());
                break;
            case CREATE_DEVICE_GROUP:
                devicesManagementService.createDeviceGroupWithDevice(event.getOwnerObjectId(), event.getDeviceId(), event.getRegistrationToken());
                break;
            case UPDATE_DEVICE:
                devicesManagementService.updateDevice(event.getOwnerObjectId(), event.getDeviceId(), event.getRegistrationToken());
                break;
            case CREATE_OR_UPDATE_DEVICE:
                devicesManagementService.createOrUpdateDevice(event.getOwnerObjectId(), event.getDeviceId(), event.getRegistrationToken());
                break;
            case REMOVE_DEVICE:
                devicesManagementService.removeDeviceFromGroup(event.getDeviceId());
                break;
        }
    }

}
