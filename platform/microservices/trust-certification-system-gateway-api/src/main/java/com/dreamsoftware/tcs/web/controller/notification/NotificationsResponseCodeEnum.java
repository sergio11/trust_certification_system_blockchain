package com.dreamsoftware.tcs.web.controller.notification;

import com.dreamsoftware.tcs.web.core.IResponseCodeTypes;

/**
 * Notifications Response Code Enum
 *
 * @author ssanchez
 */
public enum NotificationsResponseCodeEnum implements IResponseCodeTypes {
    GET_USER_NOTIFICATIONS_SUCCESS(600L),
    NO_NOTIFICATIONS_FOUND(601L),
    NOTIFICATION_DETAIL(602L),
    NO_NOTIFICATION_FOUND(603L),
    NOTIFICATION_DELETED(604L);

    private final Long code;
    public static final String CATEGORY_NAME = "NOTIFICATIONS";

    private NotificationsResponseCodeEnum(Long code) {
        this.code = code;
    }

    @Override
    public Long getResponseCode() {
        return code;
    }

    @Override
    public String getCategoryName() {
        return CATEGORY_NAME;
    }

    @Override
    public String getCodeName() {
        return name();
    }

}
