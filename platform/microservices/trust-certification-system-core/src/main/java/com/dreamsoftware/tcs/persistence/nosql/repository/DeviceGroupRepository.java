package com.dreamsoftware.tcs.persistence.nosql.repository;

import com.dreamsoftware.tcs.persistence.nosql.entity.DeviceGroupEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ssanchez
 */
@Repository
public interface DeviceGroupRepository extends MongoRepository<DeviceGroupEntity, ObjectId> {

    /**
     *
     * @param notificationKeyName
     * @return
     */
    @Query(value = "{ 'notification_key_name' : ?0 }", fields = "{ 'notification_key' : 1 }")
    String getNotificationKey(final String notificationKeyName);

    /**
     *
     * @param notificationKeyName
     * @return
     */
    DeviceGroupEntity findByNotificationKeyName(final String notificationKeyName);

    /**
     *
     * @param notificationKeyName
     * @return
     */
    Long countByNotificationKeyName(final String notificationKeyName);

    /**
     *
     * @param id
     * @return
     */
    DeviceGroupEntity findByOwnerId(final ObjectId id);

}
