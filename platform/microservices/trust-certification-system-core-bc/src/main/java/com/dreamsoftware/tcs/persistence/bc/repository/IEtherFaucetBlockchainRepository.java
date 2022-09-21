package com.dreamsoftware.tcs.persistence.bc.repository;

import com.dreamsoftware.tcs.persistence.bc.repository.entity.EtherFaucetEventEntity;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import java.math.BigInteger;

/**
 *
 * @author ssanchez
 */
public interface IEtherFaucetBlockchainRepository extends IBlockchainEventRepository<EtherFaucetEventEntity> {

    /**
     *
     * @return @throws RepositoryException
     */
    BigInteger getInitialAmount() throws RepositoryException;

    /**
     *
     * @return @throws RepositoryException
     */
    BigInteger getBalance() throws RepositoryException;

    /**
     *
     * @param amount
     * @throws RepositoryException
     */
    void setInitialAmount(final BigInteger amount) throws RepositoryException;

    /**
     * Add Seed Funds
     *
     * @param walletHash
     * @throws com.dreamsoftware.tcs.persistence.exception.RepositoryException
     */
    void addSeedFunds(final String walletHash) throws RepositoryException;

    /**
     *
     * @param amout
     * @throws RepositoryException
     */
    void depositFunds(final BigInteger amout) throws RepositoryException;

    /**
     *
     * @param targetWalletHash
     * @param amountInWei
     * @throws RepositoryException
     */
    void sendFunds(final String targetWalletHash, final Long amountInWei) throws RepositoryException;
}
