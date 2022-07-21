package com.dreamsoftware.blockchaingateway.processor;

import com.dreamsoftware.blockchaingateway.model.CertificationCourseRegisteredEvent;
import com.dreamsoftware.blockchaingateway.model.CourseCertificateRegistrationRequestEvent;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.ICertificationCourseBlockchainRepository;
import com.dreamsoftware.blockchaingateway.persistence.exception.RepositoryException;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Certification Course Registration Request Processor
 *
 * @author ssanchez
 */
@Component("courseRegistrationRequestProcessor")
@RequiredArgsConstructor
public class CertificationCourseRegistrationRequestProcessor implements Function<CourseCertificateRegistrationRequestEvent, CertificationCourseRegisteredEvent> {

    private final Logger logger = LoggerFactory.getLogger(CertificationCourseRegistrationRequestProcessor.class);

    /**
     * Certification Course Blockchain Repository
     */
    private final ICertificationCourseBlockchainRepository certificationCourseBlockchainRepository;

    @Override
    public CertificationCourseRegisteredEvent apply(CourseCertificateRegistrationRequestEvent event) {
        logger.debug("CertificationCourseRegistrationRequestProcessor CALLED!");
        try {
            certificationCourseBlockchainRepository.register(event.getCaWallet(), event.getName(),
                    event.getCostOfIssuingCertificate(), event.getDurationInHours(), event.getExpirationInDays(), event.getCanBeRenewed(), event.getCostOfRenewingCertificate());
        } catch (final RepositoryException ex) {
            logger.debug("Ex Message -> " + ex.getMessage());
        }
        return null;
    }
}
