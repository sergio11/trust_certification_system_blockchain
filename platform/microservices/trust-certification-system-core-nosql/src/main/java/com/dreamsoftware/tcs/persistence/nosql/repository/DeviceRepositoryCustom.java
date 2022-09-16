package com.dreamsoftware.tcs.persistence.nosql.repository;

/**
 *
 * @author ssanchez
 */
public interface DeviceRepositoryCustom {

    /**
     *
     * @param deviceId
     * @param newToken
     */
    void updateDeviceToken(final String deviceId, final String newToken);
}
