package com.dreamsoftware.tcs.persistence.bc.repository.impl;

import com.dreamsoftware.tcs.contracts.ext.TokenManagementContractExt;
import com.dreamsoftware.tcs.exception.LoadWalletException;
import com.dreamsoftware.tcs.persistence.bc.repository.ITokenManagementBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.core.SupportBlockchainRepository;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import java.math.BigInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.web3j.crypto.Credentials;
import org.web3j.tx.FastRawTransactionManager;

/**
 *
 * @author ssanchez
 */
@Component
public class TokenManagementBlockchainRepositoryImpl extends SupportBlockchainRepository implements ITokenManagementBlockchainRepository {

    private final Logger logger = LoggerFactory.getLogger(TokenManagementBlockchainRepositoryImpl.class);

    /**
     * Add Tokens
     *
     * @param walletHash
     * @param tokens
     * @throws RepositoryException
     */
    @Override
    public void addTokens(String walletHash, Long tokens) throws RepositoryException {
        try {
            logger.debug("addTokens address: " + properties.getTokenManagementContractAddress());
            final TokenManagementContractExt tokenManagementContract = loadTokenManagementContract(walletHash);
            final BigInteger tokensPriceInWeis = tokenManagementContract.getTokenPriceInWeis(BigInteger.valueOf(tokens)).send();
            tokenManagementContract.buyTokens(BigInteger.valueOf(tokens), tokensPriceInWeis).send();
        } catch (final Exception ex) {
            logger.debug("Ex Message -> " + ex.getMessage());
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
            return tokenManagementContract.getTokenPriceInWeis(BigInteger.valueOf(tokenCount)).send().longValue();
        } catch (final Exception ex) {
            logger.debug("Ex Message -> " + ex.getMessage());
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
