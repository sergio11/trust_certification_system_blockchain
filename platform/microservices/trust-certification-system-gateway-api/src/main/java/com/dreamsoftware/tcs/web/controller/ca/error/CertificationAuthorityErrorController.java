package com.dreamsoftware.tcs.web.controller.ca.error;

import com.dreamsoftware.tcs.web.controller.ca.CertificationAuthorityResponseCodeEnum;
import com.dreamsoftware.tcs.web.controller.ca.error.exception.AddCaMemberException;
import com.dreamsoftware.tcs.web.controller.ca.error.exception.DisableCertificationAuthorityException;
import com.dreamsoftware.tcs.web.controller.ca.error.exception.DisableCertificationAuthorityMemberException;
import com.dreamsoftware.tcs.web.controller.ca.error.exception.EnableCertificationAuthorityException;
import com.dreamsoftware.tcs.web.controller.ca.error.exception.EnableCertificationAuthorityMemberException;
import com.dreamsoftware.tcs.web.controller.ca.error.exception.GetAllCertificationAuthoritiesException;
import com.dreamsoftware.tcs.web.controller.ca.error.exception.GetCertificationAuthorityException;
import com.dreamsoftware.tcs.web.controller.ca.error.exception.PartialUpdateCAException;
import com.dreamsoftware.tcs.web.controller.ca.error.exception.RemoveCaMemberException;
import com.dreamsoftware.tcs.web.controller.core.SupportController;
import com.dreamsoftware.tcs.web.core.APIResponse;
import com.dreamsoftware.tcs.web.core.ErrorResponseDTO;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CertificationAuthorityErrorController extends SupportController {

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(GetAllCertificationAuthoritiesException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleGetAllCertificationAuthoritiesException(final GetAllCertificationAuthoritiesException ex, final HttpServletRequest request) {
        log.error("Handler for GetAllCertificationAuthoritiesException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(CertificationAuthorityResponseCodeEnum.ALL_CERTIFICATION_AUTHORITIES_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, resolveString("get_all_certification_authorities_failed", request));
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(GetCertificationAuthorityException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleGetCertificationAuthorityException(final GetCertificationAuthorityException ex, final HttpServletRequest request) {
        log.error("Handler for GetCertificationAuthorityException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(CertificationAuthorityResponseCodeEnum.CERTIFICATION_AUTHORITY_FAIL,
                HttpStatus.INTERNAL_SERVER_ERROR, resolveString("get_ca_failed", request));
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(EnableCertificationAuthorityException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleEnableCertificationAuthorityException(final EnableCertificationAuthorityException ex, final HttpServletRequest request) {
        log.error("Handler for EnableCertificationAuthorityException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(CertificationAuthorityResponseCodeEnum.ENABLE_CERTIFICATION_AUTHORITY_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, resolveString("enable_ca_failed", request));
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(DisableCertificationAuthorityException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleDisableCertificationAuthorityException(final DisableCertificationAuthorityException ex, final HttpServletRequest request) {
        log.error("Handler for DisableCertificationAuthorityException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(CertificationAuthorityResponseCodeEnum.DISABLE_CERTIFICATION_AUTHORITY_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, resolveString("disable_ca_failed", request));
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(PartialUpdateCAException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handlePartialUpdateCAException(final PartialUpdateCAException ex, final HttpServletRequest request) {
        log.error("Handler for PartialUpdateCAException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(CertificationAuthorityResponseCodeEnum.PARTIAL_CERTIFICATION_AUTHORITY_UPDATE_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, resolveString("partial_certification_authority_update_failed", request));
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(AddCaMemberException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleAddCaMemberException(final AddCaMemberException ex, final HttpServletRequest request) {
        log.error("Handler for AddCaMemberException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(CertificationAuthorityResponseCodeEnum.ADD_CA_MEMBER_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, resolveString("add_ca_member_failed", request));
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(RemoveCaMemberException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleRemoveCaMemberException(final RemoveCaMemberException ex, final HttpServletRequest request) {
        log.error("Handler for RemoveCaMemberException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(CertificationAuthorityResponseCodeEnum.REMOVE_CA_MEMBER_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, resolveString("remove_ca_member_failed", request));
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(EnableCertificationAuthorityMemberException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleEnableCertificationAuthorityMemberException(final EnableCertificationAuthorityMemberException ex, final HttpServletRequest request) {
        log.error("Handler for EnableCertificationAuthorityMemberException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(CertificationAuthorityResponseCodeEnum.ENABLE_CERTIFICATION_AUTHORITY_MEMBER_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, resolveString("enable_certification_authority_member_failed", request));
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(DisableCertificationAuthorityMemberException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleDisableCertificationAuthorityMemberException(final DisableCertificationAuthorityMemberException ex, final HttpServletRequest request) {
        log.error("Handler for DisableCertificationAuthorityMemberException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(CertificationAuthorityResponseCodeEnum.DISABLE_CERTIFICATION_AUTHORITY_MEMBER_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, resolveString("disable_certification_authority_member_failed", request));
    }

}
