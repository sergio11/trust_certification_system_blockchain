package com.dreamsoftware.blockchaingateway.handler;

import com.dreamsoftware.blockchaingateway.config.properties.TrustCertificationSystemProperties;
import com.dreamsoftware.blockchaingateway.contracts.EtherFaucetContract;
import com.dreamsoftware.blockchaingateway.contracts.TokenManagementContractExt;
import com.dreamsoftware.blockchaingateway.model.CertificationAuthorityInitialFundsRequestEvent;
import com.dreamsoftware.blockchaingateway.model.CertificationAuthorityRegistrationRequestEvent;
import com.dreamsoftware.blockchaingateway.service.IWalletService;
import java.math.BigInteger;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.FastRawTransactionManager;
import org.web3j.tx.TransactionManager;

/**
 * Certification Authority Initial Funds Request Handler
 *
 * @author ssanchez
 */
@Service
@RequiredArgsConstructor
public class CertificationAuthorityInitialFundsRequestHandler {

    private Logger logger = LoggerFactory.getLogger(CertificationAuthorityInitialFundsRequestHandler.class);

    /**
     * Constants
     */
    private final static Long DEFAULT_CERTIFICATION_AUTHORITY_TOKENS = 20L;

    /**
     * Wallet Service
     */
    private final IWalletService walletService;

    /**
     * Trust Certification System Properties
     */
    private final TrustCertificationSystemProperties properties;

    /**
     * Web3j
     */
    private final Web3j web3j;

    /**
     * Root Tx Manager
     */
    @Qualifier("rootTxManager")
    private final TransactionManager rootTxManager;

    /**
     * Certification Authority Initial Funds Request Channel
     *
     * @return
     */
    @Bean
    protected Function<CertificationAuthorityInitialFundsRequestEvent, CertificationAuthorityRegistrationRequestEvent> certificationAuthorityInitialFunds() {
        return (CertificationAuthorityInitialFundsRequestEvent event) -> onCertificationAuthorityInitialFundsRequested(event);
    }

    /**
     * Private Methods
     */
    /**
     * On Certification Authority Initial Funds Requested
     *
     * @param event
     */
    private CertificationAuthorityRegistrationRequestEvent onCertificationAuthorityInitialFundsRequested(final CertificationAuthorityInitialFundsRequestEvent event) {
        logger.debug("onCertificationAuthorityRegistered CALLED!");
        CertificationAuthorityRegistrationRequestEvent registrationRequest = null;
        try {
            logger.info("Connected to Ethereum client version: " + web3j.web3ClientVersion().send().getWeb3ClientVersion());
            logger.info("Network Gas Price: " + web3j.ethGasPrice().send().getGasPrice());
            final Credentials credentials = walletService.loadCredentials(event.getWalletHash());
            // Add Seed Funds
            addSeedFundsToCertificationAuthority(credentials);
            // Buy Tokens
            buyTokensToCertificationAuthority(credentials);
            registrationRequest = new CertificationAuthorityRegistrationRequestEvent(
                    event.getName(), event.getDefaultCostOfIssuingCertificate(), event.getWalletHash());
        } catch (final Exception ex) {
            logger.debug("Ex Message -> " + ex.getMessage());
        }
        return registrationRequest;
    }

    /**
     * Add Seed Funds To Certification Authority
     *
     * @param credentials
     * @return
     */
    private void addSeedFundsToCertificationAuthority(final Credentials credentials) {
        try {
            logger.debug("EtherFaucetContract address: " + properties.getTrustEtherFaucetContractAddress());
            final EtherFaucetContract etherFaucetContract = EtherFaucetContract.load(properties.getTrustEtherFaucetContractAddress(), web3j, rootTxManager, properties.gas());
            final TransactionReceipt tr = etherFaucetContract.sendSeedFundsTo(credentials.getAddress()).send();
            logger.debug("TransactionReceipt -> " + tr.getBlockHash());
        } catch (final Exception ex) {
            logger.debug("Ex Message -> " + ex.getMessage());
        }
    }

    /**
     * Buy Tokens To Certification Authority
     *
     * @param credentials
     * @return
     */
    private void buyTokensToCertificationAuthority(final Credentials credentials) {
        try {
            logger.debug("buyTokensToCertificationAuthority address: " + properties.getTokenManagementContractAddress());
            final FastRawTransactionManager txManager = new FastRawTransactionManager(web3j, credentials, properties.getChainId());
            final TokenManagementContractExt tokenManagementContract = TokenManagementContractExt.load(properties.getTokenManagementContractAddress(), web3j, txManager, properties.gas());
            final BigInteger tokensPriceInWeis = tokenManagementContract.getTokenPriceInWeis(BigInteger.valueOf(DEFAULT_CERTIFICATION_AUTHORITY_TOKENS)).send();
            tokenManagementContract.buyTokens(BigInteger.valueOf(DEFAULT_CERTIFICATION_AUTHORITY_TOKENS), tokensPriceInWeis).send();
        } catch (final Exception ex) {
            logger.debug("Ex Message -> " + ex.getMessage());
        }
    }

}
