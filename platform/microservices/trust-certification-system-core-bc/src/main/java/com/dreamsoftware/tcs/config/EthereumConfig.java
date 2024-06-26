package com.dreamsoftware.tcs.config;

import com.dreamsoftware.tcs.config.properties.EthereumProperties;
import java.math.BigInteger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.FastRawTransactionManager;
import org.web3j.tx.TransactionManager;

/**
 *
 * @author ssanchez
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class EthereumConfig {

    private final EthereumProperties properties;

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
     * Provide Root Tx Manager
     *
     * @param web3j
     * @return
     */
    @Bean("rootTxManager")
    public TransactionManager provideRootTxManager(final Web3j web3j) {
        BigInteger privateKeyInBT = new BigInteger(properties.getRootPrivateKey(), 16);
        final ECKeyPair aPair = ECKeyPair.create(privateKeyInBT);
        final Credentials rootCredentials = Credentials.create(aPair);
        BigInteger publicKeyInBT = aPair.getPublicKey();
        String sPublickeyInHex = publicKeyInBT.toString(16);
        log.debug("Root Public Key: " + sPublickeyInHex);
        return new FastRawTransactionManager(web3j, rootCredentials, properties.getChainId());
    }
}
