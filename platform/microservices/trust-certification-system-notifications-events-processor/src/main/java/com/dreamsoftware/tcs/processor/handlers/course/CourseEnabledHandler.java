package com.dreamsoftware.tcs.processor.handlers.course;

import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.mail.model.CourseEnabledMailRequestDTO;
import com.dreamsoftware.tcs.mail.service.IMailClientService;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseRepository;
import com.dreamsoftware.tcs.processor.handlers.AbstractNotificationHandler;
import com.dreamsoftware.tcs.stream.events.notifications.course.CourseEnabledNotificationEvent;
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
public class CourseEnabledHandler extends AbstractNotificationHandler<CourseEnabledNotificationEvent> {

    private final IMailClientService mailClientService;
    private final I18NService i18nService;
    private final CertificationCourseRepository certificationCourseRepository;

    /**
     *
     * @param notification
     */
    @Override
    public void onHandle(final CourseEnabledNotificationEvent notification) {
        Assert.notNull(notification, "Notification can not be null");
        log.debug("CourseEnabledEvent handled!");
        certificationCourseRepository.findOneByCourseId(notification.getId()).ifPresent((courseEntity) -> {
            final UserEntity caEntity = courseEntity.getCa();
            mailClientService.sendMail(CourseEnabledMailRequestDTO
                    .builder()
                    .id(notification.getId())
                    .courseName(notification.getName())
                    .caName(caEntity.getName())
                    .locale(i18nService.parseLocaleOrDefault(caEntity.getLanguage()))
                    .email(caEntity.getEmail())
                    .build());
        });
    }

}
