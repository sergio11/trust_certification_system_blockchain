package com.dreamsoftware.tcs.processor.handlers;

import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.stream.events.course.CourseCertificateRegistrationRequestEvent;
import com.dreamsoftware.tcs.stream.events.notifications.course.CertificationCourseRegisteredNotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
@Slf4j
public class CourseCertificateRegistrationRequestHandler extends AbstractCourseManagementHandler<CourseCertificateRegistrationRequestEvent, CertificationCourseRegisteredNotificationEvent> {

    /**
     *
     * @param event
     * @return
     * @throws RepositoryException
     */
    @Override
    public CertificationCourseRegisteredNotificationEvent onHandle(final CourseCertificateRegistrationRequestEvent event) throws RepositoryException {
        Assert.notNull(event, "Event can not be null");
        return null;
    }
}
