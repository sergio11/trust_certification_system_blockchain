package com.dreamsoftware.tcs.service;

import com.dreamsoftware.tcs.model.events.OnNewUserRegistrationEvent;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;

/**
 *
 * @author ssanchez
 */
public interface IUserService {

    /**
     *
     * @param event
     * @throws com.dreamsoftware.tcs.persistence.exception.RepositoryException
     */
    void register(OnNewUserRegistrationEvent event) throws RepositoryException;

    /**
     * Validate User
     *
     * @param walletHash
     */
    void validate(String walletHash);

}
