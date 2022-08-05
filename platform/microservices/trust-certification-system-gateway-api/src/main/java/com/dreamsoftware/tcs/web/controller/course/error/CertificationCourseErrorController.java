package com.dreamsoftware.tcs.web.controller.course.error;

import com.dreamsoftware.tcs.web.controller.core.SupportController;
import com.dreamsoftware.tcs.web.controller.course.CertificationCourseResponseCodeEnum;
import com.dreamsoftware.tcs.web.controller.course.error.exception.DisableCertificationCourseException;
import com.dreamsoftware.tcs.web.controller.course.error.exception.EnableCertificationCourseException;
import com.dreamsoftware.tcs.web.controller.course.error.exception.GetCertificationCourseDetailException;
import com.dreamsoftware.tcs.web.controller.course.error.exception.SaveCertificationCourseException;
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
public class CertificationCourseErrorController extends SupportController {

    private static final Logger logger = LoggerFactory.getLogger(CertificationCourseErrorController.class);

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(EnableCertificationCourseException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleEnableCertificationCourseException(final EnableCertificationCourseException ex, final HttpServletRequest request) {
        logger.error("Handler for EnableCertificationCourseException -> " + ex.getMessage());
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
        logger.error("Handler for DisableCertificationCourseException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(CertificationCourseResponseCodeEnum.DISABLE_CERTIFICATION_COURSE_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, resolveString("disable_certification_course_failed", request));
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
        logger.error("Handler for SaveCertificationCourseException -> " + ex.getMessage());
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
        logger.error("Handler for GetCertificationCourseDetailException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(CertificationCourseResponseCodeEnum.GET_CERTIFICATION_COURSE_DETAIL_FAIL,
                HttpStatus.INTERNAL_SERVER_ERROR, resolveString("get_certification_course_detail_failed", request));
    }
}
