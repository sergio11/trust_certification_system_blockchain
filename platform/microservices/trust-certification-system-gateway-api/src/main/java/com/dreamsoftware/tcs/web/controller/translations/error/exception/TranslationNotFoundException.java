package com.dreamsoftware.tcs.web.controller.translations.error.exception;

/**
 *
 * @author ssanchez
 */
public class TranslationNotFoundException extends RuntimeException {

    public TranslationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
