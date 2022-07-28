package com.dreamsoftware.tcs.persistence.nosql.repository.impl;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 *
 * @author ssanchez
 */
public class CertificationCourseRepositoryImpl implements CertificationCourseRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void updateStatus(String courseId, CertificationCourseStateEnum newStatus) {
        mongoTemplate.updateFirst(
                new Query(Criteria.where("course_id").is(courseId)),
                new Update().set("status", newStatus.name()), CertificationCourseEntity.class);
    }
}
