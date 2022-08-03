package com.dreamsoftware.tcs.service;

import com.dreamsoftware.tcs.model.events.NewTokensOrderApprovedEvent;
import com.dreamsoftware.tcs.model.events.OnNewUserRegistrationEvent;
import com.dreamsoftware.tcs.model.events.OnTokensOrderCompletedEvent;

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
     */
    void validate(String walletHash);

    /**
     *
     * @param event
     */
    void addTokensToWallet(final NewTokensOrderApprovedEvent event) throws Exception;

    /**
     *
     * @param event
     */
    void completeOrder(final OnTokensOrderCompletedEvent event);

}
