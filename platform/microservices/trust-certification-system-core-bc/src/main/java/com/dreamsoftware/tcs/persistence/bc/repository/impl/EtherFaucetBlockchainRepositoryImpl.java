package com.dreamsoftware.tcs.persistence.bc.repository.impl;

import com.dreamsoftware.tcs.contracts.EtherFaucetContract;
import com.dreamsoftware.tcs.contracts.ext.EtherFaucetContractExt;
import com.dreamsoftware.tcs.exception.LoadWalletException;
import com.dreamsoftware.tcs.persistence.bc.repository.IEtherFaucetBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.core.SupportBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.EtherFaucetEventEntity;
import com.dreamsoftware.tcs.persistence.bc.repository.mapper.EtherFaucetEventEntityMapper;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.FastRawTransactionManager;

/**
 * @author ssanchez
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EtherFaucetBlockchainRepositoryImpl extends SupportBlockchainRepository implements IEtherFaucetBlockchainRepository {

    private final EtherFaucetEventEntityMapper etherFaucetEventEntityMapper;

    /**
     * Get Initial Amount
     *
     * @return
     * @throws RepositoryException
     */
    @Override
    public BigInteger getInitialAmount() throws RepositoryException {
        try {
            final EtherFaucetContractExt etherFaucetContract = loadEtherFaucetContract();
            return etherFaucetContract.getInitialAmount().send();
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     * Set intial Amount
     *
     * @param amount
     * @throws RepositoryException
     */
    @Override
    public void setInitialAmount(final BigInteger amount) throws RepositoryException {
        Assert.notNull(amount, "amount can not be null");
        log.debug("setInitialAmount to -> " + amount);
        try {
            final EtherFaucetContractExt etherFaucetContract = loadEtherFaucetContract();
            final TransactionReceipt tr = etherFaucetContract.setInitialAmount(amount).send();
            log.debug("TransactionReceipt -> " + tr.getBlockHash());
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @return @throws RepositoryException
     */
    @Override
    public BigInteger getBalance() throws RepositoryException {
        log.debug("getBalance CALLED!");
        try {
            final EtherFaucetContractExt etherFaucetContract = loadEtherFaucetContract();
            return etherFaucetContract.getBalance().send();
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     * Add Seed Funds
     *
     * @param walletHash
     */
    @Override
    public void addSeedFunds(final String walletHash) throws RepositoryException {
        Assert.notNull(walletHash, "Wallet Hash can not be null");
        log.debug("Add Seed Funds to -> " + walletHash);
        try {
            final Credentials credentials = walletService.loadCredentials(walletHash);
            final EtherFaucetContractExt etherFaucetContract = loadEtherFaucetContract();
            final TransactionReceipt tr = etherFaucetContract.sendSeedFundsTo(credentials.getAddress()).send();
            log.debug("TransactionReceipt -> " + tr.getBlockHash());
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param amount
     * @throws RepositoryException
     */
    @Override
    public void depositFunds(final BigInteger amount) throws RepositoryException {
        log.debug("Deposit Funds CALLED!");
        try {
            final EtherFaucetContractExt etherFaucetContract = loadEtherFaucetContract();
            etherFaucetContract.deposit(amount).send();
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param targetWalletHash
     * @param amountInWei
     * @throws RepositoryException
     */
    @Override
    public void sendFunds(final String targetWalletHash, final Long amountInWei) throws RepositoryException {
        Assert.notNull(targetWalletHash, "Target Wallet hash can not be null");
        Assert.notNull(amountInWei, "amountInWei can not be null");
        log.debug("sendFunds Funds -> " + targetWalletHash);
        try {
            final EtherFaucetContractExt etherFaucetContract = loadEtherFaucetContract();
            final Credentials credentials = walletService.loadCredentials(targetWalletHash);
            etherFaucetContract.sendFunds(credentials.getAddress(), BigInteger.valueOf(amountInWei)).send();
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     * Get events
     *
     * @return
     */
    @Override
    public Flowable<EtherFaucetEventEntity> getEvents() throws RepositoryException {
        try {
            final EtherFaucetContract faucetContract = EtherFaucetContract.load(properties.getTrustEtherFaucetContractAddress(),
                    web3j, rootTxManager, properties.gas());
            return Flowable.merge(List.of(
                    faucetContract.onDepositEventFlowable(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST).map(etherFaucetEventEntityMapper::mapEventToEntity),
                    faucetContract.onSendSeedFundsEventFlowable(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST).map(etherFaucetEventEntityMapper::mapEventToEntity),
                    faucetContract.onSendFundsEventFlowable(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST).map(etherFaucetEventEntityMapper::mapEventToEntity)
            ));
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     * Private Methods
     */
    /**
     * Load Token Management Contract
     *
     * @param walletHash
     * @return
     * @throws LoadWalletException
     */
    private EtherFaucetContractExt loadEtherFaucetContract(final String walletHash) throws LoadWalletException {
        final Credentials credentials = walletService.loadCredentials(walletHash);
        final FastRawTransactionManager txManager = new FastRawTransactionManager(web3j, credentials, properties.getChainId());
        return EtherFaucetContractExt.load(properties.getTrustEtherFaucetContractAddress(),
                web3j, txManager,
                properties.gas()
        );
    }

    /**
     *
     * @return
     */
    private EtherFaucetContractExt loadEtherFaucetContract() {
        return EtherFaucetContractExt.load(properties.getTrustEtherFaucetContractAddress(),
                web3j, rootTxManager, properties.gas());
    }
}
