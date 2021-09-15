package com.dreamsoftware.blockchaingateway.services.impl;

import com.dreamsoftware.blockchaingateway.config.properties.TrustCertificationSystemProperties;
import com.dreamsoftware.blockchaingateway.contracts.CertificationAuthorityContract;
import com.dreamsoftware.blockchaingateway.contracts.EtherFaucetContract;
import com.dreamsoftware.blockchaingateway.contracts.TokenManagementContractExt;
import com.dreamsoftware.blockchaingateway.persistence.entity.CertificationAuthorityEntity;
import com.dreamsoftware.blockchaingateway.persistence.repository.CertificationAuthorityRepository;
import com.dreamsoftware.blockchaingateway.service.IWalletService;
import com.dreamsoftware.blockchaingateway.services.ICertificationAuthorityService;
import com.dreamsoftware.blockchaingateway.web.dto.request.RegisterCertificationAuthorityDTO;
import io.reactivex.Flowable;
import java.math.BigInteger;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.TransactionManager;
import com.dreamsoftware.blockchaingateway.service.IPasswordHashingService;
import com.dreamsoftware.blockchaingateway.web.controller.error.exception.RegisterCertificationAuthorityException;
import org.web3j.tx.FastRawTransactionManager;

/**
 *
 * @author ssanchez
 */
@Service
@RequiredArgsConstructor
public class CertificationAuthorityServiceImpl implements ICertificationAuthorityService {

    private Logger logger = LoggerFactory.getLogger(CertificationAuthorityServiceImpl.class);

    /**
     * Constants
     */
    private final static Long DEFAULT_CERTIFICATION_AUTHORITY_TOKENS = 20L;

    /**
     * Wallet Service
     */
    private final IWalletService walletService;

    /**
     * Hash Service
     */
    private final IPasswordHashingService hashService;

    /**
     * Certification Authority Repository
     */
    private final CertificationAuthorityRepository certificationAuthorityRepository;

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
     * Register Certification Authority
     *
     * @param registerCertificationAuthorityDTO
     */
    @Override
    public void register(final RegisterCertificationAuthorityDTO registerCertificationAuthorityDTO) {
        try {

            logger.info("Connected to Ethereum client version: " + web3j.web3ClientVersion().send().getWeb3ClientVersion());
            logger.info("Network Gas Price: " + web3j.ethGasPrice().send().getGasPrice());

            // Generate Wallet
            final String walletHash = walletService.generateWallet();
            logger.debug("Wallet created with hash: " + walletHash);
            final String secretHash = hashService.hash(registerCertificationAuthorityDTO.getPassword());
            final CertificationAuthorityEntity certificationAuthority = new CertificationAuthorityEntity();
            certificationAuthority.setName(registerCertificationAuthorityDTO.getName());
            certificationAuthority.setPasswordHash(secretHash);
            certificationAuthority.setWalletHash(walletHash);

            final CertificationAuthorityEntity certificationAuthoritySaved = certificationAuthorityRepository.save(certificationAuthority);
            final Credentials credentials = walletService.loadCredentials(walletHash);

            logger.debug("Wallet public key: " + credentials.getAddress());

            createCertificationAuthorityAccount(credentials, certificationAuthoritySaved.getName(), registerCertificationAuthorityDTO.getDefaultCostOfIssuingCertificate()).subscribe(new Subscriber<TransactionReceipt>() {
                @Override
                public void onSubscribe(Subscription s) {
                    logger.debug("onSubscribe CALLED");
                }

                @Override
                public void onNext(TransactionReceipt t) {
                    logger.debug("onNext " + t.getBlockHash() + " CALLED");
                }

                @Override
                public void onError(Throwable thrwbl) {
                    logger.debug("thrwbl " + thrwbl.getMessage() + " CALLED");
                }

                @Override
                public void onComplete() {
                    logger.debug("onComplete CALLED");
                }

            });
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("exception ocurred " + ex.getMessage() + " CALLED");
            throw new RegisterCertificationAuthorityException(ex.getMessage(), ex);
        }
    }

    /**
     * Private Methods
     */
    private Flowable<TransactionReceipt> createCertificationAuthorityAccount(final Credentials credentials, final String name, final Long defaultCostOfIssuingCertificate) {
        return addSeedFundsToCertificationAuthority(credentials)
                .flatMap(txr -> buyTokensToCertificationAuthority(credentials))
                .flatMap(txr -> registerCertificationAuthority(credentials, name, defaultCostOfIssuingCertificate));
    }

    /**
     * Add Seed Funds To Certification Authority
     *
     * @param credentials
     * @return
     */
    private Flowable<TransactionReceipt> addSeedFundsToCertificationAuthority(final Credentials credentials) {
        logger.debug("EtherFaucetContract address: " + properties.getTrustEtherFaucetContractAddress());
        final EtherFaucetContract etherFaucetContract = EtherFaucetContract.load(properties.getTrustEtherFaucetContractAddress(), web3j, rootTxManager, properties.gas());
        return etherFaucetContract.sendSeedFundsTo(credentials.getAddress()).flowable();
    }

    /**
     * Buy Tokens To Certification Authority
     *
     * @param credentials
     * @return
     */
    private Flowable<TransactionReceipt> buyTokensToCertificationAuthority(final Credentials credentials) {
        logger.debug("buyTokensToCertificationAuthority address: " + properties.getTokenManagementContractAddress());
        final FastRawTransactionManager txManager = new FastRawTransactionManager(web3j, credentials, properties.getChainId());
        final TokenManagementContractExt tokenManagementContract = TokenManagementContractExt.load(properties.getTokenManagementContractAddress(), web3j, txManager, properties.gas());
        return tokenManagementContract.getTokenPriceInWeis(BigInteger.valueOf(DEFAULT_CERTIFICATION_AUTHORITY_TOKENS)).flowable()
                .flatMap((BigInteger tokensPriceInWeis) -> tokenManagementContract.buyTokens(BigInteger.valueOf(DEFAULT_CERTIFICATION_AUTHORITY_TOKENS), tokensPriceInWeis).flowable());
    }

    /**
     * Register Certification Authority
     *
     * @param credentials
     * @param name
     * @param defaultCostOfIssuingCertificate
     * @return
     */
    private Flowable<TransactionReceipt> registerCertificationAuthority(final Credentials credentials, final String name, final Long defaultCostOfIssuingCertificate) {
        logger.debug("registerCertificationAuthority address: " + properties.getCertificationAuthorityContractAddress());
        final FastRawTransactionManager txManager = new FastRawTransactionManager(web3j, credentials, properties.getChainId());
        final CertificationAuthorityContract certificationAuthorityContract = CertificationAuthorityContract.load(properties.getCertificationAuthorityContractAddress(), web3j, txManager, properties.gas());
        return certificationAuthorityContract.addCertificationAuthority(name, BigInteger.valueOf(defaultCostOfIssuingCertificate)).flowable();
    }

}
