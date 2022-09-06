package com.dreamsoftware.tcs.web.controller.users;

import com.dreamsoftware.tcs.web.core.IResponseCodeTypes;

/**
 * Users Response Code Enum Code Response interval -> 8XX
 *
 * @author ssanchez
 */
public enum UsersResponseCodeEnum implements IResponseCodeTypes {

    AVATAR_UPLOAD_SUCCESSFULLY(800L),
    AVATAR_DELETED_SUCCESSFULLY(801L),
    DELETE_AVATAR_FAILED(802L),
    UPLOAD_AVATAR_FAILED(803L),
    GET_LOGIN_HISTORY_SUCCESSFULLY(804L),
    GET_LOGIN_HISTORY_FAILED(805L),
    DELETE_LOGIN_HISTORY_SUCCESSFULLY(806L),
    DELETE_LOGIN_HISTORY_FAILED(807L);

    private final Long code;
    public static final String CATEGORY_NAME = "USERS";

    private UsersResponseCodeEnum(Long code) {
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
