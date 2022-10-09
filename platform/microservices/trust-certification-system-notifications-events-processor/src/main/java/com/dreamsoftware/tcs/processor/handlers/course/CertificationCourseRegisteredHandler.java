package com.dreamsoftware.tcs.processor.handlers.course;

import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.mail.model.CertificationCourseRegisteredMailRequestDTO;
import com.dreamsoftware.tcs.mail.service.IMailClientService;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseRepository;
import com.dreamsoftware.tcs.processor.handlers.AbstractNotificationHandler;
import com.dreamsoftware.tcs.service.INotificationService;
import com.dreamsoftware.tcs.stream.events.notifications.course.CertificationCourseRegisteredNotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
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
public class CertificationCourseRegisteredHandler extends AbstractNotificationHandler<CertificationCourseRegisteredNotificationEvent> {

    private final CertificationCourseRepository certificationCourseRepository;
    private final IMailClientService mailClientService;
    private final INotificationService notificationService;
    private final I18NService i18nService;

    /**
     *
     * @param notification
     */
    @Override
    public void onHandle(final CertificationCourseRegisteredNotificationEvent notification) {
        Assert.notNull(notification, "Notification can not be null");
        certificationCourseRepository.findById(new ObjectId(notification.getCourseId())).ifPresent((certificationCourseEntitySaved) -> {
            notificationService.onCACertificationCourseRegistered(certificationCourseEntitySaved);
            final UserEntity caAdmin = certificationCourseEntitySaved.getCa().getAdmin();
            mailClientService.sendMail(CertificationCourseRegisteredMailRequestDTO.builder()
                    .courseId(certificationCourseEntitySaved.getId().toString())
                    .courseName(notification.getCourseName())
                    .email(caAdmin.getEmail())
                    .locale(i18nService.parseLocaleOrDefault(caAdmin.getLanguage()))
                    .build());
        });
    }

}
