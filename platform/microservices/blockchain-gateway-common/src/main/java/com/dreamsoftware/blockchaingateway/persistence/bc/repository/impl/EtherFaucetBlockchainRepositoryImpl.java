package com.dreamsoftware.blockchaingateway.persistence.bc.repository.impl;

import com.dreamsoftware.blockchaingateway.contracts.EtherFaucetContract;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.IEtherFaucetBlockchainRepository;
import com.dreamsoftware.blockchaingateway.persistence.bc.core.SupportBlockchainRepository;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.EtherFaucetAuthorityEventEntity;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.mapper.EtherFaucetEventEntityMapper;
import com.dreamsoftware.blockchaingateway.persistence.exception.RepositoryException;
import com.google.common.collect.Lists;
import io.reactivex.Flowable;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

/**
 * @author ssanchez
 */
@Component
@RequiredArgsConstructor
public class EtherFaucetBlockchainRepositoryImpl extends SupportBlockchainRepository implements IEtherFaucetBlockchainRepository {

    private final Logger logger = LoggerFactory.getLogger(EtherFaucetBlockchainRepositoryImpl.class);

    private final EtherFaucetEventEntityMapper etherFaucetEventEntityMapper;

    /**
     * Add Seed Funds
     *
     * @param walletHash
     */
    @Override
    public void addSeedFunds(final String walletHash) throws RepositoryException {
        try {
            final Credentials credentials = walletService.loadCredentials(walletHash);
            logger.debug("EtherFaucetContract address: " + properties.getTrustEtherFaucetContractAddress());
            final EtherFaucetContract etherFaucetContract = EtherFaucetContract.load(properties.getTrustEtherFaucetContractAddress(), web3j, rootTxManager, properties.gas());
            final TransactionReceipt tr = etherFaucetContract.sendSeedFundsTo(credentials.getAddress()).send();
            logger.debug("TransactionReceipt -> " + tr.getBlockHash());
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
    public Flowable<EtherFaucetAuthorityEventEntity> getEvents() throws RepositoryException {
        try {
            final EtherFaucetContract faucetContract = EtherFaucetContract.load(properties.getTrustEtherFaucetContractAddress(),
                    web3j, rootTxManager, properties.gas());
            return Flowable.merge(Lists.newArrayList(
                    faucetContract.onDepositEventFlowable(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST).map(etherFaucetEventEntityMapper::mapEventToEntity),
                    faucetContract.onGetSeedFundsEventFlowable(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST).map(etherFaucetEventEntityMapper::mapEventToEntity)
            ));
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

}
