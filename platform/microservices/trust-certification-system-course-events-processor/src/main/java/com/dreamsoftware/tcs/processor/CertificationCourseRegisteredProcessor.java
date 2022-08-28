package com.dreamsoftware.tcs.processor;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEntity;
import com.dreamsoftware.tcs.stream.events.course.CertificationCourseRegisteredEvent;
import com.dreamsoftware.tcs.service.ICertificateCourseService;
import com.dreamsoftware.tcs.stream.events.notifications.course.CertificationCourseRegisteredNotificationEvent;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Certification Course Registered Processor
 *
 * @author ssanchez
 */
@Slf4j
@Component("courseRegisteredProcessor")
@RequiredArgsConstructor
public class CertificationCourseRegisteredProcessor implements Function<CertificationCourseRegisteredEvent, CertificationCourseRegisteredNotificationEvent> {

    private final ICertificateCourseService certificateCourseService;

    @Override
    public CertificationCourseRegisteredNotificationEvent apply(CertificationCourseRegisteredEvent event) {
        log.debug("CertificationCourseRegisteredProcessor CALLED!");
        final CertificationCourseEntity certificationCourseEntity = certificateCourseService.register(event);
        return new CertificationCourseRegisteredNotificationEvent(certificationCourseEntity.getCourseId(), event.getCertificationCourse().getName());
    }
}
