package com.dreamsoftware.tcs.web.controller.certification.error;

import com.dreamsoftware.tcs.web.controller.certification.TrustCertificationResponseCodeEnum;
import com.dreamsoftware.tcs.web.controller.certification.error.exception.DisableCertificateException;
import com.dreamsoftware.tcs.web.controller.certification.error.exception.EnableCertificateException;
import com.dreamsoftware.tcs.web.controller.certification.error.exception.GetCertificateIssuedDetailException;
import com.dreamsoftware.tcs.web.controller.certification.error.exception.GetCertificatesException;
import com.dreamsoftware.tcs.web.controller.certification.error.exception.IssueCertificateException;
import com.dreamsoftware.tcs.web.controller.certification.error.exception.RenewCertificateException;
import com.dreamsoftware.tcs.web.controller.certification.error.exception.UpdateCertificateVisibilityException;
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
public class TrustCertificationErrorController extends SupportController {

    private static Logger logger = LoggerFactory.getLogger(TrustCertificationErrorController.class);

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(EnableCertificateException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleEnableCertificateCourseException(EnableCertificateException ex, HttpServletRequest request) {
        logger.error("Handler for EnableCertificateCourseException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(TrustCertificationResponseCodeEnum.ENABLE_CERTIFICATE_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, "Enable Certificate Failed");
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(DisableCertificateException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleDisableCertificateCourseException(DisableCertificateException ex, HttpServletRequest request) {
        logger.error("Handler for DisableCertificateCourseException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(TrustCertificationResponseCodeEnum.DISABLE_CERTIFICATE_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, "Disable Certification Failed");
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(GetCertificateIssuedDetailException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleGetCertificateIssuedDetailException(GetCertificateIssuedDetailException ex, HttpServletRequest request) {
        logger.error("Handler for GetCertificateIssuedDetailException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(TrustCertificationResponseCodeEnum.GET_CERTIFICATE_ISSUED_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, "Get Certificate Issued Failed");
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(UpdateCertificateVisibilityException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleUpdateCertificateVisibilityException(UpdateCertificateVisibilityException ex, HttpServletRequest request) {
        logger.error("Handler for UpdateCertificateVisibilityException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(TrustCertificationResponseCodeEnum.UPDATE_CERTIFICATE_ISSUED_VISIBILITY_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, "Update Certificate Issued Visibility Failed");
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(GetCertificatesException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleGetCertificatesException(GetCertificatesException ex, HttpServletRequest request) {
        logger.error("Handler for GetCertificatesException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(TrustCertificationResponseCodeEnum.GET_CERTIFICATES_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, "Get certificates Failed");
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(IssueCertificateException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleIssueCertificateException(IssueCertificateException ex, HttpServletRequest request) {
        logger.error("Handler for IssueCertificateException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(TrustCertificationResponseCodeEnum.ISSUE_NEW_CERTIFICATE_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, "Issue new certificate Failed");
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(RenewCertificateException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleRenewCertificateException(RenewCertificateException ex, HttpServletRequest request) {
        logger.error("Handler for RenewCertificateException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(TrustCertificationResponseCodeEnum.RENEW_CERTIFICATE_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, "Renew certificate Failed");
    }
}