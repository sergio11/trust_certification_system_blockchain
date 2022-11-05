package com.dreamsoftware.tcs.persistence.nosql.repository.impl;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseRepositoryCustom;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;

/**
 *
 * @author ssanchez
 */
public class CertificationCourseRepositoryCustomImpl implements CertificationCourseRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public CertificationCourseEntity updateStatus(final ObjectId id, final CertificationCourseStateEnum newStatus) {
        mongoTemplate.updateFirst(
                new Query(Criteria.where("id").is(id)),
                new Update().set("status", newStatus.name()), CertificationCourseEntity.class);
        return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), CertificationCourseEntity.class);
    }

    /**
     *
     * @param term
     * @return
     */
    @Override
    public Iterable<CertificationCourseEntity> searchByTerm(final String term) {
        TextQuery textQuery = TextQuery.queryText(new TextCriteria().matchingAny(term)).sortByScore();
        return mongoTemplate.find(textQuery, CertificationCourseEntity.class);
    }
}
