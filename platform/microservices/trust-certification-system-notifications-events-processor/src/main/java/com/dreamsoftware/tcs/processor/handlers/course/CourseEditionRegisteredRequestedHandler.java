package com.dreamsoftware.tcs.processor.handlers.course;

import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.mail.model.NewCourseRegistrationRequestedMailRequestDTO;
import com.dreamsoftware.tcs.mail.service.IMailClientService;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.processor.handlers.AbstractNotificationHandler;
import com.dreamsoftware.tcs.stream.events.notifications.course.CourseEditionRegisteredNotificationEvent;
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
public class CourseEditionRegisteredRequestedHandler extends AbstractNotificationHandler<CourseEditionRegisteredNotificationEvent> {

    private final IMailClientService mailClientService;
    private final I18NService i18nService;
    private final UserRepository userRepository;

    /**
     *
     * @param notification
     */
    @Override
    public void onHandle(final CourseEditionRegisteredNotificationEvent notification) {
        Assert.notNull(notification, "Notification can not be null");
        Assert.notNull(notification.getCaWalletHash(), "CA Wallet hash can not be null");
        log.debug("NewCourseRegistrationRequestedEvent handled!");
        userRepository.findOneByWalletHash(notification.getCaWalletHash()).ifPresent((caEntity) -> {
            mailClientService.sendMail(NewCourseRegistrationRequestedMailRequestDTO
                    .builder()
                    .courseName(notification.getCourseName())
                    .email(caEntity.getEmail())
                    .id(caEntity.getId().toString())
                    .locale(i18nService.parseLocaleOrDefault(caEntity.getLanguage()))
                    .caName(caEntity.getFullName())
                    .build()
            );
        });
    }

}
