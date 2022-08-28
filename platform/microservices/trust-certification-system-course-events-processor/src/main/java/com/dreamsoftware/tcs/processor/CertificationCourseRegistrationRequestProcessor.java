package com.dreamsoftware.tcs.processor;

import com.dreamsoftware.tcs.stream.events.course.CertificationCourseRegisteredEvent;
import com.dreamsoftware.tcs.stream.events.course.CourseCertificateRegistrationRequestEvent;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationCourseModelEntity;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.service.ICertificateCourseService;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Certification Course Registration Request Processor
 *
 * @author ssanchez
 */
@Slf4j
@Component("courseRegistrationRequestProcessor")
@RequiredArgsConstructor
public class CertificationCourseRegistrationRequestProcessor implements Function<CourseCertificateRegistrationRequestEvent, CertificationCourseRegisteredEvent> {

    private final ICertificateCourseService certificateCourseService;

    @Override
    public CertificationCourseRegisteredEvent apply(CourseCertificateRegistrationRequestEvent event) {
        log.debug("CertificationCourseRegistrationRequestProcessor CALLED!");
        CertificationCourseRegisteredEvent nextEvent = null;
        try {
            final CertificationCourseModelEntity certificationCourseEntity = certificateCourseService.register(event);
            nextEvent = new CertificationCourseRegisteredEvent(certificationCourseEntity, event.getCaWalletHash());
        } catch (final RepositoryException ex) {
            log.debug("Ex Message -> " + ex.getMessage());
        }
        return nextEvent;
    }
}
