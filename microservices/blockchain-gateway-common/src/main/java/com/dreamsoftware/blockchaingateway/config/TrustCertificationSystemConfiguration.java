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
     * Provide Owner Tx Manager
     *
     * @param web3j
     * @return
     */
    @Bean("ownerTxManager")
    public TransactionManager provideOwnerTxManager(final Web3j web3j) {
        return new ClientTransactionManager(web3j, properties.getOwnerAddress());
    }
}
