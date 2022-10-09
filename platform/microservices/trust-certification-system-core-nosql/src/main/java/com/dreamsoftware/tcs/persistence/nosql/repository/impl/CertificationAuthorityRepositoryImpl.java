package com.dreamsoftware.tcs.persistence.nosql.repository.impl;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationAuthorityEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationAuthorityRepositoryCustom;
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
public class CertificationAuthorityRepositoryImpl implements CertificationAuthorityRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public CertificationAuthorityEntity update(String caId, CertificationAuthorityEntity certificationAuthorityEntity) {
        Assert.notNull(caId, "caId can not be null");
        Assert.notNull(certificationAuthorityEntity, "certificationAuthorityEntity can not be null");
        mongoTemplate.updateFirst(
                new Query(Criteria.where("id").is(caId)),
                new Update()
                        .set("name", certificationAuthorityEntity.getName())
                        .set("location", certificationAuthorityEntity.getLocation())
                        .set("executive_director", certificationAuthorityEntity.getExecutiveDirector())
                        .set("support_mail", certificationAuthorityEntity.getSupportMail())
                        .set("default_cost_of_issuing_certificate", certificationAuthorityEntity.getDefaultCostOfIssuingCertificate()), CertificationAuthorityEntity.class);
        return mongoTemplate.findOne(new Query(Criteria.where("id").is(caId)), CertificationAuthorityEntity.class);
    }
}
