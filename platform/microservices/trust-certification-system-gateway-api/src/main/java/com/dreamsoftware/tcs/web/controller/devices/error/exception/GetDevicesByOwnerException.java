package com.dreamsoftware.tcs.web.controller.devices.error.exception;

/**
 * Get Devices by owner exception
 *
 * @author ssanchez
 */
public class GetDevicesByOwnerException extends RuntimeException {

    public GetDevicesByOwnerException() {
    }

    public GetDevicesByOwnerException(String message, Throwable cause) {
        super(message, cause);
    }
}
