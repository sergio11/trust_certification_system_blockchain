package com.dreamsoftware.tcs.persistence.nosql.repository;

import com.dreamsoftware.tcs.persistence.nosql.entity.DeviceRegistrationRequestEntity;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ssanchez
 */
@Repository
public interface DeviceRegistrationRequestRepository extends MongoRepository<DeviceRegistrationRequestEntity, ObjectId> {

    /**
     *
     * @param id
     * @return
     */
    Long deleteByOwner(final ObjectId id);

    /**
     *
     * @param deviceId
     * @return
     */
    Long deleteByDeviceId(final String deviceId);

    /**
     *
     * @param deviceId
     * @return
     */
    DeviceRegistrationRequestEntity findByDeviceId(final String deviceId);

    /**
     *
     * @param deviceId
     * @param owner
     * @return
     */
    DeviceRegistrationRequestEntity findByDeviceIdAndOwner(final String deviceId, final ObjectId owner);

    /**
     *
     * @param owner
     * @return
     */
    List<DeviceRegistrationRequestEntity> findByOwner(final ObjectId owner);
}
