package com.dreamsoftware.blockchaingateway.handler;

import com.dreamsoftware.blockchaingateway.model.CertificationAuthorityRegisteredEvent;
import io.reactivex.functions.Consumer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import com.dreamsoftware.blockchaingateway.persistence.nosql.repository.UserRepository;

/**
 *
 * @author ssanchez
 */
@Service
@RequiredArgsConstructor
public class CertificationAuthorityRegisteredHandler {

    private Logger logger = LoggerFactory.getLogger(CertificationAuthorityRegisteredHandler.class);

    /**
     * Certification Authority Repository
     */
    private final UserRepository certificationAuthorityRepository;

    @Bean
    protected Consumer<CertificationAuthorityRegisteredEvent> certificationAuthorityyRegisteredEventChannel() {
        return (CertificationAuthorityRegisteredEvent event) -> {
            onCertificationAuthorityRegistered(event);
        };
    }

    /**
     * Private Methods
     */
    /**
     * On Certification Authority Registered
     *
     * @param event
     */
    private void onCertificationAuthorityRegistered(final CertificationAuthorityRegisteredEvent event) {

    }
}
