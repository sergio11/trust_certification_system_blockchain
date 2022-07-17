package com.dreamsoftware.blockchaingateway.config.properties;

import org.springframework.context.annotation.Configuration;
import org.web3j.tx.gas.StaticGasProvider;
import java.math.BigInteger;
import javax.annotation.PostConstruct;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 *
 * @author ssanchez
 */
@Configuration
@Data
@RefreshScope
public class TrustCertificationSystemProperties {

    private Logger logger = LoggerFactory.getLogger(TrustCertificationSystemProperties.class);

    /**
     * Chain Id
     */
    @Value("${trust.certification.system.chain-id}")
    private Long chainId;

    /**
     * Wallet Directory
     */
    @Value("${trust.certification.system.wallet-directory}")
    private String walletDirectory;

    /**
     * Root Public Key
     */
    @Value("${trust.certification.system.root-public-key}")
    private String rootPublicKey;

    /**
     * Root Private Key
     */
    @Value("${trust.certification.system.root-private-key}")
    private String rootPrivateKey;

    /**
     * Client Address
     */
    @Value("${trust.certification.system.client-address}")
    private String clientAddress;

    /**
     * Gas Price
     */
    @Value("${trust.certification.system.gas-price}")
    private BigInteger gasPrice;

    /**
     * Gas Limit
     */
    @Value("${trust.certification.system.gas-limit}")
    private BigInteger gasLimit;

    /**
     * Token Management Contract Address
     */
    @Value("${trust.certification.system.token-management-contract-address}")
    private String tokenManagementContractAddress;

    /**
     * Certification Authority Contract Address
     */
    @Value("${trust.certification.system.certification-authority-contract-address}")
    private String certificationAuthorityContractAddress;

    /**
     * Certification Course Contract Address
     */
    @Value("${trust.certification.system.certification-course-contract-address}")
    private String certificationCourseContractAddress;

    /**
     * Trust Certification Contract Address
     */
    @Value("${trust.certification.system.trust-certification-contract-address}")
    private String trustCertificationContractAddress;

    /**
     * Trust Ether Faucet Contract Address
     */
    @Value("${trust.certification.system.trust-ether-faucet-contract-address}")
    private String trustEtherFaucetContractAddress;

    public StaticGasProvider gas() {
        return new StaticGasProvider(gasPrice, gasLimit);
    }

    @PostConstruct
    public void onPostConstruct() {
        logger.debug("Wallet Directory: " + walletDirectory);
        logger.debug("Root Public Key: " + rootPublicKey);
        logger.debug("Root Private Key: " + rootPrivateKey);
        logger.debug("Client Address: " + clientAddress);
        logger.debug("GAS Price: " + gasPrice);
        logger.debug("GAS Limit: " + gasLimit);
        logger.debug("Token Management Contract Address: " + tokenManagementContractAddress);
        logger.debug("Certification Authority Contract Address: " + certificationAuthorityContractAddress);
        logger.debug("Certification Course Contract Address: " + certificationCourseContractAddress);
        logger.debug("Trust Certification Contract Address: " + trustCertificationContractAddress);
        logger.debug("Trust Ether Faucet Contract Address: " + trustEtherFaucetContractAddress);
    }
}
