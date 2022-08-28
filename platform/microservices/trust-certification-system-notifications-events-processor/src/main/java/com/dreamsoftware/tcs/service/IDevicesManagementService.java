package com.dreamsoftware.tcs.service;

import org.bson.types.ObjectId;

/**
 *
 * @author ssanchez
 */
public interface IDevicesManagementService {

    /**
     *
     * @param userId
     * @param title
     * @param body
     */
    void sendNotification(final ObjectId userId, final String title, final String body);

    /**
     *
     * @param ownerObjectId
     * @param deviceId
     * @param registrationToken
     */
    void addDeviceToGroup(final String ownerObjectId, final String deviceId, final String registrationToken);

    /**
     *
     * @param ownerObjectId
     * @param deviceId
     * @param registrationToken
     */
    void createDeviceGroupWithDevice(final String ownerObjectId, final String deviceId, final String registrationToken);

    /**
     *
     * @param ownerObjectId
     * @param deviceId
     * @param registrationToken
     */
    void updateDevice(final String ownerObjectId, final String deviceId, final String registrationToken);

    /**
     *
     * @param ownerObjectId
     * @param deviceId
     * @param registrationToken
     */
    void createOrUpdateDevice(final String ownerObjectId, final String deviceId, final String registrationToken);

    /**
     *
     * @param deviceId
     */
    void removeDeviceFromGroup(final String deviceId);
}
