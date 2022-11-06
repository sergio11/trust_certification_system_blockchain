package com.dreamsoftware.tcs.service.impl;

import com.dreamsoftware.tcs.config.properties.EthereumProperties;
import com.dreamsoftware.tcs.exception.GenerateWalletException;
import com.dreamsoftware.tcs.exception.LoadWalletException;
import com.dreamsoftware.tcs.exception.SaveWalletException;
import com.dreamsoftware.tcs.model.WalletCredentials;
import com.dreamsoftware.tcs.service.ISftpGateway;
import com.dreamsoftware.tcs.service.IWalletService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
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

    private final Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);

    /**
     * Vault Template
     */
    private final VaultTemplate vaultTemplate;

    /**
     * SFTP Gateway
     */
    private final ISftpGateway sftpGateway;

    /**
     * Trust Certification System Properties
     */
    private final EthereumProperties trustCertificationSystemProperties;

    /**
     * Save Wallet
     *
     * @param fileName
     * @param secret
     * @param mnemonic
     * @return
     * @throws SaveWalletException
     */
    @Override
    public String saveWallet(final String fileName, final String secret, final String mnemonic) throws SaveWalletException {
        try {
            return saveWalletFile(fileName, secret, mnemonic);
        } catch (final Exception ex) {
            throw new SaveWalletException(ex.getMessage(), ex);
        }
    }

    /**
     * Generate Wallet
     *
     * @return
     * @throws GenerateWalletException
     */
    @Override
    public String generateWallet() throws GenerateWalletException {
        try {
            final String walletSecret = generateWalletSecret();
            final Bip39Wallet bip39Wallet = WalletUtils.generateBip39Wallet(walletSecret, new File(trustCertificationSystemProperties.getWalletDirectory()));
            // Return file SHA Hash
            return saveWalletFile(bip39Wallet.getFilename(), walletSecret, bip39Wallet.getMnemonic());
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
    /**
     * Save Wallet File
     * @param walletFileName
     * @param walletSecret
     * @param walletMnemonic
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    private String saveWalletFile(final String walletFileName, final String walletSecret, final String walletMnemonic) throws IOException, NoSuchAlgorithmException {
        final File walletFile = new File(trustCertificationSystemProperties.getWalletDirectory(),
                walletFileName);

        sftpGateway.sendFile(walletFile);

        final String walletHash = getFileHash(walletFile);
        // Write into Vault the WalletCredentials information
        vaultTemplate.write("tcs-v1/wallets/" + walletHash, new WalletCredentials(walletFileName, walletSecret, walletMnemonic));
        // Return file SHA Hash
        return walletHash;
    }

    private String generateWalletSecret() {
        String upperCaseLetters = RandomStringUtils.random(2, 65, 90, true, true);
        String lowerCaseLetters = RandomStringUtils.random(2, 97, 122, true, true);
        String numbers = RandomStringUtils.randomNumeric(2);
        String specialChar = RandomStringUtils.random(2, 33, 47, false, false);
        String totalChars = RandomStringUtils.randomAlphanumeric(2);
        String combinedChars = upperCaseLetters.concat(lowerCaseLetters)
                .concat(numbers)
                .concat(specialChar)
                .concat(totalChars);
        List<Character> pwdChars = combinedChars.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(pwdChars);
        String password = pwdChars.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
        return password;
    }

    /**
     * Generate File Hash
     *
     * @param file
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
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
