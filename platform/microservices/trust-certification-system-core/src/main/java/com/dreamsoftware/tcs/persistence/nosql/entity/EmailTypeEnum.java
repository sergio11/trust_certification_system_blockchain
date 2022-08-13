package com.dreamsoftware.tcs.persistence.nosql.entity;

/**
 *
 * @author ssanchez
 */
public enum EmailTypeEnum {
    COMPLETE_ACCOUNT_DELETION,
    USER_ACTIVATED,
    CONFIRM_PASSWORD_CHANGE,
    RESET_PASSWORD,
    USER_PENDING_VALIDATION,
    CA_ENABLED,
    CA_DISABLED;
}
