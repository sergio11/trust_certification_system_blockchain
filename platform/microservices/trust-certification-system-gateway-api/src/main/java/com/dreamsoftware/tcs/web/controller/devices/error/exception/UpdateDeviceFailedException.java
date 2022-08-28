package com.dreamsoftware.tcs.web.controller.devices.error.exception;

/**
 * Update Device Failed Exception
 *
 * @author ssanchez
 */
public class UpdateDeviceFailedException extends RuntimeException {

    public UpdateDeviceFailedException() {
    }

    public UpdateDeviceFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
