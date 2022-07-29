package com.dreamsoftware.tcs.web.controller.certification.error.exception;

/**
 * Reject Certificate Request Exception
 *
 * @author ssanchez
 */
public class RejectCertificateRequestException extends RuntimeException {

    public RejectCertificateRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
