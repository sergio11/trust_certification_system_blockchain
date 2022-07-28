package com.dreamsoftware.tcs.runners;

import com.dreamsoftware.tcs.config.properties.TrustCertificationSystemProperties;
import com.dreamsoftware.tcs.service.ISftpGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 *
 * @author ssanchez
 */
@Component
@RequiredArgsConstructor
public class SyncKeystoreRunner implements ApplicationRunner {

    private final TrustCertificationSystemProperties trustCertificationSystemProperties;
    private final ISftpGateway sftpGateway;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        sftpGateway.syncRemoteFolderTo(trustCertificationSystemProperties.getWalletDirectory());
    }
}
