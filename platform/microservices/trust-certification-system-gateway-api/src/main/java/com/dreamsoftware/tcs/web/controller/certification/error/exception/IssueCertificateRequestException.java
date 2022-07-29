package com.dreamsoftware.tcs.web.controller.certification.error.exception;

/**
 * Issue Certificate Request Exception
 *
 * @author ssanchez
 */
public class IssueCertificateRequestException extends RuntimeException {

    public IssueCertificateRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
