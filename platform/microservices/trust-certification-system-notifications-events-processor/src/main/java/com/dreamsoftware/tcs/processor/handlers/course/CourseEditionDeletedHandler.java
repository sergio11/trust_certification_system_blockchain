package com.dreamsoftware.tcs.processor.handlers.course;

import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.mail.service.IMailClientService;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.processor.handlers.AbstractNotificationHandler;
import com.dreamsoftware.tcs.stream.events.notifications.course.CourseEditionDeletedNotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 *
 * @author ssanchez
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
@Slf4j
public class CourseEditionDeletedHandler extends AbstractNotificationHandler<CourseEditionDeletedNotificationEvent> {

    private final IMailClientService mailClientService;
    private final I18NService i18nService;
    private final UserRepository userRepository;

    /**
     *
     * @param notification
     */
    @Override
    public void onHandle(final CourseEditionDeletedNotificationEvent notification) {
        Assert.notNull(notification, "Notification can not be null");
        log.debug("CourseEditionDeletedHandler CALLED!");
    }

}
