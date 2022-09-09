package com.dreamsoftware.tcs.web.controller.account.error.exception;

/**
 *
 * @author ssanchez
 */
public class SignInException extends RuntimeException {

    public SignInException(String message, Throwable cause) {
        super(message, cause);
    }
}
