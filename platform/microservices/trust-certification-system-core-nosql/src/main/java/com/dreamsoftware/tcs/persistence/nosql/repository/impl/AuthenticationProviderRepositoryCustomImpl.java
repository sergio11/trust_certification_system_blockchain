package com.dreamsoftware.tcs.persistence.nosql.repository.impl;

import com.dreamsoftware.tcs.persistence.nosql.entity.AuthenticationProviderEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.AuthenticationProviderRepositoryCustom;
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
public class AuthenticationProviderRepositoryCustomImpl implements AuthenticationProviderRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     *
     * @param token
     * @param key
     */
    @Override
    public void updateAuthenticationProviderTokenForkey(final String token, final String key) {
        Assert.notNull(token, "token can not be null");
        Assert.notNull(key, "key can not be null");
        mongoTemplate.updateFirst(
                new Query(Criteria.where("key").is(key)),
                new Update()
                        .set("token ", token),
                AuthenticationProviderEntity.class);
    }

}
