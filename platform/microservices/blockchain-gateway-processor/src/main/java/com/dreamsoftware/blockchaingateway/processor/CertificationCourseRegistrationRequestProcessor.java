package com.dreamsoftware.blockchaingateway.processor;

import com.dreamsoftware.blockchaingateway.model.CertificationCourseRegisteredEvent;
import com.dreamsoftware.blockchaingateway.model.CourseCertificateRegistrationRequestEvent;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.ICertificationCourseBlockchainRepository;
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
        return null;
    }
}
