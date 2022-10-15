package com.dreamsoftware.tcs.processor;

import com.dreamsoftware.tcs.service.ICertificateCourseService;
import com.dreamsoftware.tcs.stream.events.notifications.AbstractNotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import java.util.function.Function;

/**
 * Course Management Processor
 *
 * @author ssanchez
 */
@Component("courseManagementProcessor")
@RequiredArgsConstructor
@Slf4j
public class CourseManagementProcessor implements Function<GenericMessage<String>, AbstractNotificationEvent> {

    /**
     * Certification Course Service
     */
    private final ICertificateCourseService certificateCourseService;

    /**
     * @param event
     * @return
     */
    @Override
    public AbstractNotificationEvent apply(final GenericMessage<String> event) {
        log.debug("CourseManagementProcessor CALLED!");
        AbstractNotificationEvent notificationEvent = null;
        try {
            notificationEvent = certificateCourseService.handle(event);
        } catch (Exception e) {
            log.error("CourseManagementProcessor ex -> " + e.getMessage());
        }
        return notificationEvent;
    }
}
