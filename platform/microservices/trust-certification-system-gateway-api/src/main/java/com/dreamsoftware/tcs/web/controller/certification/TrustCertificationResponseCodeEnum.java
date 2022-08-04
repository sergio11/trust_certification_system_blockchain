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
    NEW_ISSUED_CERTIFICATE_REQUESTED(411L),
    NEW_ISSUE_CERTIFICATE_REQUEST_FAILED(412L),
    ISSUE_CERTIFICATE_REQUEST_ACCEPTED(413L),
    ACCEPT_CERTIFICATE_REQUEST_FAILED(414L),
    ISSUE_CERTIFICATE_REQUEST_REJECTED(415L),
    REJECT_CERTIFICATE_REQUEST_FAILED(416L),
    RENEW_CERTIFICATE_ISSUED(417L),
    RENEW_CERTIFICATE_FAILED(418L),
    GET_CERTIFICATES_ISSUANCE_REQUEST_SUCCESS(419L),
    GET_CERTIFICATES_ISSUANCE_REQUEST_FAILED(420L),
    DOWNLOAD_CERTIFICATE_FILE_FAILED(421L);

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
