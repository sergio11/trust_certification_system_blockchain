package com.dreamsoftware.blockchaingateway.web.controller.error.exception;

/**
 *
 * @author ssanchez
 */
public class SignUpException extends RuntimeException {

    public SignUpException(String message, Throwable cause) {
        super(message, cause);
    }
}
