package com.dreamsoftware.blockchaingateway.web.controller;

import com.dreamsoftware.blockchaingateway.web.core.IResponseCodeTypes;

/**
 *
 * @author ssanchez
 */
public enum CertificationAuthorityResponseCodeEnum implements IResponseCodeTypes {
    CERTIFICATION_AUTHORITY_REGISTRATION_REQUESTED(101L),
    CERTIFICATION_AUTHORITY_REGISTRATION_FAILED(102L),
    VALIDATION_ERROR(104L);

    private final Long code;
    public static final String CATEGORY_NAME = "CERTIFICATION_AUTHORITY";

    private CertificationAuthorityResponseCodeEnum(Long code) {
        this.code = code;
    }

    @Override
    public Long getResponseCode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
