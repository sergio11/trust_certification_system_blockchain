package com.dreamsoftware.tcs.web.controller.course.error;

import com.dreamsoftware.tcs.web.controller.core.SupportController;
import com.dreamsoftware.tcs.web.controller.course.CertificationCourseResponseCodeEnum;
import com.dreamsoftware.tcs.web.controller.course.error.exception.DeleteCertificationCourseException;
import com.dreamsoftware.tcs.web.controller.course.error.exception.DisableCertificationCourseException;
import com.dreamsoftware.tcs.web.controller.course.error.exception.EnableCertificationCourseException;
import com.dreamsoftware.tcs.web.controller.course.error.exception.GetCertificationCourseDetailException;
import com.dreamsoftware.tcs.web.controller.course.error.exception.PartialUpdateCertificationCourseException;
import com.dreamsoftware.tcs.web.controller.course.error.exception.SaveCertificationCourseException;
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
public class CertificationCourseErrorController extends SupportController {

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(EnableCertificationCourseException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleEnableCertificationCourseException(final EnableCertificationCourseException ex, final HttpServletRequest request) {
        log.error("Handler for EnableCertificationCourseException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(CertificationCourseResponseCodeEnum.ENABLE_CERTIFICATION_COURSE_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, resolveString("enable_certification_course_failed", request));
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(DisableCertificationCourseException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleDisableCertificationCourseException(final DisableCertificationCourseException ex, final HttpServletRequest request) {
        log.error("Handler for DisableCertificationCourseException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(CertificationCourseResponseCodeEnum.DISABLE_CERTIFICATION_COURSE_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, resolveString("disable_certification_course_failed", request));
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(DeleteCertificationCourseException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleDeleteCertificationCourseException(final DeleteCertificationCourseException ex, final HttpServletRequest request) {
        log.error("Handler for DeleteCertificationCourseException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(CertificationCourseResponseCodeEnum.DELETE_CERTIFICATION_COURSE_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, resolveString("delete_certification_course_failed", request));
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(SaveCertificationCourseException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleSaveCertificationCourseException(final SaveCertificationCourseException ex, final HttpServletRequest request) {
        log.error("Handler for SaveCertificationCourseException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(CertificationCourseResponseCodeEnum.SAVE_CERTIFICATION_COURSE_FAIL,
                HttpStatus.INTERNAL_SERVER_ERROR, resolveString("save_certification_course_failed", request));
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(GetCertificationCourseDetailException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleGetCertificationCourseDetailException(final GetCertificationCourseDetailException ex, final HttpServletRequest request) {
        log.error("Handler for GetCertificationCourseDetailException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(CertificationCourseResponseCodeEnum.GET_CERTIFICATION_COURSE_DETAIL_FAIL,
                HttpStatus.INTERNAL_SERVER_ERROR, resolveString("get_certification_course_detail_failed", request));
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(PartialUpdateCertificationCourseException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handlePartialUpdateCertificationCourseException(final PartialUpdateCertificationCourseException ex, final HttpServletRequest request) {
        log.error("Handler for PartialUpdateCertificationCourseException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(CertificationCourseResponseCodeEnum.PARTIAL_CERTIFICATION_COURSE_UPDATE_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, resolveString("partial_certification_course_update_failed", request));
    }
}
