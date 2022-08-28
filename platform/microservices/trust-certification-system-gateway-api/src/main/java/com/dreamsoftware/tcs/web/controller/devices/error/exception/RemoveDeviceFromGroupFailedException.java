package com.dreamsoftware.tcs.web.controller.devices.error.exception;

/**
 * Remove Device From Group Failed Exception
 *
 * @author ssanchez
 */
public class RemoveDeviceFromGroupFailedException extends RuntimeException {

    public RemoveDeviceFromGroupFailedException() {
    }

    public RemoveDeviceFromGroupFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
