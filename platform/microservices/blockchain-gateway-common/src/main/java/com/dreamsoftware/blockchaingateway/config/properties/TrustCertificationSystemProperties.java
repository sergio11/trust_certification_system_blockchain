package com.dreamsoftware.blockchaingateway.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.web3j.tx.gas.StaticGasProvider;
import java.math.BigInteger;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ssanchez
 */
@Configuration
@ConfigurationProperties(prefix = "trust.certification.system")
@Getter
@Setter
public class TrustCertificationSystemProperties {

    private Logger logger = LoggerFactory.getLogger(TrustCertificationSystemProperties.class);

    private Long chainId;
    private String walletDirectory;
    private String rootPublicKey;
    private String rootPrivateKey;
    private String clientAddress;
    private BigInteger gasPrice;
    private BigInteger gasLimit;
    private String tokenManagementContractAddress;
    private String certificationAuthorityContractAddress;
    private String certificationCourseContractAddress;
    private String trustCertificationContractAddress;
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
