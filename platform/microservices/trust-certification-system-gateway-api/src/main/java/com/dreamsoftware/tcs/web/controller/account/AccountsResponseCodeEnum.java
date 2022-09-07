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
    SIGNIN_VIA_FACEBOOK_SUCCESS(111L),
    SIGNIN_VIA_FACEBOOK_FAILED(112L),
    SIGNIN_VIA_GOOGLE_SUCCESS(113L),
    SIGNIN_VIA_GOOGLE_FAILED(114L);

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
