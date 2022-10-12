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
