package com.dreamsoftware.blockchaingateway.processor;

import com.dreamsoftware.blockchaingateway.model.CertificationCourseRegisteredEvent;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Certification Course Registered Processor
 *
 * @author ssanchez
 */
@Component("courseRegisteredProcessor")
@RequiredArgsConstructor
public class CertificationCourseRegisteredProcessor implements Consumer<CertificationCourseRegisteredEvent> {

    private final Logger logger = LoggerFactory.getLogger(CertificationCourseRegisteredProcessor.class);

    @Override
    public void accept(CertificationCourseRegisteredEvent event) {
        logger.debug("CertificationCourseRegisteredEvent CALLED!");
    }
}
