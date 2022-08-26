package com.dreamsoftware.tcs.web.controller.course.error.exception;

/**
 * Certification Course Not Found Exception
 *
 * @author ssanchez
 */
public class CertificationCourseNotFoundException extends RuntimeException {

    public CertificationCourseNotFoundException() {
    }

    public CertificationCourseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
