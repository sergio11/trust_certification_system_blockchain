package com.dreamsoftware.blockchaingateway.service.impl;

import com.dreamsoftware.blockchaingateway.exception.GenerateWalletException;
import com.dreamsoftware.blockchaingateway.exception.LoadWalletException;
import com.dreamsoftware.blockchaingateway.model.Wallet;
import com.dreamsoftware.blockchaingateway.service.IWalletService;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponseSupport;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.utils.Files;

/**
 *
 * @author ssanchez
 */
@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements IWalletService {

    /**
     * Vault Template
     */
    private final VaultTemplate vaultTemplate;

    @Override
    public String generateWallet(String secret) throws GenerateWalletException {
        try {
            final String directory = System.getProperty("java.io.tmpdir");
            // Generate new wallet file
            final String fileName = WalletUtils.generateNewWalletFile(secret, new File(directory));
            final File walletFile = new File(directory, fileName);
            final String walletHash = getFileHash(walletFile);
            // Write into Vault the Wallet information
            vaultTemplate.write("tcs-v1/wallets/" + walletHash, new Wallet(fileName, secret, Files.readString(walletFile)));
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
            final VaultResponseSupport<Wallet> response = vaultTemplate.read("tcs-v1/wallets/" + walletHash, Wallet.class);
            final Wallet wallet = response.getData();
            final File walletFile = saveIntoTmpFile(wallet.getName(), wallet.getContent());
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

    private File saveIntoTmpFile(final String name, final String content) throws IOException {

        final File tmpFile = new File(System.getProperty("java.io.tmpdir"), name);
        if (!tmpFile.exists()) {
            tmpFile.createNewFile();
        }
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(tmpFile), "utf-8"))) {
            writer.write(content);
        }
        return tmpFile;
    }
}
