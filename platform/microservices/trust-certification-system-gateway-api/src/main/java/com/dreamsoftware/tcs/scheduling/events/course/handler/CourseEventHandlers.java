package com.dreamsoftware.tcs.scheduling.events.course.handler;

import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.mail.model.CourseDeletedMailRequestDTO;
import com.dreamsoftware.tcs.mail.model.CourseDisabledMailRequestDTO;
import com.dreamsoftware.tcs.mail.model.CourseEnabledMailRequestDTO;
import com.dreamsoftware.tcs.mail.model.NewCourseRegistrationRequestedMailRequestDTO;
import com.dreamsoftware.tcs.mail.service.IMailClientService;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseRepository;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.scheduling.events.course.CourseDeletedEvent;
import com.dreamsoftware.tcs.scheduling.events.course.CourseDisabledEvent;
import com.dreamsoftware.tcs.scheduling.events.course.CourseEnabledEvent;
import com.dreamsoftware.tcs.scheduling.events.course.NewCourseRegistrationRequestedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.Assert;

/**
 *
 * @author ssanchez
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CourseEventHandlers {

    private final IMailClientService mailClientService;
    private final I18NService i18nService;
    private final CertificationCourseRepository certificationCourseRepository;
    private final UserRepository userRepository;

    /**
     *
     * @param event
     */
    @Async
    @EventListener
    void handle(final CourseDisabledEvent event) {
        Assert.notNull(event.getId(), "Id can not be null");
        log.debug("CourseDisabledEvent handled!");
        certificationCourseRepository.findOneByCourseId(event.getId()).ifPresent((courseEntity) -> {
            final UserEntity caEntity = courseEntity.getCa();
            mailClientService.sendMail(CourseDisabledMailRequestDTO
                    .builder()
                    .id(event.getId())
                    .courseName(event.getName())
                    .caName(caEntity.getName())
                    .locale(i18nService.parseLocaleOrDefault(caEntity.getLanguage()))
                    .email(caEntity.getEmail())
                    .build());
        });
    }

    /**
     *
     * @param event
     */
    @Async
    @EventListener
    void handle(final CourseEnabledEvent event) {
        Assert.notNull(event.getId(), "Id can not be null");
        log.debug("CourseEnabledEvent handled!");
        certificationCourseRepository.findOneByCourseId(event.getId()).ifPresent((courseEntity) -> {
            final UserEntity caEntity = courseEntity.getCa();
            mailClientService.sendMail(CourseEnabledMailRequestDTO
                    .builder()
                    .id(event.getId())
                    .courseName(event.getName())
                    .caName(caEntity.getName())
                    .locale(i18nService.parseLocaleOrDefault(caEntity.getLanguage()))
                    .email(caEntity.getEmail())
                    .build());
        });
    }

    /**
     *
     * @param event
     */
    @Async
    @EventListener
    void handle(final CourseDeletedEvent event) {
        Assert.notNull(event.getId(), "Id can not be null");
        log.debug("CourseDeletedEvent handled!");
        certificationCourseRepository.findOneByCourseId(event.getId()).ifPresent((courseEntity) -> {
            final UserEntity caEntity = courseEntity.getCa();
            mailClientService.sendMail(CourseDeletedMailRequestDTO
                    .builder()
                    .id(event.getId())
                    .courseName(event.getName())
                    .caName(caEntity.getName())
                    .locale(i18nService.parseLocaleOrDefault(caEntity.getLanguage()))
                    .email(caEntity.getEmail())
                    .build());
        });
    }

    /**
     *
     * @param event
     */
    @Async
    @EventListener
    void handle(final NewCourseRegistrationRequestedEvent event) {
        Assert.notNull(event, "Event can not be null");
        Assert.notNull(event.getCaWalletHash(), "CA Wallet hash can not be null");
        log.debug("NewCourseRegistrationRequestedEvent handled!");
        userRepository.findOneByWalletHash(event.getCaWalletHash()).ifPresent((caEntity) -> {
            mailClientService.sendMail(NewCourseRegistrationRequestedMailRequestDTO
                    .builder()
                    .courseName(event.getCourseName())
                    .email(caEntity.getEmail())
                    .id(caEntity.getId().toString())
                    .locale(i18nService.parseLocaleOrDefault(caEntity.getLanguage()))
                    .caName(caEntity.getName())
                    .build()
            );
        });
    }
}
