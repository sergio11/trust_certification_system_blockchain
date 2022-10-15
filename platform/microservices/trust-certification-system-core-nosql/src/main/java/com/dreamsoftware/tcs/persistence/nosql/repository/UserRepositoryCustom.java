package com.dreamsoftware.tcs.persistence.nosql.repository;

import com.dreamsoftware.tcs.persistence.nosql.entity.UserAccountStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;

/**
 *
 * @author ssanchez
 */
public interface UserRepositoryCustom {

    /**
     *
     * @param email
     * @param token
     */
    UserEntity updateLastPasswordReset(final String email, final String token);

    /**
     *
     * @param password
     * @param token
     */
    void updatePasswordByConfirmationToken(final String password, final String token);

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
