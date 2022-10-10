package com.dreamsoftware.tcs.service;

import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;

/**
 *
 * @author ssanchez
 */
public interface IUserService {


    /**
     * Validate User
     *
     * @param walletHash
     * @return
     */
    UserEntity validate(String walletHash);

    /**
     *
     * @return
     */
    long deleteUnactivatedAccounts();

}
