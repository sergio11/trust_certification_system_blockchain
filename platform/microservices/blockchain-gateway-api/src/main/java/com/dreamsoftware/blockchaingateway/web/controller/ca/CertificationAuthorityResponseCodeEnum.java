package com.dreamsoftware.blockchaingateway.web.controller.ca;

import com.dreamsoftware.blockchaingateway.web.core.IResponseCodeTypes;

/**
 * Certification Authority Response Code Enum
 *
 * @author ssanchez
 */
public enum CertificationAuthorityResponseCodeEnum implements IResponseCodeTypes {
    VALIDATION_ERROR(203L);

    private final Long code;
    public static final String CATEGORY_NAME = "CERTIFICATION_AUTHORITY";

    private CertificationAuthorityResponseCodeEnum(Long code) {
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