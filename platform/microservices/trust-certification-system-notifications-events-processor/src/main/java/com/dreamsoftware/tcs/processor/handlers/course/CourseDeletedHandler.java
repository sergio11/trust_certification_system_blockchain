package com.dreamsoftware.tcs.processor.handlers.course;

import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.mail.model.course.CourseDeletedMailRequestDTO;
import com.dreamsoftware.tcs.mail.service.IMailClientService;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseRepository;
import com.dreamsoftware.tcs.processor.handlers.AbstractNotificationHandler;
import com.dreamsoftware.tcs.service.INotificationService;
import com.dreamsoftware.tcs.stream.events.notifications.course.CourseDeletedNotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Locale;

/**
 *
 * @author ssanchez
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
@Slf4j
public class CourseDeletedHandler extends AbstractNotificationHandler<CourseDeletedNotificationEvent> {

    private final IMailClientService mailClientService;
    private final I18NService i18nService;
    private final CertificationCourseRepository certificationCourseRepository;
    private final INotificationService notificationService;

    /**
     *
     * @param notification
     */
    @Override
    public void onHandle(final CourseDeletedNotificationEvent notification) {
        Assert.notNull(notification, "Notification can not be null");
        log.debug("CourseDeletedEvent handled!");
        certificationCourseRepository.findById(new ObjectId(notification.getId())).ifPresent((courseEntity) -> {
            final UserEntity caAdmin = courseEntity.getCa().getAdmin();
            final Locale userLocale = i18nService.parseLocaleOrDefault(caAdmin.getLanguage());
            final String notificationTitle = resolveString("course_deleted_title", userLocale, new Object[]{ courseEntity.getName() });
            final String notificationMessage = resolveString("course_deleted_message", userLocale, new Object[]{ courseEntity.getName() });
            notificationService.saveNotification(notificationTitle, notificationMessage, caAdmin);
            mailClientService.sendMail(CourseDeletedMailRequestDTO
                    .builder()
                    .id(notification.getId())
                    .courseName(courseEntity.getName())
                    .caName(caAdmin.getFullName())
                    .locale(userLocale)
                    .email(caAdmin.getEmail())
                    .build());
        });
    }

}
