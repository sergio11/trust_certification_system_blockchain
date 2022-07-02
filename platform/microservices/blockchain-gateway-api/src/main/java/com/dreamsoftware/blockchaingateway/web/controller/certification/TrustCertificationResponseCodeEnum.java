package com.dreamsoftware.blockchaingateway.web.controller.certification;

import com.dreamsoftware.blockchaingateway.web.core.IResponseCodeTypes;

/**
 * Trust Certification Response Code Enum
 *
 * @author ssanchez
 */
public enum TrustCertificationResponseCodeEnum implements IResponseCodeTypes {
    CERTIFICATE_ISSUED_ENABLED(400L);

    private final Long code;
    public static final String CATEGORY_NAME = "CERTIFICATE_ISSUED";

    private TrustCertificationResponseCodeEnum(Long code) {
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
