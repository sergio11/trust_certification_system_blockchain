package com.dreamsoftware.blockchaingateway.web.controller.translations.error.exception;

/**
 *
 * @author ssanchez
 */
public class NoTranslationsFoundException extends RuntimeException {

    public NoTranslationsFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}