package com.dreamsoftware.tcs.persistence.bc.repository;

import com.dreamsoftware.tcs.persistence.exception.RepositoryException;

/**
 *
 * @author ssanchez
 */
public interface ITokenManagementBlockchainRepository {

    /**
     * Add Tokens
     *
     * @param walletHash
     * @param tokens
     * @throws
     * com.dreamsoftware.tcs.persistence.exception.RepositoryException
     */
    void addTokens(final String walletHash, final Long tokens) throws RepositoryException;

}
