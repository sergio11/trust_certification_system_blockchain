package com.dreamsoftware.tcs.services;

import com.dreamsoftware.tcs.web.dto.response.DeviceDTO;
import org.bson.types.ObjectId;

/**
 *
 * @author ssanchez
 */
public interface IDeviceGroupsService {

    /**
     *
     * @param ownerId
     * @return
     * @throws Throwable
     */
    Iterable<DeviceDTO> getDevicesByOwner(final ObjectId ownerId) throws Throwable;

    /**
     *
     * @param ownerId
     * @param deviceId
     * @param registrationToken
     */
    void addDeviceToGroup(final ObjectId ownerId, final String deviceId, final String registrationToken);

    /**
     *
     * @param ownerId
     * @param deviceId
     * @param registrationToken
     */
    void updateDevice(final ObjectId ownerId, final String deviceId, final String registrationToken);

    /**
     *
     * @param ownerId
     * @param deviceId
     * @param registrationToken
     */
    void createOrUpdateDevice(final ObjectId ownerId, final String deviceId, final String registrationToken);

    /**
     *
     * @param deviceId
     */
    void removeDeviceFromGroup(final String deviceId);

}
