package com.dreamsoftware.tcs.web.controller.account;

import com.dreamsoftware.tcs.web.core.IResponseCodeTypes;

/**
 * Accounts Response Code Enum Code Response interval -> 1XX
 *
 * @author ssanchez
 */
public enum AccountsResponseCodeEnum implements IResponseCodeTypes {

    SIGNIN_SUCCESS(100L),
    BAD_CREDENTIALS(101L),
    SIGNUP_SUCCESS(102L),
    SIGNUP_FAIL(103L),
    ACTIVATE_SUCCESS(104L),
    ACTIVATE_FAIL(105L),
    RESET_PASSWORD_REQUEST_SUCCESS(106L),
    RESET_PASSWORD_REQUEST_FAIL(107L),
    REFRESH_TOKEN(108L),
    REFRESH_TOKEN_FAIL(109L),
    SIGNIN_ADMIN_SUCCESS(110L),
    SIGN_IN_EXTERNAL_ACCOUNT_SUCCESS(111L),
    SIGN_IN_EXTERNAL_ACCOUNT_FAILED(112L),
    SIGNUP_EXTERNAL_ACCOUNT_SUCCESS(113L),
    SIGNUP_EXTERNAL_ACCOUNT_FAILED(114L),
    SIGNUP_AS_CA_ADMIN_SUCCESS(115L),
    CHANGE_PASSWORD_REQUEST_SUCCESS(116L),
    CHANGE_PASSWORD_REQUEST_FAILED(117L);

    private final Long code;
    public static final String CATEGORY_NAME = "ACCOUNTS";

    private AccountsResponseCodeEnum(Long code) {
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
