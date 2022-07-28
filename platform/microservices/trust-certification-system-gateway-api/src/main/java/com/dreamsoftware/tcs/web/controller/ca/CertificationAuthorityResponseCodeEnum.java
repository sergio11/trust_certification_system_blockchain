package com.dreamsoftware.tcs.web.controller.ca;

import com.dreamsoftware.tcs.web.core.IResponseCodeTypes;

/**
 * Certification Authority Response Code Enum
 *
 * @author ssanchez
 */
public enum CertificationAuthorityResponseCodeEnum implements IResponseCodeTypes {
    CERTIFICATION_AUTHORITY_DETAIL(200L),
    CERTIFICATION_AUTHORITY_FAIL(201L),
    CERTIFICATION_AUTHORITY_ENABLED(202L),
    ENABLE_CERTIFICATION_AUTHORITY_FAILED(203L),
    CERTIFICATION_AUTHORITY_DISABLED(204L),
    DISABLE_CERTIFICATION_AUTHORITY_FAILED(205L);

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
