package com.dreamsoftware.tcs.service;

import com.dreamsoftware.tcs.exception.GenerateWalletException;
import com.dreamsoftware.tcs.exception.LoadWalletException;
import com.dreamsoftware.tcs.exception.SaveWalletException;
import org.web3j.crypto.Credentials;

/**
 *
 * @author ssanchez
 */
public interface IWalletService {

    /**
     * Save Wallet
     *
     * @param fileName
     * @param secret
     * @param mnemonic
     * @return
     * @throws com.dreamsoftware.tcs.exception.SaveWalletException
     */
    String saveWallet(final String fileName, final String secret, final String mnemonic) throws SaveWalletException;

    /**
     * Generate Wallet
     *
     * @return
     * @throws
     * com.dreamsoftware.tcs.exception.GenerateWalletException
     */
    String generateWallet() throws GenerateWalletException;

    /**
     * Load Credentials
     *
     * @param walletHash
     * @return
     * @throws com.dreamsoftware.tcs.exception.LoadWalletException
     */
    Credentials loadCredentials(final String walletHash) throws LoadWalletException;

}
