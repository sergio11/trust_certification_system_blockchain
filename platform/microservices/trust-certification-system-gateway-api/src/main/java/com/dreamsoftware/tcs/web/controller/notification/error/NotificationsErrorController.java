package com.dreamsoftware.tcs.web.controller.notification.error;

import com.dreamsoftware.tcs.web.controller.core.SupportController;
import com.dreamsoftware.tcs.web.controller.notification.NotificationsResponseCodeEnum;
import com.dreamsoftware.tcs.web.controller.notification.error.exception.DeleteNotificationException;
import com.dreamsoftware.tcs.web.controller.notification.error.exception.GetNotificationDetailException;
import com.dreamsoftware.tcs.web.controller.notification.error.exception.NoNotificationsFoundException;
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
public class NotificationsErrorController extends SupportController {

    private static final Logger logger = LoggerFactory.getLogger(NotificationsErrorController.class);

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(NoNotificationsFoundException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleNoNotificationsFoundException(final NoNotificationsFoundException ex, final HttpServletRequest request) {
        logger.error("Handler for NotificationsFoundException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(NotificationsResponseCodeEnum.NO_NOTIFICATIONS_FOUND,
                HttpStatus.NOT_FOUND, resolveString("no_notifications_found", request));
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(GetNotificationDetailException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleGetNotificationDetailException(final GetNotificationDetailException ex, final HttpServletRequest request) {
        logger.error("Handler for GetNotificationDetailException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(NotificationsResponseCodeEnum.NO_NOTIFICATION_FOUND,
                HttpStatus.NOT_FOUND, resolveString("no_notification_found", request));
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(DeleteNotificationException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleDeleteNotificationException(final DeleteNotificationException ex, final HttpServletRequest request) {
        logger.error("Handler for DeleteNotificationException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(NotificationsResponseCodeEnum.NO_NOTIFICATION_FOUND,
                HttpStatus.NOT_FOUND, resolveString("delete_notification_failed", request));
    }
}
