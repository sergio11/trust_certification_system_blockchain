package com.dreamsoftware.tcs.web.controller.account.error.exception;

/**
 *
 * @author ssanchez
 */
public class SignInExternalProviderException extends RuntimeException {

    public SignInExternalProviderException(String message, Throwable cause) {
        super(message, cause);
    }
}
