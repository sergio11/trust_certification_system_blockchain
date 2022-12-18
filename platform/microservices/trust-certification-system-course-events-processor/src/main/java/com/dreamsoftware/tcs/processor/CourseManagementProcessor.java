package com.dreamsoftware.tcs.processor;

import com.dreamsoftware.tcs.service.IDispatcherEventHandlerService;
import com.dreamsoftware.tcs.stream.events.AbstractEvent;
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
public class CourseManagementProcessor implements Function<GenericMessage<String>, AbstractEvent> {

    private final IDispatcherEventHandlerService dispatcherEventHandlerService;

    /**
     * @param event
     * @return
     */
    @Override
    public AbstractEvent apply(final GenericMessage<String> event) {
        log.debug("CourseManagementProcessor CALLED!");
        AbstractEvent notificationEvent = null;
        try {
            notificationEvent = dispatcherEventHandlerService.processEventAndGetResult(event.getPayload());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("CourseManagementProcessor ex -> " + e.getMessage());
        }
        return notificationEvent;
    }
}
