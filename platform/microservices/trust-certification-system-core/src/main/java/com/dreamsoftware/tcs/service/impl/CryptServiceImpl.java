package com.dreamsoftware.tcs.service.impl;

import com.dreamsoftware.tcs.config.properties.CryptProperties;
import com.dreamsoftware.tcs.exception.CryptException;
import com.dreamsoftware.tcs.service.ICryptService;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteSource;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 *
 * @author ssanchez
 */
@Slf4j
@Service("cryptService")
@RequiredArgsConstructor
public class CryptServiceImpl implements ICryptService {

    private static final String ALGORITHM = "AES";
    private static final String IV_SEPARATOR = "]:";
    private final String transformation = "AES/CFB/PKCS5Padding";

    private final CryptProperties cryptProperties;

    /**
     *
     * @param content
     * @return
     * @throws CryptException
     */
    @Override
    public String encrypt(final String content) throws CryptException {
        Assert.notNull(content, "Content can not be null");
        try {
            final IvParameterSpec ivParameterSpec = generateIv();
            final SecretKey key = getKeyFromPassword(cryptProperties.getPassword(), cryptProperties.getSalt());
            final Cipher cipher = createCipher();
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
            byte[] cipherText = cipher.doFinal(content.getBytes());
            final String ivEncoded = Base64.getEncoder().encodeToString(ivParameterSpec.getIV());
            final String cipherTextEncoded = Base64.getEncoder().encodeToString(cipherText);
            final StringBuilder sb = new StringBuilder();
            sb.append(ivEncoded);
            sb.append(buildSeparator());
            sb.append(cipherTextEncoded);
            return sb.toString();
        } catch (final InvalidAlgorithmParameterException | IOException | InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {
            throw new CryptException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param content
     * @return
     * @throws CryptException
     */
    @Override
    public String decrypt(final String content) throws CryptException {
        Assert.notNull(content, "Content can not be null");
        try {
            final String contentDecoded = new String(Base64.getDecoder().decode(content));
            final String[] contentDecodedChunks = contentDecoded.split(IV_SEPARATOR);
            if (contentDecodedChunks.length != 2) {
                throw new IllegalStateException("Content decoded invalid");
            }
            final IvParameterSpec iv = new IvParameterSpec(contentDecodedChunks[0].getBytes());
            final String cipherText = contentDecodedChunks[1];
            final SecretKey key = getKeyFromPassword(cryptProperties.getPassword(), cryptProperties.getSalt());
            final Cipher cipher = createCipher();
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] plainText = cipher.doFinal(Base64.getDecoder()
                    .decode(cipherText));
            return new String(plainText);
        } catch (final InvalidAlgorithmParameterException | InvalidKeySpecException | NoSuchProviderException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {
            throw new CryptException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @return @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     */
    private Cipher createCipher() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        return Cipher.getInstance(transformation);
    }

    /**
     *
     * @param password
     * @param salt
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private SecretKey getKeyFromPassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        final SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        final KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }

    /**
     *
     * @return
     */
    private IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    /**
     *
     * @return @throws IOException
     */
    private String buildSeparator() throws IOException {
        return ByteSource.wrap(cryptProperties.getSeparator().getBytes()).hash(Hashing.sha256()).toString();
    }

}
