package com.dreamsoftware.blockchaingateway.processor;

import com.dreamsoftware.blockchaingateway.model.CertificationAuthorityRegisteredEvent;
import com.dreamsoftware.blockchaingateway.model.CertificationAuthorityRegistrationRequestEvent;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.ICertificationAuthorityBlockchainRepository;
import com.dreamsoftware.blockchaingateway.persistence.exception.RepositoryException;
import com.dreamsoftware.blockchaingateway.persistence.nosql.repository.UserRepository;
import java.util.function.Consumer;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Certification Authority Registered Processor
 *
 * @author ssanchez
 */
@Component("caRegisteredProcessor")
@RequiredArgsConstructor
public class CARegisteredProcessor implements Consumer<CertificationAuthorityRegisteredEvent> {

    private Logger logger = LoggerFactory.getLogger(CARegisteredProcessor.class);

    /**
     * Certification Authority Repository
     */
    private final UserRepository certificationAuthorityRepository;

    @Override
    public void accept(CertificationAuthorityRegisteredEvent event) {
        logger.debug("CARegisteredProcessor CALLED!");
    }
}
