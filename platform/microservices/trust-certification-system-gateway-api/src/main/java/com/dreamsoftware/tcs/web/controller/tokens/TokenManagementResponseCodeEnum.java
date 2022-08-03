package com.dreamsoftware.tcs.web.controller.tokens;

import com.dreamsoftware.tcs.web.core.IResponseCodeTypes;

/**
 * Token Management Code Enum
 *
 * @author ssanchez
 */
public enum TokenManagementResponseCodeEnum implements IResponseCodeTypes {
    GET_MY_TOKENS_SUCCESS(700L),
    GET_MY_TOKENS_ERROR(701L),
    GET_CLIENT_TOKENS_SUCCESS(702L),
    GET_CLIENT_TOKENS_ERROR(703L),
    PLACE_TOKENS_ORDER_SUCCESS(704L),
    PLACE_TOKENS_ORDER_ERROR(705L),
    CONFIRM_ORDER_SUCCESS(706L),
    CONFIRM_ORDER_ERROR(707L);

    private final Long code;
    public static final String CATEGORY_NAME = "TOKEN_MANAGEMENT";

    private TokenManagementResponseCodeEnum(Long code) {
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
