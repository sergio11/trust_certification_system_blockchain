package com.dreamsoftware.blockchaingateway.web.controller.error;

import com.dreamsoftware.blockchaingateway.web.controller.core.CommonErrorResponseCodeEnum;
import com.dreamsoftware.blockchaingateway.web.core.APIResponse;
import com.dreamsoftware.blockchaingateway.web.core.ErrorResponseDTO;
import com.dreamsoftware.blockchaingateway.web.core.FieldErrorDTO;
import com.dreamsoftware.blockchaingateway.web.controller.core.SupportController;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Common Error REST Controller
 *
 * @author ssanchez
 */
@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class CommonErrorRestController extends SupportController {

    private static Logger logger = LoggerFactory.getLogger(CommonErrorRestController.class);

    /**
     * Handler for Validation Exception
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<APIResponse<ErrorResponseDTO>> handleValidationException(MethodArgumentNotValidException ex) {

        logger.error("Handler for Validation Exception -> MethodArgumentNotValidException ");

        final List<FieldErrorDTO> fieldErrors = new ArrayList();

        final BindingResult br = ex.getBindingResult();
        if (br != null) {
            br.getFieldErrors().forEach((fieldError) -> {
                fieldErrors.add(FieldErrorDTO.builder()
                        .field(fieldError.getObjectName())
                        .message(fieldError.getDefaultMessage())
                        .build());
            });
        }

        return responseHelper.<ErrorResponseDTO>createAndSendErrorResponse(
                CommonErrorResponseCodeEnum.VALIDATION_ERROR, HttpStatus.BAD_REQUEST,
                fieldErrors);
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

        logger.error("Handler for Constraint Violation Exception -> ConstraintViolationException ");

        List<FieldErrorDTO> fieldErrors = ex.getConstraintViolations().stream()
                .map(constraintViolation -> FieldErrorDTO.builder()
                .field(constraintViolation.getPropertyPath().toString())
                .message(constraintViolation.getMessage())
                .build())
                .collect(Collectors.toList());

        return responseHelper.createAndSendErrorResponse(
                CommonErrorResponseCodeEnum.VALIDATION_ERROR,
                HttpStatus.BAD_REQUEST, fieldErrors);

    }

    /**
     * Handler for Bind Exception
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResponseEntity<APIResponse<ErrorResponseDTO>> handleBindException(BindException ex) {

        logger.error("Handler for BindException ");

        final List<FieldErrorDTO> fieldErrors = new ArrayList();

        if (!ex.getFieldErrors().isEmpty()) {
            ex.getFieldErrors().forEach((fieldError) -> {
                fieldErrors.add(FieldErrorDTO.builder()
                        .field(fieldError.getField())
                        .message(fieldError.getDefaultMessage())
                        .build());
            });
        } else {
            ex.getAllErrors().forEach((fieldError) -> {
                fieldErrors.add(FieldErrorDTO.builder()
                        .field(fieldError.getObjectName())
                        .message(fieldError.getDefaultMessage())
                        .build());
            });
        }

        return responseHelper.<ErrorResponseDTO>createAndSendErrorResponse(
                CommonErrorResponseCodeEnum.VALIDATION_ERROR, HttpStatus.BAD_REQUEST,
                fieldErrors);

    }

    /**
     * Handler for locked exception
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(LockedException.class)
    @ResponseBody
    public ResponseEntity<APIResponse<ErrorResponseDTO>> handleLockedException(LockedException ex, HttpServletRequest request) {

        logger.error("Handler for LockedException -> LockedException ");

        return responseHelper.createAndSendErrorResponse(
                CommonErrorResponseCodeEnum.ACCOUNT_LOCKED, HttpStatus.FORBIDDEN,
                "User Account Locked");
    }

    /**
     * Handler for Disabled Exception
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(DisabledException.class)
    @ResponseBody
    public ResponseEntity<APIResponse<ErrorResponseDTO>> handleDisabledException(DisabledException ex) {

        logger.error("Handler for DisabledException -> DisabledException ");

        return responseHelper.<ErrorResponseDTO>createAndSendErrorResponse(
                CommonErrorResponseCodeEnum.ACCOUNT_DISABLED, HttpStatus.FORBIDDEN,
                "User Account Not Activate");
    }

    /**
     * Handler For Access Denied Exception
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ResponseEntity<APIResponse<ErrorResponseDTO>> handleAccessDeniedException(AccessDeniedException ex) {
        logger.error("Handler for AccessDeniedException ");
        return responseHelper.<ErrorResponseDTO>createAndSendErrorResponse(
                CommonErrorResponseCodeEnum.ACCESS_DENIED, HttpStatus.FORBIDDEN,
                "User Access Denied");
    }

    /**
     * Handler for HTTP Message Not Readable Exception
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<APIResponse<ErrorResponseDTO>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        logger.error("Handler for HttpMessageNotReadableException ");
        return responseHelper.<String>createAndSendErrorResponse(
                CommonErrorResponseCodeEnum.MESSAGE_NOT_READABLE, HttpStatus.BAD_REQUEST,
                "Message Not readable");
    }

    /**
     * Handler for unexpected exception
     *
     * @param exception
     * @param request
     * @param response
     * @return
     */
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleException(
            Throwable exception, HttpServletRequest request, HttpServletResponse response) {
        exception.printStackTrace();
        logger.error(exception.getClass().getCanonicalName());
        logger.error(exception.getMessage());
        return responseHelper.createAndSendErrorResponse(CommonErrorResponseCodeEnum.GENERIC_ERROR, HttpStatus.INTERNAL_SERVER_ERROR,
                "Unexpected Error Occurred");
    }
}
