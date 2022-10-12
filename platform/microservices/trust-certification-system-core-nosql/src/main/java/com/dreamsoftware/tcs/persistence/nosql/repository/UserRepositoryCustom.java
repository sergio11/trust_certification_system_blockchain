package com.dreamsoftware.tcs.persistence.nosql.repository;

import com.dreamsoftware.tcs.persistence.nosql.entity.UserAccountStateEnum;

import java.util.Date;

/**
 *
 * @author ssanchez
 */
public interface UserRepositoryCustom {

    /**
     *
     * @param walletHash
     * @param date
     */
    void updateLastPasswordReset(final String walletHash, final Date date);

    /**
     *
     * @param walletHash
     */
    void updateStatusAsValidatedByWalletHash(final String walletHash);

    /**
     *
     * @param caId
     * @param accountState
     */
    void updateAccountStateByCaId(final String caId, final UserAccountStateEnum accountState);

    /**
     *
     * @param walletHash
     * @param accountState
     */
    void updateAccountStateByWalletHash(final String walletHash, final UserAccountStateEnum accountState);

}
