package com.dreamsoftware.tcs.web.controller.account.error.exception;

/**
 *
 * @author ssanchez
 */
public class ResetPasswordRequestException extends RuntimeException {

    public ResetPasswordRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
