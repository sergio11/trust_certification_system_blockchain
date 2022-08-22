package com.dreamsoftware.tcs.processor;

import com.dreamsoftware.tcs.model.events.CertificationCourseRegisteredEvent;
import com.dreamsoftware.tcs.service.ICertificateCourseService;
import java.util.function.Consumer;
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
public class CertificationCourseRegisteredProcessor implements Consumer<CertificationCourseRegisteredEvent> {

    private final ICertificateCourseService certificateCourseService;

    @Override
    public void accept(CertificationCourseRegisteredEvent event) {
        log.debug("CertificationCourseRegisteredEvent CALLED!");
        certificateCourseService.register(event);
    }
}
