package com.dreamsoftware.tcs.persistence.nosql.repository.impl;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificateIssuanceRequestEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificateStatusEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificateIssuanceRequestRepositoryCustom;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;

/**
 *
 * @author ssanchez
 */
public class CertificateIssuanceRequestRepositoryImpl implements CertificateIssuanceRequestRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void updateStatus(ObjectId id, CertificateStatusEnum newStatus) {
        Assert.notNull(id, "Certificate Id can not be null");
        Assert.notNull(newStatus, "New Status can not be null");
        mongoTemplate.updateFirst(
                new Query(Criteria.where("id").is(id)),
                new Update().set("status", newStatus.name()), CertificateIssuanceRequestEntity.class);
    }
}
