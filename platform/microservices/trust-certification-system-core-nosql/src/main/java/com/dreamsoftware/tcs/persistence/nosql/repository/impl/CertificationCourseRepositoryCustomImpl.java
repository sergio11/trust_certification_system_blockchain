package com.dreamsoftware.tcs.persistence.nosql.repository.impl;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseRepositoryCustom;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 *
 * @author ssanchez
 */
public class CertificationCourseRepositoryCustomImpl implements CertificationCourseRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public CertificationCourseEntity updateStatus(ObjectId id, CertificationCourseStateEnum newStatus) {
        mongoTemplate.updateFirst(
                new Query(Criteria.where("id").is(id)),
                new Update().set("status", newStatus.name()), CertificationCourseEntity.class);
        return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), CertificationCourseEntity.class);
    }
}
