package com.dreamsoftware.tcs.persistence.nosql.repository.impl;

import com.dreamsoftware.tcs.persistence.nosql.entity.UserAccountStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;
import java.util.Date;

/**
 * @author ssanchez
 */
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     *
     * @param email
     * @param token
     */
    @Override
    public UserEntity updateLastPasswordReset(final String email, final String token) {
        Assert.notNull(email, "email Has can not be null");
        Assert.notNull(token, "token can not be null");
        mongoTemplate.updateFirst(
                new Query(Criteria.where("email").is(email)),
                new Update()
                        .set("last_password_reset", new Date())
                        .set("confirmation_token", token), UserEntity.class);
        return mongoTemplate.findOne(new Query(Criteria.where("email").is(email)), UserEntity.class);
    }

    /**
     *
     * @param password
     * @param token
     */
    @Override
    public void updatePasswordByConfirmationToken(final String password, final String token) {
        Assert.notNull(password, "password has can not be null");
        Assert.notNull(token, "token can not be null");
        mongoTemplate.updateFirst(
                new Query(Criteria.where("confirmationToken").is(token)),
                new Update()
                        .set("password", password), UserEntity.class);
    }

    /**
     * @param walletHash
     */
    @Override
    public void updateStatusAsValidatedByWalletHash(final String walletHash) {
        Assert.notNull(walletHash, "walletHash can not be null");
        mongoTemplate.updateFirst(
                new Query(Criteria.where("wallet_hash").is(walletHash)),
                new Update()
                        .set("activation_date", new Date())
                        .set("state", UserStateEnum.VALIDATED)
                        .set("account_state", UserAccountStateEnum.ENABLED), UserEntity.class);
    }

    /**
     *
     * @param caId
     * @param accountState
     */
    @Override
    public void updateAccountStateByCaId(String caId, UserAccountStateEnum accountState) {
        Assert.notNull(caId, "caId can not be null");
        Assert.notNull(accountState, "accountState can not be null");
        mongoTemplate.updateFirst(
                new Query(Criteria.where("ca").is(caId)),
                new Update().set("account_state", accountState), UserEntity.class);
    }

    /**
     *
     * @param walletHash
     * @param accountState
     */
    @Override
    public void updateAccountStateByWalletHash(String walletHash, UserAccountStateEnum accountState) {
        Assert.notNull(walletHash, "caId can not be null");
        Assert.notNull(accountState, "accountState can not be null");
        mongoTemplate.updateFirst(
                new Query(Criteria.where("wallet_hash").is(walletHash)),
                new Update().set("account_state", accountState), UserEntity.class);
    }

}
