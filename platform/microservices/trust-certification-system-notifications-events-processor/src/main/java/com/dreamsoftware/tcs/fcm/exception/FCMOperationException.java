package com.dreamsoftware.tcs.fcm.exception;

import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FCMOperationException extends RuntimeException {

    private Map<String, Object> properties;

}
