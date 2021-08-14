package com.dreamsoftware.blockchaingateway.config;

import com.dreamsoftware.blockchaingateway.config.properties.TrustCertificationSystemProperties;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;
import com.dreamsoftware.blockchaingateway.contracts.*;

/**
 *
 * @author ssanchez
 */
@Configuration
@RequiredArgsConstructor
public class TrustCertificationSystemConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(TrustCertificationSystemConfiguration.class);

    private final TrustCertificationSystemProperties properties;

    /**
     * Provide Web3J
     *
     * @return
     */
    @Bean
    public Web3j provideWeb3j() {
        return Web3j.build(new HttpService(properties.getClientAddress(), new OkHttpClient.Builder().build()));
    }

    /**
     * Provide Tx Manager
     *
     * @param web3j
     * @return
     */
    @Bean
    public TransactionManager provideTxManager(final Web3j web3j) {
        return new ClientTransactionManager(web3j, properties.getOwnerAddress());
    }

    /**
     * Provide Token Management Contract
     *
     * @param web3j
     * @param transactionManager
     * @return
     */
    @Bean
    public TokenManagementContract provideTokenManagementContract(final Web3j web3j, final TransactionManager transactionManager) {
        return TokenManagementContract.load(properties.getTokenManagementContractAddress(), web3j, transactionManager,
                properties.gas());
    }

    /**
     * Provide Certification Authority Contract
     *
     * @param web3j
     * @param transactionManager
     * @return
     */
    @Bean
    public CertificationAuthorityContract provideCertificationAuthorityContract(final Web3j web3j, final TransactionManager transactionManager) {
        return CertificationAuthorityContract.load(properties.getCertificationAuthorityContractAddress(), web3j, transactionManager,
                properties.gas());
    }

    /**
     * Provide Certification Course Contract
     *
     * @param web3j
     * @param transactionManager
     * @return
     */
    @Bean
    public CertificationCourseContract provideCertificationCourseContract(final Web3j web3j, final TransactionManager transactionManager) {
        return CertificationCourseContract.load(properties.getCertificationAuthorityContractAddress(), web3j, transactionManager,
                properties.gas());
    }

    /**
     * Provide Trust Certification Contract
     *
     * @param web3j
     * @param transactionManager
     * @return
     */
    @Bean
    public TrustCertificationContract provideTrustCertificationContract(final Web3j web3j, final TransactionManager transactionManager) {
        return TrustCertificationContract.load(properties.getCertificationAuthorityContractAddress(), web3j, transactionManager,
                properties.gas());
    }
}
