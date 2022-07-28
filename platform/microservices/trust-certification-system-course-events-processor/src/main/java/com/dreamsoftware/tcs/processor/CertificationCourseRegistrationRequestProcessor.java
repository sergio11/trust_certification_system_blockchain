package com.dreamsoftware.tcs.processor;

import com.dreamsoftware.tcs.model.events.CertificationCourseRegisteredEvent;
import com.dreamsoftware.tcs.model.events.CourseCertificateRegistrationRequestEvent;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationCourseModelEntity;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.service.ICertificateCourseService;
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

    private final ICertificateCourseService certificateCourseService;

    @Override
    public CertificationCourseRegisteredEvent apply(CourseCertificateRegistrationRequestEvent event) {
        logger.debug("CertificationCourseRegistrationRequestProcessor CALLED!");
        CertificationCourseRegisteredEvent nextEvent = null;
        try {
            final CertificationCourseModelEntity certificationCourseEntity = certificateCourseService.register(event);
            nextEvent = new CertificationCourseRegisteredEvent(certificationCourseEntity, event.getCaWalletHash());
        } catch (final RepositoryException ex) {
            logger.debug("Ex Message -> " + ex.getMessage());
        }
        return nextEvent;
    }
}
