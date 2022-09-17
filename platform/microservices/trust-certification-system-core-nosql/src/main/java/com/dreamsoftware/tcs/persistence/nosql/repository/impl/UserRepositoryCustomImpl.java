package com.dreamsoftware.tcs.persistence.nosql.repository.impl;

import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepositoryCustom;
import java.util.Date;
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
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     *
     * @param walletHash
     * @param date
     */
    @Override
    public void updateLastPasswordReset(final String walletHash, final Date date) {
        Assert.notNull(walletHash, "Wallet Has can not be null");
        Assert.notNull(date, "date can not be null");
        mongoTemplate.updateFirst(
                new Query(Criteria.where("wallet_hash").is(walletHash)),
                Update.update("last_password_reset", date), UserEntity.class);
    }

}
