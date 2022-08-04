package com.dreamsoftware.tcs.web.controller.certification.error;

import com.dreamsoftware.tcs.web.controller.certification.TrustCertificationResponseCodeEnum;
import com.dreamsoftware.tcs.web.controller.certification.error.exception.AcceptCertificateRequestException;
import com.dreamsoftware.tcs.web.controller.certification.error.exception.DisableCertificateException;
import com.dreamsoftware.tcs.web.controller.certification.error.exception.DownloadCertificateFileException;
import com.dreamsoftware.tcs.web.controller.certification.error.exception.EnableCertificateException;
import com.dreamsoftware.tcs.web.controller.certification.error.exception.GetCertificateIssuedDetailException;
import com.dreamsoftware.tcs.web.controller.certification.error.exception.GetCertificatesException;
import com.dreamsoftware.tcs.web.controller.certification.error.exception.GetCertificatesIssuanceRequestsException;
import com.dreamsoftware.tcs.web.controller.certification.error.exception.IssueCertificateRequestException;
import com.dreamsoftware.tcs.web.controller.certification.error.exception.RejectCertificateRequestException;
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

    private static final Logger logger = LoggerFactory.getLogger(TrustCertificationErrorController.class);

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
    @ExceptionHandler(IssueCertificateRequestException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleIssueCertificateException(IssueCertificateRequestException ex, HttpServletRequest request) {
        logger.error("Handler for IssueCertificateException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(TrustCertificationResponseCodeEnum.NEW_ISSUE_CERTIFICATE_REQUEST_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, "Issue new certificate Failed");
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(AcceptCertificateRequestException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleAcceptCertificateRequestException(AcceptCertificateRequestException ex, HttpServletRequest request) {
        logger.error("Handler for AcceptCertificateRequestException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(TrustCertificationResponseCodeEnum.ACCEPT_CERTIFICATE_REQUEST_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, "Accept certificate request failed");
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(RejectCertificateRequestException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleRejectCertificateRequestException(RejectCertificateRequestException ex, HttpServletRequest request) {
        logger.error("Handler for RejectCertificateRequestException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(TrustCertificationResponseCodeEnum.REJECT_CERTIFICATE_REQUEST_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, "Reject Certificate Request Failed");
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

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(GetCertificatesIssuanceRequestsException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleGetCertificatesIssuanceRequestsException(GetCertificatesIssuanceRequestsException ex, HttpServletRequest request) {
        logger.error("Handler for GetCertificatesIssuanceRequestsException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(TrustCertificationResponseCodeEnum.GET_CERTIFICATES_ISSUANCE_REQUEST_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, "Get certificates Issuance requests Failed");
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(DownloadCertificateFileException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleDownloadCertificateFileException(DownloadCertificateFileException ex, HttpServletRequest request) {
        logger.error("Handler for DownloadCertificateFileException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(TrustCertificationResponseCodeEnum.DOWNLOAD_CERTIFICATE_FILE_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, "Download Certificate File failed");
    }

}
