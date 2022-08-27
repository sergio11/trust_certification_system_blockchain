package com.dreamsoftware.tcs.service;

import com.dreamsoftware.tcs.persistence.nosql.entity.CreatedOrderEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.stream.events.NewTokensOrderApprovedEvent;
import com.dreamsoftware.tcs.stream.events.OnNewUserRegistrationEvent;
import com.dreamsoftware.tcs.stream.events.OnTokensOrderCompletedEvent;

/**
 *
 * @author ssanchez
 */
public interface IUserService {

    /**
     *
     * @param event
     * @throws java.lang.Exception
     */
    void register(OnNewUserRegistrationEvent event) throws Exception;

    /**
     * Validate User
     *
     * @param walletHash
     * @return
     */
    UserEntity validate(String walletHash);

    /**
     *
     * @param event
     * @return
     * @throws java.lang.Exception
     */
    Long addTokensToWallet(final NewTokensOrderApprovedEvent event) throws Exception;

    /**
     *
     * @param event
     * @return
     */
    CreatedOrderEntity completeOrder(final OnTokensOrderCompletedEvent event);

    /**
     *
     * @return
     */
    long deleteUnactivatedAccounts();

}
