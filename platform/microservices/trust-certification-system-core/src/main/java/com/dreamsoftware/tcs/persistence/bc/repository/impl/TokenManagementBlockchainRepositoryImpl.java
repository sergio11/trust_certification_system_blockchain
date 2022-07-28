package com.dreamsoftware.tcs.persistence.bc.repository.impl;

import com.dreamsoftware.tcs.contracts.TokenManagementContractExt;
import com.dreamsoftware.tcs.persistence.bc.repository.ITokenManagementBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.core.SupportBlockchainRepository;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import java.math.BigInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
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
            final Credentials credentials = walletService.loadCredentials(walletHash);
            final FastRawTransactionManager txManager = new FastRawTransactionManager(web3j, credentials, properties.getChainId());
            final TokenManagementContractExt tokenManagementContract = TokenManagementContractExt.load(properties.getTokenManagementContractAddress(), web3j, txManager, properties.gas());
            final BigInteger tokensPriceInWeis = tokenManagementContract.getTokenPriceInWeis(BigInteger.valueOf(tokens)).send();
            tokenManagementContract.buyTokens(BigInteger.valueOf(tokens), tokensPriceInWeis).send();
        } catch (final Exception ex) {
            logger.debug("Ex Message -> " + ex.getMessage());
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }
}
