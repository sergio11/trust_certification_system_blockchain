package com.dreamsoftware.blockchaingateway.web.controller.course;

import com.dreamsoftware.blockchaingateway.web.core.IResponseCodeTypes;

/**
 * Certification Course Response Code Enum
 *
 * @author ssanchez
 */
public enum CertificationCourseResponseCodeEnum implements IResponseCodeTypes {
    CERTIFICATION_COURSE_DETAIL(300L),
    CERTIFICATION_COURSE_FAIL(301L),
    CERTIFICATION_COURSE_ENABLED(302L),
    ENABLE_CERTIFICATION_COURSE_FAILED(303L),
    CERTIFICATION_COURSE_DISABLED(304L),
    DISABLE_CERTIFICATION_COURSE_FAILED(305L),
    SAVE_CERTIFICATION_COURSE(306L),
    SAVE_CERTIFICATION_COURSE_FAIL(307L);

    private final Long code;
    public static final String CATEGORY_NAME = "CERTIFICATION_COURSE";

    private CertificationCourseResponseCodeEnum(Long code) {
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
