package com.dreamsoftware.tcs.scheduling.events.course.handler;

import com.dreamsoftware.tcs.mail.model.service.IMailClientService;
import com.dreamsoftware.tcs.scheduling.events.course.CourseDisabledEvent;
import com.dreamsoftware.tcs.scheduling.events.course.CourseEnabledEvent;
import com.dreamsoftware.tcs.scheduling.events.course.NewCourseRegistrationRequestedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.Assert;

/**
 *
 * @author ssanchez
 */
@Component
@RequiredArgsConstructor
public class CourseEventHandlers {

    private static final Logger logger = LoggerFactory.getLogger(CourseEventHandlers.class);

    /**
     * Mail Client Service
     */
    private final IMailClientService mailClientService;

    /**
     *
     * @param event
     */
    @Async
    @EventListener
    void handle(final CourseDisabledEvent event) {
        Assert.notNull(event.getId(), "Id can not be null");
        logger.debug("CourseDisabledEvent handled!");

    }

    /**
     *
     * @param event
     */
    @Async
    @EventListener
    void handle(final CourseEnabledEvent event) {
        Assert.notNull(event.getId(), "Id can not be null");
        logger.debug("CourseEnabledEvent handled!");
    }

    /**
     *
     * @param event
     */
    @Async
    @EventListener
    void handle(final NewCourseRegistrationRequestedEvent event) {
        Assert.notNull(event.getId(), "Id can not be null");
        logger.debug("NewCourseRegistrationRequestedEvent handled!");
    }
}
