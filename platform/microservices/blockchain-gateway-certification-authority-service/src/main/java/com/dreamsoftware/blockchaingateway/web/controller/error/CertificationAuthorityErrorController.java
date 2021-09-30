package com.dreamsoftware.blockchaingateway.web.controller.error;

import com.dreamsoftware.blockchaingateway.web.controller.ca.CertificationAuthorityResponseCodeEnum;
import com.dreamsoftware.blockchaingateway.web.controller.error.exception.RegisterCertificationAuthorityException;
import com.dreamsoftware.blockchaingateway.web.core.APIResponse;
import com.dreamsoftware.blockchaingateway.web.core.ErrorResponseDTO;
import com.dreamsoftware.blockchaingateway.web.core.FieldErrorDTO;
import com.dreamsoftware.blockchaingateway.web.controller.core.SupportController;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author ssanchez
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CertificationAuthorityErrorController extends SupportController {

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Object exceptionHandler(IOException e) {
        if (StringUtils.containsIgnoreCase(ExceptionUtils.getRootCauseMessage(e), "Broken pipe")) {
            return null;        //socket is closed, cannot return any response
        } else {
            return new HttpEntity<>(e.getMessage());
        }
    }

    /**
     * Handler for Constraint Violation Exception
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<APIResponse<ErrorResponseDTO>> handleConstraintViolationException(ConstraintViolationException ex) {

        List<FieldErrorDTO> fieldErrors = ex.getConstraintViolations().stream()
                .map(constraintViolation -> FieldErrorDTO.builder()
                .field(constraintViolation.getPropertyPath().toString())
                .message(constraintViolation.getMessage())
                .build())
                .collect(Collectors.toList());

        return responseHelper.createAndSendErrorResponse(
                CertificationAuthorityResponseCodeEnum.VALIDATION_ERROR,
                HttpStatus.BAD_REQUEST, fieldErrors);

    }

    /**
     * Handler for RegisterCertificationAuthorityException
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(RegisterCertificationAuthorityException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleRegisterCertificationAuthorityException(RegisterCertificationAuthorityException ex, HttpServletRequest request) {
        return responseHelper.<String>createAndSendErrorResponse(CertificationAuthorityResponseCodeEnum.CERTIFICATION_AUTHORITY_REGISTRATION_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, "An error ocurred when try to register certification authority");
    }
}
