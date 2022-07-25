package com.dreamsoftware.blockchaingateway.persistence.bc.repository;

import com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.EtherFaucetEventEntity;
import com.dreamsoftware.blockchaingateway.persistence.exception.RepositoryException;

/**
 *
 * @author ssanchez
 */
public interface IEtherFaucetBlockchainRepository extends IBlockchainEventRepository<EtherFaucetEventEntity> {

    /**
     * Add Seed Funds
     *
     * @param walletHash
     * @throws
     * com.dreamsoftware.blockchaingateway.persistence.exception.RepositoryException
     */
    void addSeedFunds(final String walletHash) throws RepositoryException;

}
