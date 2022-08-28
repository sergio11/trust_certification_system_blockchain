package com.dreamsoftware.tcs.web.controller.devices.error.exception;

/**
 * Add Device to group Exception
 *
 * @author ssanchez
 */
public class AddDeviceToGroupException extends RuntimeException {

    public AddDeviceToGroupException() {
    }

    public AddDeviceToGroupException(String message, Throwable cause) {
        super(message, cause);
    }
}
