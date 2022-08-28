package com.dreamsoftware.tcs.web.controller.devices.error;

import com.dreamsoftware.tcs.web.controller.core.SupportController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 *
 * @author ssanchez
 */
@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DeviceGroupsErrorController extends SupportController {

}
