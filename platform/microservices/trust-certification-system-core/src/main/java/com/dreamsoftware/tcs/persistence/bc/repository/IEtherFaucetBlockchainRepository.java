package com.dreamsoftware.tcs.persistence.bc.repository;

import com.dreamsoftware.tcs.persistence.bc.repository.entity.EtherFaucetEventEntity;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;

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
     * com.dreamsoftware.tcs.persistence.exception.RepositoryException
     */
    void addSeedFunds(final String walletHash) throws RepositoryException;

}
