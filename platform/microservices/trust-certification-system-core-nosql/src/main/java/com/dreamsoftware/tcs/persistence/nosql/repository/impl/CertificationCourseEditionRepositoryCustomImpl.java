package com.dreamsoftware.tcs.persistence.nosql.repository.impl;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEditionEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseEditionRepositoryCustom;
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
public class CertificationCourseEditionRepositoryCustomImpl implements CertificationCourseEditionRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void updateStatus(ObjectId id, CertificationCourseStateEnum newStatus) {
        mongoTemplate.updateFirst(
                new Query(Criteria.where("id").is(id)),
                new Update().set("status", newStatus.name()), CertificationCourseEditionEntity.class);
    }
}
