package com.dreamsoftware.tcs.persistence.nosql.repository.impl;

import com.dreamsoftware.tcs.persistence.nosql.entity.DeviceEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.DeviceRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 *
 * @author ssanchez
 */
public class DeviceRepositoryCustomImpl implements DeviceRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void updateDeviceToken(String deviceId, String newToken) {
        mongoTemplate.updateFirst(
                new Query(Criteria.where("device_id").is(deviceId)),
                Update.update("registration_token", newToken), DeviceEntity.class);
    }
}
