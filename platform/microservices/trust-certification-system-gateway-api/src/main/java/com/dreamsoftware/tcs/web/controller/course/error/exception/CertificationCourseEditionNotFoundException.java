package com.dreamsoftware.tcs.web.controller.course.error.exception;

/**
 * Certification Course Edition Not Found Exception
 *
 * @author ssanchez
 */
public class CertificationCourseEditionNotFoundException extends RuntimeException {

    public CertificationCourseEditionNotFoundException() {
    }

    public CertificationCourseEditionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
