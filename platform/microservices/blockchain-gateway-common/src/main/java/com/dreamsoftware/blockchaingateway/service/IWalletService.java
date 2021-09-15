package com.dreamsoftware.blockchaingateway.service;

import com.dreamsoftware.blockchaingateway.exception.GenerateWalletException;
import com.dreamsoftware.blockchaingateway.exception.LoadWalletException;
import org.web3j.crypto.Credentials;

/**
 *
 * @author ssanchez
 */
public interface IWalletService {

    /**
     * Generate Wallet
     *
     * @return
     * @throws
     * com.dreamsoftware.blockchaingateway.exception.GenerateWalletException
     */
    String generateWallet() throws GenerateWalletException;

    /**
     * Load Credentials
     *
     * @param walletHash
     * @return
     * @throws com.dreamsoftware.blockchaingateway.exception.LoadWalletException
     */
    Credentials loadCredentials(final String walletHash) throws LoadWalletException;

}
