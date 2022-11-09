package com.dreamsoftware.tcs.service.impl;

import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.i18n.service.IMessageSourceResolverService;
import com.dreamsoftware.tcs.persistence.nosql.entity.NotificationEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.NotificationLevelEnum;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.NotificationRepository;
import com.dreamsoftware.tcs.service.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Locale;

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
     * @param titleKey
     * @param messageKey
     * @param user
     */
    @Override
    public void saveNotification(final String titleKey, final String messageKey, final UserEntity user) {
        saveNotification(titleKey, messageKey, user, NotificationLevelEnum.INFO);
    }

    /**
     *
     * @param titleKey
     * @param messageKey
     * @param user
     * @param level
     */
    public void saveNotification(final String titleKey, final String messageKey, final UserEntity user, final NotificationLevelEnum level) {
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
