package com.dreamsoftware.tcs.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.vault.config.EnvironmentVaultConfiguration;

/**
 *
 * @author ssanchez
 */
@Configuration
@Data
@Import(value = EnvironmentVaultConfiguration.class)
public class VaultConfig {

    // Vault Wallet Path
    @Value("${vault.wallet-path}")
    private String walletPath;

}
