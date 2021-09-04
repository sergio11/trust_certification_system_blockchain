package com.dreamsoftware.blockchaingateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.vault.config.EnvironmentVaultConfiguration;

/**
 *
 * @author ssanchez
 */
@Configuration
@PropertySource(value = {"vault-config.properties"})
@Import(value = EnvironmentVaultConfiguration.class)
public class VaultEnvironmentConfig {

}
