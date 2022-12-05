package com.dreamsoftware.tcs.web.controller.users.error;

import com.dreamsoftware.tcs.web.controller.core.SupportController;
import com.dreamsoftware.tcs.web.controller.users.UsersResponseCodeEnum;
import com.dreamsoftware.tcs.web.controller.users.error.exception.*;
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
public class UserErrorController extends SupportController {

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(DeleteProfileAvatarException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleDeleteProfileAvatarException(final DeleteProfileAvatarException ex, final HttpServletRequest request) {
        log.error("Handler for DeleteProfileAvatarException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(UsersResponseCodeEnum.DELETE_AVATAR_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, resolveString("delete_profile_avatar_failed", request));
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(UploadProfileImageException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleUploadProfileImageException(final UploadProfileImageException ex, final HttpServletRequest request) {
        log.error("Handler for UploadProfileImageException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(UsersResponseCodeEnum.UPLOAD_AVATAR_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, resolveString("upload_profile_image_failed", request));
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(DeleteLoginHistoryException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleDeleteLoginHistoryException(final DeleteLoginHistoryException ex, final HttpServletRequest request) {
        log.error("Handler for DeleteLoginHistoryException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(UsersResponseCodeEnum.DELETE_LOGIN_HISTORY_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, resolveString("delete_loging_history_failed", request));
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(GetLoginHistoryException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleGetLoginHistoryException(final GetLoginHistoryException ex, final HttpServletRequest request) {
        log.error("Handler for GetLoginHistoryException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(UsersResponseCodeEnum.GET_LOGIN_HISTORY_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, resolveString("get_loging_history_failed", request));
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(GetUserDetailException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleGetUserDetailException(final GetUserDetailException ex, final HttpServletRequest request) {
        log.error("Handler for GetUserDetailException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(UsersResponseCodeEnum.GET_USER_DETAIL_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, resolveString("get_user_detail_failed", request));
    }
}
