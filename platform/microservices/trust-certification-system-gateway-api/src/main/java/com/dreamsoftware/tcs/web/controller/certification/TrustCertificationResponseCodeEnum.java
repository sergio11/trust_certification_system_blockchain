package com.dreamsoftware.tcs.web.controller.certification;

import com.dreamsoftware.tcs.web.core.IResponseCodeTypes;

/**
 * Trust Certification Response Code Enum
 *
 * @author ssanchez
 */
public enum TrustCertificationResponseCodeEnum implements IResponseCodeTypes {
    CERTIFICATE_ISSUED_ENABLED(400L),
    ENABLE_CERTIFICATE_FAILED(401L),
    CERTIFICATE_ISSUED_DISABLED(402L),
    DISABLE_CERTIFICATE_FAILED(403L),
    CERTIFICATE_ISSUED_DETAIL(404L),
    GET_CERTIFICATE_ISSUED_FAILED(405L),
    CERTIFICATE_ISSUED_VISIBILITY_UPDATED(406L),
    UPDATE_CERTIFICATE_ISSUED_VISIBILITY_FAILED(407L),
    GET_MY_CERTIFICATES_AS_RECIPIENT(408L),
    GET_MY_CERTIFICATES_AS_ISSUER(409L),
    GET_CERTIFICATES_FAILED(410L),
    NEW_CERTIFICATE_ISSUED(411L),
    ISSUE_NEW_CERTIFICATE_FAILED(412L),
    RENEW_CERTIFICATE_ISSUED(413L),
    RENEW_CERTIFICATE_FAILED(414L);

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
