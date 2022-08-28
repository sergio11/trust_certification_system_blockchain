package com.dreamsoftware.tcs.service;

/**
 *
 * @author ssanchez
 */
public interface IDevicesManagementService {

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
