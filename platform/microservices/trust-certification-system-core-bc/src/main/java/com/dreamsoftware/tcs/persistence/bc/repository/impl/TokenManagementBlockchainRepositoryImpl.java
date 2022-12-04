package com.dreamsoftware.tcs.persistence.bc.repository.impl;

import com.dreamsoftware.tcs.contracts.ext.TokenManagementContractExt;
import com.dreamsoftware.tcs.exception.LoadWalletException;
import com.dreamsoftware.tcs.persistence.bc.repository.ITokenManagementBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.core.SupportBlockchainRepository;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import java.math.BigInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.web3j.crypto.Credentials;
import org.web3j.tx.FastRawTransactionManager;

/**
 *
 * @author ssanchez
 */
@Slf4j
@Component
public class TokenManagementBlockchainRepositoryImpl extends SupportBlockchainRepository implements ITokenManagementBlockchainRepository {

    private final long CA_CLIENT_TYPE_VALUE = 0L;
    private final long STUDENT_CLIENT_TYPE_VALUE = 1L;
    private final long ADMIN_CLIENT_TYPE_VALUE = 2L;

    /**
     *
     * @param walletHash
     * @throws RepositoryException
     */
    @Override
    public void configureInitialTokenFundsToStudent(final String walletHash) throws RepositoryException {
        Assert.notNull(walletHash, "walletHash can not be null");
        try {
            log.debug("sendInitialTokenFundsTo address: " + properties.getTokenManagementContractAddress());
            final Credentials credentials = walletService.loadCredentials(walletHash);
            final TokenManagementContractExt tokenManagementContract = loadTokenManagementContract();
            tokenManagementContract.sendInitialTokenFundsTo(credentials.getAddress(), BigInteger.valueOf(STUDENT_CLIENT_TYPE_VALUE)).send();
            final BigInteger clientTokens = tokenManagementContract.getTokens(credentials.getAddress()).send();
            log.debug("sendInitialTokenFundsTo Success! client tokens -> " + clientTokens);
        } catch (final Exception ex) {
            log.debug("Ex Message -> " + ex.getMessage());
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param walletHash
     * @throws RepositoryException
     */
    @Override
    public void configureInitialTokenFundsToCa(final String walletHash) throws RepositoryException {
        Assert.notNull(walletHash, "walletHash can not be null");
        try {
            log.debug("sendInitialTokenFundsTo address: " + properties.getTokenManagementContractAddress());
            final Credentials credentials = walletService.loadCredentials(walletHash);
            final TokenManagementContractExt tokenManagementContract = loadTokenManagementContract();
            tokenManagementContract.sendInitialTokenFundsTo(credentials.getAddress(), BigInteger.valueOf(CA_CLIENT_TYPE_VALUE)).send();
            final BigInteger clientTokens = tokenManagementContract.getTokens(credentials.getAddress()).send();
            log.debug("sendInitialTokenFundsTo Success! client tokens -> " + clientTokens);
        } catch (final Exception ex) {
            log.debug("Ex Message -> " + ex.getMessage());
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param walletHash
     * @throws RepositoryException
     */
    @Override
    public void configureInitialTokenFundsToAdmin(final String walletHash) throws RepositoryException {
        Assert.notNull(walletHash, "walletHash can not be null");
        try {
            log.debug("sendInitialTokenFundsTo address: " + properties.getTokenManagementContractAddress());
            final Credentials credentials = walletService.loadCredentials(walletHash);
            final TokenManagementContractExt tokenManagementContract = loadTokenManagementContract();
            tokenManagementContract.sendInitialTokenFundsTo(credentials.getAddress(), BigInteger.valueOf(ADMIN_CLIENT_TYPE_VALUE)).send();
            final BigInteger clientTokens = tokenManagementContract.getTokens(credentials.getAddress()).send();
            log.debug("sendInitialTokenFundsTo Success! client tokens -> " + clientTokens);
        } catch (final Exception ex) {
            log.debug("Ex Message -> " + ex.getMessage());
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     * Add Tokens
     *
     * @param walletHash
     * @param tokens
     * @throws RepositoryException
     */
    @Override
    public void addTokens(final String walletHash, final Long tokens) throws RepositoryException {
        Assert.notNull(walletHash, "walletHash can not be null");
        Assert.notNull(tokens, "Token can not be null");
        try {
            log.debug("addTokens address: " + properties.getTokenManagementContractAddress());
            final TokenManagementContractExt tokenManagementContract = loadTokenManagementContract(walletHash);
            final BigInteger tokensPriceInWeis = tokenManagementContract.getTokenPriceInWei(BigInteger.valueOf(tokens)).send();
            tokenManagementContract.buyTokens(BigInteger.valueOf(tokens), tokensPriceInWeis).send();
        } catch (final Exception ex) {
            log.debug("Ex Message -> " + ex.getMessage());
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param tokenCount
     * @return
     * @throws com.dreamsoftware.tcs.persistence.exception.RepositoryException
     */
    @Override
    public Long getTokenPriceInWeis(final Long tokenCount) throws RepositoryException {
        Assert.notNull(tokenCount, "Token Count can not be null");
        Assert.isTrue(tokenCount > 0, "Token count should be greather than 0");
        try {
            final TokenManagementContractExt tokenManagementContract = loadTokenManagementContract();
            return tokenManagementContract.getTokenPriceInWei(BigInteger.valueOf(tokenCount)).send().longValue();
        } catch (final Exception ex) {
            log.debug("Ex Message -> " + ex.getMessage());
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     * Get My Tokens
     *
     * @param walletHash
     * @return
     * @throws RepositoryException
     */
    @Override
    public Long getMyTokens(final String walletHash) throws RepositoryException {
        Assert.notNull(walletHash, "Wallet Has can not be null");
        try {
            final TokenManagementContractExt tokenManagementContract = loadTokenManagementContract(walletHash);
            return tokenManagementContract.getMyTokens().send().longValue();
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param walletHash
     * @param clientWalletHash
     * @return
     * @throws RepositoryException
     */
    @Override
    public Long getTokensByClient(final String walletHash, final String clientWalletHash) throws RepositoryException {
        Assert.notNull(walletHash, "Wallet Has can not be null");
        Assert.notNull(clientWalletHash, "Client Wallet Hash can not be null");
        try {
            final TokenManagementContractExt tokenManagementContract = loadTokenManagementContract(walletHash);
            final Credentials clientCredentials = walletService.loadCredentials(clientWalletHash);
            return tokenManagementContract.getTokens(clientCredentials.getAddress()).send().longValue();
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param walletHash
     * @param tokenCount
     * @throws RepositoryException
     */
    @Override
    public void generateTokens(final String walletHash, final Long tokenCount) throws RepositoryException {
        Assert.notNull(walletHash, "Wallet Has can not be null");
        Assert.notNull(tokenCount, "Token Count can not be null");
        try {
            final TokenManagementContractExt tokenManagementContract = loadTokenManagementContract(walletHash);
            tokenManagementContract.generateTokens(BigInteger.valueOf(tokenCount)).send();
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param fromWalletHash
     * @param toWalletHash
     * @param tokenCount
     * @throws RepositoryException
     */
    @Override
    public void transfer(final String fromWalletHash, final String toWalletHash, final Long tokenCount) throws RepositoryException {
        Assert.notNull(fromWalletHash, "fromWalletHash can not be null");
        Assert.notNull(toWalletHash, "toWalletHash can not be null");
        Assert.notNull(tokenCount, "tokenCount can not be null");
        try {
            final TokenManagementContractExt tokenManagementContract = loadTokenManagementContract();
            final Credentials fromCredentials = walletService.loadCredentials(fromWalletHash);
            final Credentials toCredentials = walletService.loadCredentials(toWalletHash);
            tokenManagementContract.transfer(fromCredentials.getAddress(), toCredentials.getAddress(),
                    BigInteger.valueOf(tokenCount));
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     * Load Token Management Contract
     *
     * @param walletHash
     * @return
     * @throws LoadWalletException
     */
    private TokenManagementContractExt loadTokenManagementContract(final String walletHash) throws LoadWalletException {
        final Credentials credentials = walletService.loadCredentials(walletHash);
        final FastRawTransactionManager txManager = new FastRawTransactionManager(web3j, credentials, properties.getChainId());
        return TokenManagementContractExt.load(properties.getTokenManagementContractAddress(),
                web3j, txManager, properties.gas());
    }

    /**
     *
     * @return
     */
    private TokenManagementContractExt loadTokenManagementContract() {
        return TokenManagementContractExt.load(properties.getTokenManagementContractAddress(),
                web3j, rootTxManager, properties.gas());
    }
}
