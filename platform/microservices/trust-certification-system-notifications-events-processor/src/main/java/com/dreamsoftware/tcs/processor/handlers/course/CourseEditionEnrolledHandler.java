package com.dreamsoftware.tcs.processor.handlers.course;

import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.mail.model.course.CourseEditionEnrolledMailRequestDTO;
import com.dreamsoftware.tcs.mail.service.IMailClientService;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEditionEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserTypeEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseEditionRepository;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.processor.handlers.AbstractNotificationHandler;
import com.dreamsoftware.tcs.service.INotificationService;
import com.dreamsoftware.tcs.stream.events.notifications.course.CourseEditionEnrolledNotificationEvent;
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
public class CourseEditionEnrolledHandler extends AbstractNotificationHandler<CourseEditionEnrolledNotificationEvent> {

    private final IMailClientService mailClientService;
    private final I18NService i18nService;
    private final CertificationCourseEditionRepository certificationCourseEditionRepository;
    private final UserRepository userRepository;
    private final INotificationService notificationService;

    /**
     *
     * @param notification
     */
    @Override
    public void onHandle(final CourseEditionEnrolledNotificationEvent notification) {
        Assert.notNull(notification, "Notification can not be null");
        log.debug("CourseEditionEnrolledHandler CALLED!");
        certificationCourseEditionRepository.findById(new ObjectId(notification.getEditionId())).ifPresent((courseEditionEntity) -> {
            final UserEntity caMember = courseEditionEntity.getCaMember();
            sendNotification(caMember, courseEditionEntity);
            userRepository.findOneByWalletHash(notification.getStudentWalletHash()).ifPresent(studentEntity -> {
                sendNotification(studentEntity, courseEditionEntity);
            });
        });
    }


    private void sendNotification(final UserEntity userEntity, final CertificationCourseEditionEntity courseEditionEntity) {
        final Locale userLocale = i18nService.parseLocaleOrDefault(userEntity.getLanguage());
        final String courseTitle = courseEditionEntity.getName().isBlank() ? courseEditionEntity.getCourse().getName() : courseEditionEntity.getName();
        final String notificationTitle = userEntity.getType().equals(UserTypeEnum.STUDENT) ?
                resolveString("course_edition_enrolled_student_title", userLocale, new Object[]{ userEntity.getFullName(), courseTitle }) :
                resolveString("course_edition_enrolled_title", userLocale, new Object[]{ courseTitle });
        final String notificationMessage = userEntity.getType().equals(UserTypeEnum.STUDENT) ?
                resolveString("course_edition_enrolled_student_message", userLocale, new Object[]{ userEntity.getFullName(), courseTitle }) :
                resolveString("course_edition_enrolled_message", userLocale, new Object[]{ courseTitle });
        notificationService.saveNotification(notificationTitle, notificationMessage, userEntity);
        if(userEntity.getType().equals(UserTypeEnum.STUDENT)) {
            mailClientService.sendMail(CourseEditionEnrolledMailRequestDTO
                    .builder()
                    .id(courseEditionEntity.getId().toString())
                    .courseName(courseTitle)
                    .studentName(userEntity.getFullName())
                    .locale(userLocale)
                    .email(userEntity.getEmail())
                    .build());
        }
    }
}
