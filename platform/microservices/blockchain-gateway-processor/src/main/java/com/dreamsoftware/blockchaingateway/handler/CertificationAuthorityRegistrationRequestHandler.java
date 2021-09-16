package com.dreamsoftware.blockchaingateway.handler;

import com.dreamsoftware.blockchaingateway.config.properties.TrustCertificationSystemProperties;
import com.dreamsoftware.blockchaingateway.contracts.CertificationAuthorityContract;
import com.dreamsoftware.blockchaingateway.model.CertificationAuthorityRegisteredEvent;
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
import org.web3j.tx.FastRawTransactionManager;
import org.web3j.tx.TransactionManager;

/**
 * Certification Authority Registration Request Handler
 *
 * @author ssanchez
 */
@Service
@RequiredArgsConstructor
public class CertificationAuthorityRegistrationRequestHandler {

    private Logger logger = LoggerFactory.getLogger(CertificationAuthorityRegistrationRequestHandler.class);

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
     * Certification Authority Registered Channel
     *
     * @return
     */
    @Bean
    protected Function<CertificationAuthorityRegistrationRequestEvent, CertificationAuthorityRegisteredEvent> certificationAuthorityRegistrationRequestedChannel() {
        return (CertificationAuthorityRegistrationRequestEvent event) -> onCertificationAuthorityRegistrationRequested(event);
    }

    /**
     * Private Methods
     */
    /**
     * On Certification Authority Registration Requested
     *
     * @param event
     */
    private CertificationAuthorityRegisteredEvent onCertificationAuthorityRegistrationRequested(final CertificationAuthorityRegistrationRequestEvent event) {
        logger.debug("onCertificationAuthorityRegistrationRequested CALLED!");
        CertificationAuthorityRegisteredEvent registeredEvent = null;
        try {
            logger.info("Connected to Ethereum client version: " + web3j.web3ClientVersion().send().getWeb3ClientVersion());
            logger.info("Network Gas Price: " + web3j.ethGasPrice().send().getGasPrice());

            final Credentials credentials = walletService.loadCredentials(event.getWalletHash());
            logger.debug("registerCertificationAuthority address: " + properties.getCertificationAuthorityContractAddress());
            final FastRawTransactionManager txManager = new FastRawTransactionManager(web3j, credentials, properties.getChainId());
            final CertificationAuthorityContract certificationAuthorityContract = CertificationAuthorityContract.load(properties.getCertificationAuthorityContractAddress(),
                    web3j, txManager, properties.gas());
            certificationAuthorityContract.addCertificationAuthority(event.getName(), BigInteger.valueOf(event.getDefaultCostOfIssuingCertificate())).send();
            registeredEvent = new CertificationAuthorityRegisteredEvent(event.getWalletHash());
        } catch (final Exception ex) {
            logger.debug("Ex Message -> " + ex.getMessage());
        }
        return registeredEvent;
    }
}
