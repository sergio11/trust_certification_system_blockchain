package com.dreamsoftware.blockchaingateway.web.controller.error.exception;

/**
 *
 * @author ssanchez
 */
public class RefreshTokenException extends RuntimeException {

    public RefreshTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
