package com.dreamsoftware.blockchaingateway.processor;

import com.dreamsoftware.blockchaingateway.model.CertificationAuthorityRegisteredEvent;
import com.dreamsoftware.blockchaingateway.model.CertificationAuthorityRegistrationRequestEvent;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.ICertificationAuthorityBlockchainRepository;
import com.dreamsoftware.blockchaingateway.persistence.exception.RepositoryException;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Certification Authority Registration Request Processor
 *
 * @author ssanchez
 */
@Component("caRegistrationRequestProcessor")
@RequiredArgsConstructor
public class CARegistrationRequestProcessor implements Function<CertificationAuthorityRegistrationRequestEvent, CertificationAuthorityRegisteredEvent> {

    private Logger logger = LoggerFactory.getLogger(CARegistrationRequestProcessor.class);

    /**
     * Certification Authority Blockchain Repository
     */
    private final ICertificationAuthorityBlockchainRepository certificationAuthorityBlockchainRepository;

    @Override
    public CertificationAuthorityRegisteredEvent apply(CertificationAuthorityRegistrationRequestEvent event) {
        logger.debug("CARegistrationRequestProcessor CALLED!");
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
