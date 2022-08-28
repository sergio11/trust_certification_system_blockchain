package com.dreamsoftware.tcs.service.impl;

import com.dreamsoftware.tcs.fcm.exception.DeviceAddToGroupFailedException;
import com.dreamsoftware.tcs.fcm.exception.DeviceGroupCreateFailedException;
import com.dreamsoftware.tcs.fcm.exception.RemoveDeviceFromGroupFailedException;
import com.dreamsoftware.tcs.fcm.exception.UpdateDeviceFailedException;
import com.dreamsoftware.tcs.fcm.properties.FCMCustomProperties;
import com.dreamsoftware.tcs.fcm.service.IPushNotificationsService;
import com.dreamsoftware.tcs.persistence.nosql.entity.DeviceEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.DeviceGroupEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.DeviceGroupRepository;
import com.dreamsoftware.tcs.persistence.nosql.repository.DeviceRepository;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.service.ISecurityTokenGeneratorService;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.dreamsoftware.tcs.service.IDevicesManagementService;

/**
 *
 * @author ssanchez
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DevicesManagementServiceImpl implements IDevicesManagementService {

    private final IPushNotificationsService pushNotificationsService;
    private final DeviceGroupRepository deviceGroupRepository;
    private final DeviceRepository deviceRepository;
    private final ISecurityTokenGeneratorService tokenGeneratorService;
    private final FCMCustomProperties firebaseCustomProperties;
    private final UserRepository userRepository;

    /**
     *
     * @param ownerObjectId
     * @param deviceId
     * @param registrationToken
     */
    @Override
    public void addDeviceToGroup(final String ownerObjectId, final String deviceId, final String registrationToken) {
        Assert.notNull(ownerObjectId, "ownerObjectId can not be null");
        Assert.notNull(deviceId, "deviceId can not be null");
        Assert.notNull(registrationToken, "registrationToken can not be null");
        deviceGroupRepository.findByOwnerId(new ObjectId(ownerObjectId)).ifPresent((deviceGroup) -> {
            try {
                addDeviceToGroup(deviceGroup, deviceId, registrationToken);
            } catch (final DeviceAddToGroupFailedException ex) {
                Logger.getLogger(DevicesManagementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    /**
     *
     * @param ownerObjectId
     * @param deviceId
     * @param registrationToken
     */
    @Override
    public void createDeviceGroupWithDevice(final String ownerObjectId, final String deviceId, final String registrationToken) {
        Assert.notNull(ownerObjectId, "ownerObjectId can not be null");
        Assert.notNull(deviceId, "deviceId can not be null");
        Assert.notNull(registrationToken, "registrationToken can not be null");
        userRepository.findById(new ObjectId(ownerObjectId)).ifPresent((ownerEntity) -> {
            try {
                createDeviceGroupWithDevice(ownerEntity, deviceId, registrationToken);
            } catch (DeviceGroupCreateFailedException ex) {
                Logger.getLogger(DevicesManagementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    /**
     *
     * @param ownerObjectId
     * @param deviceId
     * @param registrationToken
     */
    @Override
    public void updateDevice(final String ownerObjectId, final String deviceId, final String registrationToken) {
        Assert.notNull(ownerObjectId, "ownerObjectId can not be null");
        Assert.notNull(deviceId, "deviceId can not be null");
        Assert.notNull(registrationToken, "registrationToken can not be null");
        deviceRepository.findByDeviceId(deviceId).ifPresent((deviceEntity) -> {
            try {
                updateDevice(deviceEntity, ownerObjectId, registrationToken);
            } catch (UpdateDeviceFailedException ex) {
                Logger.getLogger(DevicesManagementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    /**
     *
     * @param ownerObjectId
     * @param deviceId
     * @param registrationToken
     */
    @Override
    public void createOrUpdateDevice(final String ownerObjectId, final String deviceId, final String registrationToken) {
        Assert.notNull(ownerObjectId, "ownerObjectId can not be null");
        Assert.notNull(deviceId, "deviceId can not be null");
        Assert.notNull(registrationToken, "registrationToken can not be null");
        userRepository.findById(new ObjectId(ownerObjectId)).ifPresent((ownerEntity) -> {
            try {
                final Optional<DeviceEntity> deviceEntity = deviceRepository.findByDeviceId(deviceId);
                if (deviceEntity.isPresent()) {
                    try {
                        updateDevice(deviceEntity.get(), ownerObjectId, registrationToken);
                    } catch (UpdateDeviceFailedException ex) {
                        Logger.getLogger(DevicesManagementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    final Optional<DeviceGroupEntity> deviceGroupEntity = deviceGroupRepository.findByOwnerId(ownerEntity.getId());
                    if (deviceGroupEntity.isPresent()) {
                        try {
                            addDeviceToGroup(deviceGroupEntity.get(), deviceId, registrationToken);
                        } catch (DeviceAddToGroupFailedException ex) {
                            createDeviceGroupWithDevice(ownerEntity, deviceId, registrationToken);
                        }
                    } else {
                        createDeviceGroupWithDevice(ownerEntity, deviceId, registrationToken);
                    }
                }
            } catch (final DeviceGroupCreateFailedException ex) {

            }

        });
    }

    /**
     *
     * @param deviceId
     */
    @Override
    public void removeDeviceFromGroup(final String deviceId) {
        Assert.notNull(deviceId, "deviceId can not be null");
        deviceRepository.findByDeviceId(deviceId)
                .ifPresent((deviceEntity) -> {
                    try {
                        removeDeviceFromGroup(deviceEntity);
                    } catch (RemoveDeviceFromGroupFailedException ex) {
                        Logger.getLogger(DevicesManagementServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
    }

    /**
     * Private Methods
     */
    /**
     *
     * @param deviceEntity
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private void removeDeviceFromGroup(final DeviceEntity deviceEntity) throws RemoveDeviceFromGroupFailedException {
        Assert.notNull(deviceEntity, "deviceEntity can not be null");
        try {
            final DeviceGroupEntity deviceGroupEntity = deviceEntity.getDeviceGroup();
            pushNotificationsService.removeDeviceFromGroup(deviceGroupEntity.getNotificationKeyName(),
                    deviceGroupEntity.getNotificationKey(), deviceEntity.getRegistrationToken()).get();
            final ObjectId deviceGroupId = deviceEntity.getDeviceGroup().getId();
            deviceRepository.delete(deviceEntity);
            final Long totalDevicesIntoGroup = deviceRepository.countByDeviceGroupId(deviceGroupId);
            if (totalDevicesIntoGroup == 0) {
                deviceGroupRepository.deleteById(deviceGroupId);
            }
        } catch (InterruptedException | ExecutionException ex) {
            throw new RemoveDeviceFromGroupFailedException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param deviceGroupEntity
     * @param deviceId
     * @param registrationToken
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private void addDeviceToGroup(final DeviceGroupEntity deviceGroupEntity, final String deviceId, final String registrationToken) throws DeviceAddToGroupFailedException {
        Assert.notNull(deviceGroupEntity, "deviceGroupEntity can not be null");
        Assert.notNull(deviceId, "deviceId can not be null");
        Assert.notNull(registrationToken, "registrationToken can not be null");
        try {
            pushNotificationsService.addDeviceToGroup(deviceGroupEntity.getNotificationKeyName(),
                    deviceGroupEntity.getNotificationKey(), registrationToken).get();

            deviceRepository.save(DeviceEntity.builder()
                    .createdAt(new Date())
                    .deviceId(deviceId)
                    .registrationToken(registrationToken)
                    .deviceGroup(deviceGroupEntity)
                    .build());
        } catch (InterruptedException | ExecutionException ex) {
            throw new DeviceAddToGroupFailedException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param owner
     * @param deviceId
     * @param registrationToken
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private void createDeviceGroupWithDevice(final UserEntity owner, final String deviceId, final String registrationToken) throws DeviceGroupCreateFailedException {
        Assert.notNull(owner, "owner can not be null");
        Assert.notNull(deviceId, "deviceId can not be null");
        Assert.notNull(registrationToken, "registrationToken can not be null");
        try {
            final String notificationGroupName = String.format("%s_%s", firebaseCustomProperties.getGroupPrefix(), tokenGeneratorService.generateToken(owner.getId().toString()));
            final ResponseEntity<Map<String, String>> response = pushNotificationsService.createNotificationGroup(notificationGroupName, Collections.singletonList(registrationToken))
                    .get();
            if (response != null && response.getBody() != null
                    && response.getBody().containsKey("notification_key")
                    && !response.getBody().get("notification_key").isEmpty()) {

                final String groupKey = response.getBody().get("notification_key");
                log.debug("Device Group Key -> " + groupKey);
                log.debug("Device Group Name -> " + notificationGroupName);
                final DeviceGroupEntity deviceGroupSaved = deviceGroupRepository.save(DeviceGroupEntity
                        .builder()
                        .notificationKeyName(notificationGroupName)
                        .notificationKey(groupKey)
                        .createdAt(new Date())
                        .owner(owner)
                        .build());
                deviceRepository.save(DeviceEntity
                        .builder()
                        .deviceId(deviceId)
                        .registrationToken(registrationToken)
                        .createdAt(new Date())
                        .deviceGroup(deviceGroupSaved)
                        .build());
            }
        } catch (InterruptedException | ExecutionException ex) {
            throw new DeviceGroupCreateFailedException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param device
     * @param ownerObjectId
     * @param registrationToken
     * @throws UpdateDeviceFailedException
     */
    private void updateDevice(final DeviceEntity device, final String ownerObjectId, final String registrationToken) throws UpdateDeviceFailedException {
        try {
            final UserEntity owner = device.getDeviceGroup().getOwner();
            final DeviceGroupEntity deviceGroupEntity = device.getDeviceGroup();
            if (owner != null && owner.getId().equals(new ObjectId(ownerObjectId))) {
                if (device.getRegistrationToken() == null || device.getRegistrationToken().isEmpty()
                        || !device.getRegistrationToken().equals(registrationToken)) {
                    pushNotificationsService.updateDeviceToken(deviceGroupEntity.getNotificationKeyName(),
                            deviceGroupEntity.getNotificationKey(), device.getRegistrationToken(), registrationToken).get();
                    log.debug("Registration Token for device " + device.getDeviceId() + " updated");
                    deviceRepository.updateDeviceToken(device.getDeviceId(), registrationToken);
                }
            } else {
                removeDeviceFromGroup(device);
                try {
                    addDeviceToGroup(deviceGroupEntity, device.getDeviceId(), registrationToken);
                } catch (final DeviceAddToGroupFailedException ex) {
                    createDeviceGroupWithDevice(owner, device.getDeviceId(), registrationToken);
                }
            }
        } catch (final DeviceGroupCreateFailedException | RemoveDeviceFromGroupFailedException | InterruptedException | ExecutionException ex) {
            throw new UpdateDeviceFailedException(ex.getMessage(), ex);
        }

    }
}
