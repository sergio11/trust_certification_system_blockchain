package com.dreamsoftware.blockchaingateway.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.web3j.tx.gas.StaticGasProvider;
import java.math.BigInteger;

/**
 *
 * @author ssanchez
 */
@Configuration
@ConfigurationProperties(prefix = "trust.certification.system.contracts")
@Getter
@Setter
public class TrustCertificationSystemProperties {

    private String ownerAddress;
    private String clientAddress;
    private BigInteger gasPrice;
    private BigInteger gasLimit;
    private String tokenManagementContractAddress;
    private String certificationAuthorityContractAddress;
    private String certificationCourseContractAddress;
    private String trustCertificationContractAddress;

    public StaticGasProvider gas() {
        return new StaticGasProvider(gasPrice, gasLimit);
    }
}
