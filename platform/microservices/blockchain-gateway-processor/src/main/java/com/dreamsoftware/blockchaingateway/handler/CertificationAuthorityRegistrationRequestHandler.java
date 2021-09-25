package com.dreamsoftware.blockchaingateway.handler;

import com.dreamsoftware.blockchaingateway.model.CertificationAuthorityRegisteredEvent;
import com.dreamsoftware.blockchaingateway.model.CertificationAuthorityRegistrationRequestEvent;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.ICertificationAuthorityBlockchainRepository;
import com.dreamsoftware.blockchaingateway.persistence.exception.RepositoryException;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * Certification Authority Registration Request Handler
 *
 * @author ssanchez
 */
@Service
@RequiredArgsConstructor
public class CertificationAuthorityRegistrationRequestHandler {

    private Logger logger = LoggerFactory.getLogger(CertificationAuthorityRegistrationRequestHandler.class);

    /**
     * Certification Authority Blockchain Repository
     */
    private final ICertificationAuthorityBlockchainRepository certificationAuthorityBlockchainRepository;

    /**
     * Certification Authority Registered Channel
     *
     * @return
     */
    @Bean
    public Function<CertificationAuthorityRegistrationRequestEvent, CertificationAuthorityRegisteredEvent> certificationAuthorityRegistrationRequestedChannel() {
        return (CertificationAuthorityRegistrationRequestEvent event) -> onCertificationAuthorityRegistrationRequested(event);
    }

    /**
     * Private Methods
     */
    /**
     * On Certification Authority Registration Requested
     *
     * @param event
     */
    private CertificationAuthorityRegisteredEvent onCertificationAuthorityRegistrationRequested(final CertificationAuthorityRegistrationRequestEvent event) {
        logger.debug("onCertificationAuthorityRegistrationRequested CALLED!");
        CertificationAuthorityRegisteredEvent registeredEvent = null;
        try {
            certificationAuthorityBlockchainRepository.register(event.getName(), event.getDefaultCostOfIssuingCertificate(),
                    event.getWalletHash());
            registeredEvent = new CertificationAuthorityRegisteredEvent(event.getWalletHash());
        } catch (final RepositoryException ex) {
            logger.debug("Ex Message -> " + ex.getMessage());
        }
        return registeredEvent;
    }
}
