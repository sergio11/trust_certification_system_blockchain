package com.dreamsoftware.blockchaingateway.services.impl;

import com.dreamsoftware.blockchaingateway.config.properties.TrustCertificationSystemProperties;
import com.dreamsoftware.blockchaingateway.contracts.CertificationAuthorityContract;
import com.dreamsoftware.blockchaingateway.contracts.EtherFaucetContract;
import com.dreamsoftware.blockchaingateway.contracts.TokenManagementContract;
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
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;
import com.dreamsoftware.blockchaingateway.service.IPasswordHashingService;

/**
 *
 * @author ssanchez
 */
@Service
@RequiredArgsConstructor
public class CertificationAuthorityServiceImpl implements ICertificationAuthorityService {

    private Logger logger = LoggerFactory.getLogger(CertificationAuthorityServiceImpl.class);

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
     * Owner Tx Manager
     */
    @Qualifier("ownerTxManager")
    private final TransactionManager ownerTxManager;

    /**
     * Register Certification Authority
     *
     * @param registerCertificationAuthorityDTO
     */
    @Override
    public void register(final RegisterCertificationAuthorityDTO registerCertificationAuthorityDTO) {
        try {

            // Generate Wallet
            final String walletHash = walletService.generateWallet(registerCertificationAuthorityDTO.getSecret());
            logger.debug("Wallet created with hash: " + walletHash);
            final String secretHash = hashService.hash(registerCertificationAuthorityDTO.getSecret());
            final CertificationAuthorityEntity certificationAuthority = new CertificationAuthorityEntity();
            certificationAuthority.setName(registerCertificationAuthorityDTO.getName());
            certificationAuthority.setSecretHash(secretHash);
            certificationAuthority.setWalletHash(walletHash);
            final CertificationAuthorityEntity certificationAuthoritySaved = certificationAuthorityRepository.save(certificationAuthority);
            final Credentials credentials = walletService.loadCredentials(walletHash);
            createCertificationAuthorityAccount(credentials, certificationAuthoritySaved.getName(), registerCertificationAuthorityDTO.getDefaultCostOfIssuingCertificate()).subscribe(new Subscriber<TransactionReceipt>() {
                @Override
                public void onSubscribe(Subscription s) {
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
        }
    }

    /**
     * Private Methods
     */
    private Flowable<TransactionReceipt> createCertificationAuthorityAccount(final Credentials credentials, final String name, final Long defaultCostOfIssuingCertificate) {
        return addSeedFundsToCertificationAuthority(credentials)
                .flatMap(txr -> buyTokensToCertificationAuthority(credentials))
                .flatMap(txr -> registerCertificationAuthority(name, defaultCostOfIssuingCertificate));
    }

    /**
     * Add Seed Funds To Certification Authority
     *
     * @param credentials
     * @return
     */
    private Flowable<TransactionReceipt> addSeedFundsToCertificationAuthority(final Credentials credentials) {
        logger.debug("EtherFaucetContract address: " + properties.getTrustEtherFaucetContractAddress());
        final ClientTransactionManager clientTxManager = new ClientTransactionManager(web3j, credentials.getAddress());
        final EtherFaucetContract etherFaucetContract = EtherFaucetContract.load(properties.getTrustEtherFaucetContractAddress(), web3j, clientTxManager, properties.gas());
        return etherFaucetContract.getSeedFunds().flowable();
    }

    /**
     * Buy Tokens To Certification Authority
     *
     * @param credentials
     * @return
     */
    private Flowable<TransactionReceipt> buyTokensToCertificationAuthority(final Credentials credentials) {
        final ClientTransactionManager clientTxManager = new ClientTransactionManager(web3j, credentials.getAddress());
        final TokenManagementContract tokenManagementContract = TokenManagementContract.load(properties.getTokenManagementContractAddress(), web3j, clientTxManager, properties.gas());
        return tokenManagementContract.buyTokens(BigInteger.TEN).flowable();
    }

    /**
     * Register Certification Authority
     *
     * @param name
     * @param defaultCostOfIssuingCertificate
     * @return
     */
    private Flowable<TransactionReceipt> registerCertificationAuthority(final String name, final Long defaultCostOfIssuingCertificate) {
        final CertificationAuthorityContract certificationAuthorityContract = CertificationAuthorityContract.load(properties.getCertificationAuthorityContractAddress(), web3j, ownerTxManager, properties.gas());
        return certificationAuthorityContract.addCertificationAuthority(name, BigInteger.valueOf(defaultCostOfIssuingCertificate)).flowable();
    }

}
