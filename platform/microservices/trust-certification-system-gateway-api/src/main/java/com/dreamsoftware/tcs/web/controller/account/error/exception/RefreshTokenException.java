package com.dreamsoftware.tcs.web.controller.account.error.exception;

/**
 *
 * @author ssanchez
 */
public class RefreshTokenException extends RuntimeException {

    public RefreshTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
