package com.dreamsoftware.tcs.services.impl;

import com.dreamsoftware.tcs.config.properties.StreamChannelsProperties;
import com.dreamsoftware.tcs.mapper.DeviceEntityMapper;
import com.dreamsoftware.tcs.persistence.nosql.entity.DeviceEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.DeviceRepository;
import com.dreamsoftware.tcs.services.IDeviceGroupsService;
import com.dreamsoftware.tcs.stream.events.devices.DeviceManagementEvent;
import com.dreamsoftware.tcs.stream.events.devices.DeviceManagementEventTypeEnum;
import com.dreamsoftware.tcs.web.dto.response.DeviceDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Device Group Service
 *
 * @author ssanchez
 */
@Slf4j
@Service("deviceGroupsService")
@RequiredArgsConstructor
public class DeviceGroupsServiceImpl implements IDeviceGroupsService {

    private final StreamChannelsProperties streamChannelsProperties;
    private final StreamBridge streamBridge;
    private final DeviceRepository deviceRepository;
    private final DeviceEntityMapper deviceEntityMapper;

    /**
     *
     * @param ownerId
     * @return
     * @throws Throwable
     */
    @Override
    public Iterable<DeviceDTO> getDevicesByOwner(final String ownerId) throws Throwable {
        Assert.notNull(ownerId, "Owner Id can not be null");
        Assert.isTrue(ObjectId.isValid(ownerId), "Owner Id is not valid");
        final Iterable<DeviceEntity> devices = deviceRepository.findByDeviceGroupOwnerId(new ObjectId(ownerId));
        return deviceEntityMapper.entityToDTO(devices);
    }

    /**
     *
     * @param ownerId
     * @param deviceId
     * @param registrationToken
     */
    @Override
    public void addDeviceToGroup(final String ownerId, final String deviceId, final String registrationToken) {
        Assert.notNull(ownerId, "Owner Id can not be null");
        Assert.notNull(deviceId, "Device Id can not be null");
        Assert.notNull(registrationToken, "Registration Token can not be null");
        Assert.isTrue(ObjectId.isValid(ownerId), "Owner Id is not valid");
        streamBridge.send(streamChannelsProperties.getDeviceManagement(), DeviceManagementEvent
                .builder()
                .deviceId(deviceId)
                .registrationToken(registrationToken)
                .ownerObjectId(ownerId)
                .type(DeviceManagementEventTypeEnum.ADD_DEVICE)
                .build());
    }

    /**
     *
     * @param ownerId
     * @param deviceId
     * @param registrationToken
     */
    @Override
    public void updateDevice(final String ownerId, final String deviceId, final String registrationToken) {
        Assert.notNull(ownerId, "Owner Id can not be null");
        Assert.notNull(deviceId, "Device Id can not be null");
        Assert.notNull(registrationToken, "Registration Token can not be null");
        Assert.isTrue(ObjectId.isValid(ownerId), "Owner Id is not valid");
        streamBridge.send(streamChannelsProperties.getDeviceManagement(), DeviceManagementEvent
                .builder()
                .deviceId(deviceId)
                .registrationToken(registrationToken)
                .ownerObjectId(ownerId)
                .type(DeviceManagementEventTypeEnum.UPDATE_DEVICE)
                .build());
    }

    /**
     *
     * @param ownerId
     * @param deviceId
     * @param registrationToken
     */
    @Override
    public void createOrUpdateDevice(final String ownerId, final String deviceId, final String registrationToken) {
        Assert.notNull(ownerId, "Owner Id can not be null");
        Assert.notNull(deviceId, "Device Id can not be null");
        Assert.notNull(registrationToken, "Registration Token can not be null");
        Assert.isTrue(ObjectId.isValid(ownerId), "Owner Id is not valid");
        streamBridge.send(streamChannelsProperties.getDeviceManagement(), DeviceManagementEvent
                .builder()
                .deviceId(deviceId)
                .registrationToken(registrationToken)
                .ownerObjectId(ownerId)
                .type(DeviceManagementEventTypeEnum.CREATE_OR_UPDATE_DEVICE)
                .build());
    }

    /**
     *
     * @param deviceId
     */
    @Override
    public void removeDeviceFromGroup(final String deviceId) {
        Assert.notNull(deviceId, "Device Id can not be null");
        streamBridge.send(streamChannelsProperties.getDeviceManagement(), DeviceManagementEvent
                .builder()
                .deviceId(deviceId)
                .type(DeviceManagementEventTypeEnum.REMOVE_DEVICE)
                .build());
    }

}
