package com.dreamsoftware.blockchaingateway.persistence.bc.repository;

import com.dreamsoftware.blockchaingateway.persistence.exception.RepositoryException;

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
     * com.dreamsoftware.blockchaingateway.persistence.exception.RepositoryException
     */
    void addTokens(final String walletHash, final Long tokens) throws RepositoryException;

}
