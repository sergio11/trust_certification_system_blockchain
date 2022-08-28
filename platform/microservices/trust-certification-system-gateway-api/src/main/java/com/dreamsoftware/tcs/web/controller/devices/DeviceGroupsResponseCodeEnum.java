package com.dreamsoftware.tcs.web.controller.devices;

import com.dreamsoftware.tcs.web.core.IResponseCodeTypes;

/**
 * Device Groups Response Code Enum
 *
 * @author ssanchez
 */
public enum DeviceGroupsResponseCodeEnum implements IResponseCodeTypes {
    GET_DEVICES_BY_OWNER(600L),
    DEVICE_ADDED_TO_GROUP(601L),
    DEVICE_TOKEN_UPDATED(602L),
    DEVICE_TOKEN_SAVED(603L),
    DEVICE_REMOVED_FROM_GROUP(604L),
    ADD_DEVICE_TO_GROUP_FAILED(605L),
    GET_DEVICES_BY_OWNER_FAILED(606L),
    REMOVE_DEVICE_FROM_GROUP_FAILED(607L),
    SAVE_DEVICE_FAILED(608L),
    UPDATE_DEVICE_FAILED(609L);

    private final Long code;
    public static final String CATEGORY_NAME = "DEVICE_GROUPS";

    private DeviceGroupsResponseCodeEnum(Long code) {
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
