package com.dreamsoftware.blockchaingateway.web.controller.account;

import com.dreamsoftware.blockchaingateway.web.core.IResponseCodeTypes;

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
    SIGNIN_VIA_FACEBOOK_SUCCESS(104L),
    SIGNIN_VIA_FACEBOOK_FAIL(105L),
    RESET_PASSWORD_REQUEST_SUCCESS(106L),
    RESET_PASSWORD_REQUEST_FAIL(107L),
    REFRESH_TOKEN(108L),
    REFRESH_TOKEN_FAIL(109L),
    SIGNIN_VIA_GOOGLE_SUCCESS(110L),
    SIGNIN_VIA_GOOGLE_FAIL(111L);

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
