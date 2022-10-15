package com.dreamsoftware.tcs.web.controller.account.error.exception;

/**
 *
 * @author ssanchez
 */
public class ChangePasswordRequestException extends RuntimeException {

    public ChangePasswordRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
