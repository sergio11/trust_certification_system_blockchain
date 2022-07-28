package com.dreamsoftware.tcs.web.controller.ca.error;

import com.dreamsoftware.tcs.web.controller.ca.CertificationAuthorityResponseCodeEnum;
import com.dreamsoftware.tcs.web.controller.ca.error.exception.DisableCertificationAuthorityException;
import com.dreamsoftware.tcs.web.controller.ca.error.exception.EnableCertificationAuthorityException;
import com.dreamsoftware.tcs.web.controller.ca.error.exception.GetCertificationAuthorityException;
import com.dreamsoftware.tcs.web.controller.core.SupportController;
import com.dreamsoftware.tcs.web.core.APIResponse;
import com.dreamsoftware.tcs.web.core.ErrorResponseDTO;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author ssanchez
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CertificationAuthorityErrorController extends SupportController {

    private static Logger logger = LoggerFactory.getLogger(CertificationAuthorityErrorController.class);

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(GetCertificationAuthorityException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleGetCertificationAuthorityException(GetCertificationAuthorityException ex, HttpServletRequest request) {
        logger.error("Handler for GetCertificationAuthorityException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(CertificationAuthorityResponseCodeEnum.CERTIFICATION_AUTHORITY_FAIL,
                HttpStatus.INTERNAL_SERVER_ERROR, "Certification Authority Fail");
    }

    @ExceptionHandler(EnableCertificationAuthorityException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleEnableCertificationAuthorityException(EnableCertificationAuthorityException ex, HttpServletRequest request) {
        logger.error("Handler for EnableCertificationAuthorityException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(CertificationAuthorityResponseCodeEnum.ENABLE_CERTIFICATION_AUTHORITY_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, "Enable Certification Authority Failed");
    }

    @ExceptionHandler(DisableCertificationAuthorityException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleEnableCertificationAuthorityException(DisableCertificationAuthorityException ex, HttpServletRequest request) {
        logger.error("Handler for DisableCertificationAuthorityException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(CertificationAuthorityResponseCodeEnum.DISABLE_CERTIFICATION_AUTHORITY_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, "Disable Certification Authority Failed");
    }
}
