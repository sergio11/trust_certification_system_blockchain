package com.dreamsoftware.tcs.persistence.bc.core;

import com.dreamsoftware.tcs.config.properties.EthereumProperties;
import com.dreamsoftware.tcs.service.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.web3j.protocol.Web3j;
import org.web3j.tx.TransactionManager;

/**
 *
 * @author ssanchez
 */
public abstract class SupportBlockchainRepository {

    /**
     * Trust Certification System Properties
     */
    @Autowired
    protected EthereumProperties properties;

    /**
     * Web3j
     */
    @Autowired
    protected Web3j web3j;

    /**
     * Wallet Service
     */
    @Autowired
    protected IWalletService walletService;

    /**
     * Root Tx Manager
     */
    @Autowired
    @Qualifier("rootTxManager")
    protected TransactionManager rootTxManager;

}
