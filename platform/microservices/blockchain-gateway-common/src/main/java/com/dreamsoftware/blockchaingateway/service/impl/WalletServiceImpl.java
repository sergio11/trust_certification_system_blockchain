package com.dreamsoftware.blockchaingateway.service.impl;

import com.dreamsoftware.blockchaingateway.config.properties.TrustCertificationSystemProperties;
import com.dreamsoftware.blockchaingateway.exception.GenerateWalletException;
import com.dreamsoftware.blockchaingateway.exception.LoadWalletException;
import com.dreamsoftware.blockchaingateway.model.WalletCredentials;
import com.dreamsoftware.blockchaingateway.service.IWalletService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponseSupport;
import org.web3j.crypto.Bip39Wallet;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

/**
 *
 * @author ssanchez
 */
@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements IWalletService {

    private Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);

    /**
     * Vault Template
     */
    private final VaultTemplate vaultTemplate;

    /**
     * Trust Certification System Properties
     */
    private final TrustCertificationSystemProperties trustCertificationSystemProperties;

    @Override
    public String generateWallet(String secret) throws GenerateWalletException {
        try {
            final Bip39Wallet bip39Wallet = WalletUtils.generateBip39Wallet(secret, new File(trustCertificationSystemProperties.getWalletDirectory()));
            final File walletFile = new File(trustCertificationSystemProperties.getWalletDirectory(),
                    bip39Wallet.getFilename());
            final String walletHash = getFileHash(walletFile);
            // Write into Vault the WalletCredentials information
            vaultTemplate.write("tcs-v1/wallets/" + walletHash, new WalletCredentials(bip39Wallet.getFilename(), secret, bip39Wallet.getMnemonic()));
            // Return file SHA Hash
            return walletHash;
        } catch (final Exception ex) {
            throw new GenerateWalletException(ex.getMessage(), ex);
        }
    }

    /**
     * Load Credentials
     *
     * @param walletHash
     * @return
     */
    @Override
    public Credentials loadCredentials(String walletHash) throws LoadWalletException {
        Assert.notNull(walletHash, "Wallet Hash can not be null");
        try {
            final VaultResponseSupport<WalletCredentials> response = vaultTemplate.read("tcs-v1/wallets/" + walletHash, WalletCredentials.class);
            final WalletCredentials wallet = response.getData();
            logger.debug("Load Credentials for wallet: " + wallet.getName());
            final File walletFile = new File(trustCertificationSystemProperties.getWalletDirectory(),
                    wallet.getName());
            return WalletUtils.loadCredentials(wallet.getSecret(), walletFile);
        } catch (final Exception ex) {
            throw new LoadWalletException(ex.getMessage(), ex);
        }
    }

    /**
     * Private Methods
     */
    private String getFileHash(File file) throws IOException, NoSuchAlgorithmException {
        //Use SHA-1 algorithm
        MessageDigest shaDigest = MessageDigest.getInstance("SHA-256");
        try ( //Get file input stream for reading the file content
                FileInputStream fis = new FileInputStream(file)) {
            //Create byte array to read data in chunks
            byte[] byteArray = new byte[1024];
            int bytesCount;
            //Read file data and update in message digest
            while ((bytesCount = fis.read(byteArray)) != -1) {
                shaDigest.update(byteArray, 0, bytesCount);
            }
            //close the stream; We don't need it now.
        }
        //Get the hash's bytes
        byte[] bytes = shaDigest.digest();

        //This bytes[] has bytes in decimal format;
        //Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        //return complete hash
        return sb.toString();
    }
}
