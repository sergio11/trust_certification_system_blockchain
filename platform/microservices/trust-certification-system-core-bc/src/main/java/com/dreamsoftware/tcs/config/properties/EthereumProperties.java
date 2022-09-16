package com.dreamsoftware.tcs.config.properties;

import org.springframework.context.annotation.Configuration;
import org.web3j.tx.gas.StaticGasProvider;
import java.math.BigInteger;
import javax.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 *
 * @author ssanchez
 */
@Slf4j
@Configuration
@Data
@RefreshScope
public class EthereumProperties {

    /**
     * Chain Id
     */
    @Value("${ethereum.chain-id}")
    private Long chainId;

    /**
     * Wallet Directory
     */
    @Value("${ethereum.wallet-directory}")
    private String walletDirectory;

    /**
     * Root Public Key
     */
    @Value("${ethereum.root-public-key}")
    private String rootPublicKey;

    /**
     * Root Private Key
     */
    @Value("${ethereum.root-private-key}")
    private String rootPrivateKey;

    /**
     * Client Address
     */
    @Value("${ethereum.client-address}")
    private String clientAddress;

    /**
     * Gas Price
     */
    @Value("${ethereum.gas-price}")
    private BigInteger gasPrice;

    /**
     * Gas Limit
     */
    @Value("${ethereum.gas-limit}")
    private BigInteger gasLimit;

    /**
     * Token Management Contract Address
     */
    @Value("${ethereum.token-management-contract-address}")
    private String tokenManagementContractAddress;

    /**
     * Certification Authority Contract Address
     */
    @Value("${ethereum.certification-authority-contract-address}")
    private String certificationAuthorityContractAddress;

    /**
     * Certification Course Contract Address
     */
    @Value("${ethereum.certification-course-contract-address}")
    private String certificationCourseContractAddress;

    /**
     * Trust Certification Contract Address
     */
    @Value("${ethereum.trust-certification-contract-address}")
    private String trustCertificationContractAddress;

    /**
     * Trust Ether Faucet Contract Address
     */
    @Value("${ethereum.trust-ether-faucet-contract-address}")
    private String trustEtherFaucetContractAddress;

    /**
     *
     * @return
     */
    public StaticGasProvider gas() {
        return new StaticGasProvider(gasPrice, gasLimit);
    }

    @PostConstruct
    public void onPostConstruct() {
        log.debug("Wallet Directory: " + walletDirectory);
        log.debug("Root Public Key: " + rootPublicKey);
        log.debug("Root Private Key: " + rootPrivateKey);
        log.debug("Client Address: " + clientAddress);
        log.debug("GAS Price: " + gasPrice);
        log.debug("GAS Limit: " + gasLimit);
        log.debug("Token Management Contract Address: " + tokenManagementContractAddress);
        log.debug("Certification Authority Contract Address: " + certificationAuthorityContractAddress);
        log.debug("Certification Course Contract Address: " + certificationCourseContractAddress);
        log.debug("Trust Certification Contract Address: " + trustCertificationContractAddress);
        log.debug("Trust Ether Faucet Contract Address: " + trustEtherFaucetContractAddress);
    }
}
