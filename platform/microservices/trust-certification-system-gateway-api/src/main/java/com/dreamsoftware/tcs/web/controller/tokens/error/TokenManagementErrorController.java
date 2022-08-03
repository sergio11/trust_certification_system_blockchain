package com.dreamsoftware.tcs.web.controller.tokens.error;

import com.dreamsoftware.tcs.web.controller.core.SupportController;
import com.dreamsoftware.tcs.web.controller.tokens.TokenManagementResponseCodeEnum;
import com.dreamsoftware.tcs.web.controller.tokens.error.exception.ConfirmOrderException;
import com.dreamsoftware.tcs.web.controller.tokens.error.exception.GetClientTokensException;
import com.dreamsoftware.tcs.web.controller.tokens.error.exception.GetMyTokensException;
import com.dreamsoftware.tcs.web.controller.tokens.error.exception.PlaceTokensOrderException;
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
public class TokenManagementErrorController extends SupportController {

    private static final Logger logger = LoggerFactory.getLogger(TokenManagementErrorController.class);

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(GetMyTokensException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleGetMyTokensException(GetMyTokensException ex, HttpServletRequest request) {
        logger.error("Handler for GetMyTokensException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(TokenManagementResponseCodeEnum.GET_MY_TOKENS_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR, "An error ocurred while getting tokens");
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(GetClientTokensException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleGetClientTokensException(GetClientTokensException ex, HttpServletRequest request) {
        logger.error("Handler for GetClientTokensException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(TokenManagementResponseCodeEnum.GET_CLIENT_TOKENS_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR, "An error ocurred while getting client tokens");
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(PlaceTokensOrderException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handlePlaceTokensOrderException(PlaceTokensOrderException ex, HttpServletRequest request) {
        logger.error("Handler for PlaceTokensOrderException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(TokenManagementResponseCodeEnum.PLACE_TOKENS_ORDER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR, "An error ocurred while place order");
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(ConfirmOrderException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleConfirmOrderException(ConfirmOrderException ex, HttpServletRequest request) {
        logger.error("Handler for ConfirmOrderException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(TokenManagementResponseCodeEnum.CONFIRM_ORDER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR, "An error ocurred while confirm order");
    }
}
