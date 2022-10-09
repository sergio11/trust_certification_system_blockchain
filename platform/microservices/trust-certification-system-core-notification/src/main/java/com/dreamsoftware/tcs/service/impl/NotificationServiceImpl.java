package com.dreamsoftware.tcs.service.impl;

import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.i18n.service.IMessageSourceResolverService;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificateIssuanceRequestEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CreatedOrderEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.NotificationEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.NotificationLevelEnum;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.NotificationRepository;
import com.dreamsoftware.tcs.service.INotificationService;
import java.util.Date;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 *
 * @author ssanchez
 */
@Service("notificationServiceImpl")
@RequiredArgsConstructor
public class NotificationServiceImpl implements INotificationService {

    /**
     * Message Source Resolver Service
     */
    private final IMessageSourceResolverService messageSourceResolverService;

    /**
     * I18N Service
     */
    private final I18NService i18NService;

    /**
     * Notification Repository
     */
    private final NotificationRepository notificationRepository;

    /**
     *
     * @param user
     */
    @Override
    public void onUserAccountValidated(final UserEntity user) {
        Assert.notNull(user, "User entity can not be null");
        saveNotification("user_account_validated_title", "user_account_validated_message", user);
    }

    /**
     *
     * @param order
     */
    @Override
    public void onUserOrderCompleted(final CreatedOrderEntity order) {
        Assert.notNull(order, "Order entity can not be null");
        Assert.notNull(order.getUser(), "User entity can not be null");
        saveNotification("user_order_completed_title", "user_order_completed_message", order.getUser());
    }

    /**
     *
     * @param certificate
     */
    @Override
    public void onUserCertificateValidated(final CertificateIssuanceRequestEntity certificate) {
        Assert.notNull(certificate, "Certificate can not be null");
        saveNotification("user_certificate_validated_title", "user_account_validated_message", certificate.getStudent());
    }

    /**
     *
     * @param certificationCourseEntity
     */
    @Override
    public void onCACertificationCourseRegistered(final CertificationCourseEntity certificationCourseEntity) {
        Assert.notNull(certificationCourseEntity, "Certification course can not be null");
        saveNotification("ca_certification_course_registered_title", "ca_certification_course_registered_message", certificationCourseEntity.getCa().getAdmin());
    }

    /**
     * Private Methods
     */
    /**
     *
     * @param titleKey
     * @param messageKey
     * @param user
     */
    private void saveNotification(final String titleKey, final String messageKey, final UserEntity user) {
        saveNotification(titleKey, messageKey, user, NotificationLevelEnum.INFO);
    }

    /**
     *
     * @param titleKey
     * @param messageKey
     * @param user
     * @param level
     */
    private void saveNotification(final String titleKey, final String messageKey, final UserEntity user, final NotificationLevelEnum level) {
        final Locale userLocale = i18NService.parseLocaleOrDefault(user.getLanguage());
        final String title = messageSourceResolverService.resolver(titleKey, userLocale);
        final String message = messageSourceResolverService.resolver(messageKey, userLocale);
        notificationRepository.save(NotificationEntity
                .builder()
                .createdAt(new Date())
                .level(level)
                .title(title)
                .message(message)
                .user(user)
                .build());
    }
}
