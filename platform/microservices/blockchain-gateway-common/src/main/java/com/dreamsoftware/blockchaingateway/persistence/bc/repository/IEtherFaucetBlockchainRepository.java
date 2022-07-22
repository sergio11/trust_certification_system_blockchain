package com.dreamsoftware.blockchaingateway.persistence.bc.repository;

import com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.EtherFaucetAuthorityEventEntity;
import com.dreamsoftware.blockchaingateway.persistence.exception.RepositoryException;
import io.reactivex.Flowable;

/**
 *
 * @author ssanchez
 */
public interface IEtherFaucetBlockchainRepository {

    /**
     * Add Seed Funds
     *
     * @param walletHash
     * @throws
     * com.dreamsoftware.blockchaingateway.persistence.exception.RepositoryException
     */
    void addSeedFunds(final String walletHash) throws RepositoryException;

    /**
     * Get Events
     *
     * @return
     * @throws
     * com.dreamsoftware.blockchaingateway.persistence.exception.RepositoryException
     */
    Flowable<EtherFaucetAuthorityEventEntity> getEvents() throws RepositoryException;

}
