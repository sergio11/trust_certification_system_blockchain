package com.dreamsoftware.tcs.service;

import com.dreamsoftware.tcs.persistence.nosql.entity.NotificationLevelEnum;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;

/**
 *
 * @author ssanchez
 */
public interface INotificationService {

    /**
     *
     * @param titleKey
     * @param messageKey
     * @param user
     * @param level
     */
    void saveNotification(final String titleKey, final String messageKey, final UserEntity user, final NotificationLevelEnum level);

    /**
     *
     * @param titleKey
     * @param messageKey
     * @param user
     */
    void saveNotification(final String titleKey, final String messageKey, final UserEntity user);

}
