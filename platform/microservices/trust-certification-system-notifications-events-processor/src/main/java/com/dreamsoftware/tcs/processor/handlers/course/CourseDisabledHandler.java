package com.dreamsoftware.tcs.processor.handlers.course;

import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.mail.model.course.CourseDisabledMailRequestDTO;
import com.dreamsoftware.tcs.mail.service.IMailClientService;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseRepository;
import com.dreamsoftware.tcs.processor.handlers.AbstractNotificationHandler;
import com.dreamsoftware.tcs.service.INotificationService;
import com.dreamsoftware.tcs.stream.events.notifications.course.CourseDisabledNotificationEvent;
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
public class CourseDisabledHandler extends AbstractNotificationHandler<CourseDisabledNotificationEvent> {

    private final IMailClientService mailClientService;
    private final I18NService i18nService;
    private final CertificationCourseRepository certificationCourseRepository;
    private final INotificationService notificationService;

    /**
     *
     * @param notification
     */
    @Override
    public void onHandle(final CourseDisabledNotificationEvent notification) {
        Assert.notNull(notification, "Notification can not be null");
        log.debug("CourseDisabledEvent handled!");
        certificationCourseRepository.findById(new ObjectId(notification.getId())).ifPresent((courseEntity) -> {
            final UserEntity caAdmin = courseEntity.getCa().getAdmin();
            final Locale userLocale = i18nService.parseLocaleOrDefault(caAdmin.getLanguage());
            final String notificationTitle = resolveString("course_disabled_title", userLocale, new Object[]{ courseEntity.getName() });
            final String notificationMessage = resolveString("course_disabled_message", userLocale, new Object[]{ courseEntity.getName() });
            notificationService.saveNotification(notificationTitle, notificationMessage, caAdmin);
            mailClientService.sendMail(CourseDisabledMailRequestDTO
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
