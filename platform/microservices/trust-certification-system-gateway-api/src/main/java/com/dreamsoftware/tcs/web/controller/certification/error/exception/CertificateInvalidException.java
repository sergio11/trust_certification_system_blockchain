package com.dreamsoftware.tcs.web.controller.certification.error.exception;

/**
 *
 *
 * @author ssanchez
 */
public class CertificateInvalidException extends RuntimeException {

    public CertificateInvalidException() {
        super();
    }
    public CertificateInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}
