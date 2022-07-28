package com.dreamsoftware.tcs.web.controller.core;

import com.dreamsoftware.tcs.web.core.IResponseCodeTypes;

/**
 * Common Error Response Code Enum
 *
 * @author ssanchez Code Reponse interval -> 5xx
 */
public enum CommonErrorResponseCodeEnum implements IResponseCodeTypes {

    ACCESS_DENIED(500L),
    SECURITY_ERROR(501L),
    GENERIC_ERROR(502L),
    RESOURCE_NOT_FOUND(503L),
    USER_NOT_FOUND(504L),
    VALIDATION_ERROR(505L),
    ACCOUNT_LOCKED(506L),
    MESSAGE_NOT_READABLE(507L),
    UNPROCESSABLE_ENTITY(508L),
    ACCOUNT_DISABLED(509L),
    MAX_UUPLOAD_SIZE_EXCEEDED(510L),
    MISSING_REQUEST_PART(511L);

    private final Long code;
    private final static String CATEGORY_NAME = "COMMON_ERRORS";

    private CommonErrorResponseCodeEnum(Long code) {
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
