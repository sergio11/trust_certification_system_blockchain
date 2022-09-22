package com.dreamsoftware.tcs.runners;

import com.dreamsoftware.tcs.config.properties.EthereumProperties;
import com.dreamsoftware.tcs.service.ISftpGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 *
 * @author ssanchez
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SyncKeystoreRunner implements ApplicationRunner {

    private final EthereumProperties trustCertificationSystemProperties;
    private final ISftpGateway sftpGateway;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.debug("SyncKeystoreRunner CALLED!");
        try {
            sftpGateway.syncRemoteFolderTo(trustCertificationSystemProperties.getWalletDirectory());
        } catch (final Exception ex) {
            log.debug("SyncKeystoreRunner ex -> " + ex.getMessage());
        }
    }
}
