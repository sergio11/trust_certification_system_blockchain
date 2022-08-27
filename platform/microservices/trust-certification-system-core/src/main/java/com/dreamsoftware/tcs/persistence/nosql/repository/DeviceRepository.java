package com.dreamsoftware.tcs.persistence.nosql.repository;

import com.dreamsoftware.tcs.persistence.nosql.entity.DeviceEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.DeviceGroupEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ssanchez
 */
@Repository
public interface DeviceRepository extends MongoRepository<DeviceEntity, ObjectId>, DeviceRepositoryCustom {

    /**
     *
     * @param deviceGroup
     * @return
     */
    Iterable<DeviceEntity> findByDeviceGroup(final DeviceGroupEntity deviceGroup);

    /**
     *
     * @param registrationToken
     * @return
     */
    DeviceEntity findByRegistrationToken(final String registrationToken);

    /**
     *
     * @param registrationToken
     * @return
     */
    Long deleteByRegistrationToken(final String registrationToken);

    /**
     *
     * @param deviceId
     * @return
     */
    Long deleteByDeviceId(final String deviceId);

    /**
     *
     * @param registrationToken
     * @return
     */
    Long countByRegistrationToken(final String registrationToken);

    /**
     *
     * @param deviceId
     * @return
     */
    Long countByDeviceId(final String deviceId);

    /**
     *
     * @param deviceId
     * @return
     */
    DeviceEntity findByDeviceId(final String deviceId);

    /**
     *
     * @param deviceGroupId
     * @return
     */
    Long countByDeviceGroupId(final ObjectId deviceGroupId);

    /**
     *
     * @param deviceGroup
     * @return
     */
    Long deleteByDeviceGroup(final DeviceGroupEntity deviceGroup);
}
