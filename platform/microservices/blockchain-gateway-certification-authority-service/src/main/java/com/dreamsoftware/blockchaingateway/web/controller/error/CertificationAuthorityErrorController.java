package com.dreamsoftware.blockchaingateway.web.controller.error;

import com.dreamsoftware.blockchaingateway.web.controller.core.SupportController;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 *
 * @author ssanchez
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CertificationAuthorityErrorController extends SupportController {

}
