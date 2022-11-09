package com.dreamsoftware.tcs.processor.handlers.course;

import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.mail.model.course.CourseEditionDeletedMailRequestDTO;
import com.dreamsoftware.tcs.mail.service.IMailClientService;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEditionEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseEditionRepository;
import com.dreamsoftware.tcs.processor.handlers.AbstractNotificationHandler;
import com.dreamsoftware.tcs.service.INotificationService;
import com.dreamsoftware.tcs.stream.events.notifications.course.CourseEditionDeletedNotificationEvent;
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
public class CourseEditionDeletedHandler extends AbstractNotificationHandler<CourseEditionDeletedNotificationEvent> {

    private final IMailClientService mailClientService;
    private final I18NService i18nService;
    private final CertificationCourseEditionRepository certificationCourseEditionRepository;
    private final INotificationService notificationService;

    /**
     *
     * @param notification
     */
    @Override
    public void onHandle(final CourseEditionDeletedNotificationEvent notification) {
        Assert.notNull(notification, "Notification can not be null");
        log.debug("CourseEditionDeletedHandler CALLED!");
        certificationCourseEditionRepository.findById(new ObjectId(notification.getEditionId())).ifPresent((courseEditionEntity) -> {
            final UserEntity caMember = courseEditionEntity.getCaMember();
            sendNotification(caMember, courseEditionEntity);
            final UserEntity caAdmin = courseEditionEntity.getCourse().getCa().getAdmin();
            sendNotification(caAdmin, courseEditionEntity);
        });
    }


    private void sendNotification(final UserEntity userEntity, final CertificationCourseEditionEntity courseEditionEntity) {
        final Locale userLocale = i18nService.parseLocaleOrDefault(userEntity.getLanguage());
        final String courseTitle = courseEditionEntity.getName().isBlank() ? courseEditionEntity.getCourse().getName() : courseEditionEntity.getName();
        final String notificationTitle = resolveString("course_edition_deleted_title", userLocale, new Object[]{ courseTitle });
        final String notificationMessage = resolveString("course_edition_deleted_message", userLocale, new Object[]{ courseTitle });
        notificationService.saveNotification(notificationTitle, notificationMessage, userEntity);
        mailClientService.sendMail(CourseEditionDeletedMailRequestDTO
                .builder()
                .id(courseEditionEntity.getId().toString())
                .courseName(courseTitle)
                .caName(userEntity.getFullName())
                .locale(userLocale)
                .email(userEntity.getEmail())
                .build());
    }
}
