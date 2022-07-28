package com.dreamsoftware.tcs.web.controller.account.error;

import com.dreamsoftware.tcs.web.controller.account.AccountsResponseCodeEnum;
import com.dreamsoftware.tcs.web.controller.error.exception.RefreshTokenException;
import com.dreamsoftware.tcs.web.controller.error.exception.SignUpException;
import com.dreamsoftware.tcs.web.core.APIResponse;
import com.dreamsoftware.tcs.web.core.ErrorResponseDTO;
import com.dreamsoftware.tcs.web.controller.core.SupportController;
import com.dreamsoftware.tcs.web.controller.error.exception.ActivateAccountException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Accounts Error Contoller
 *
 * @author ssanchez
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AccountsErrorController extends SupportController {

    private static Logger logger = LoggerFactory.getLogger(AccountsErrorController.class);

    /**
     * Handler for Bad Credentials Exception
     *
     * @param badCredentialsException
     * @param request
     * @return
     */
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleBadCredentialsException(BadCredentialsException badCredentialsException, HttpServletRequest request) {
        logger.error("Handler for BadCredentialsException");
        return responseHelper.createAndSendErrorResponse(AccountsResponseCodeEnum.BAD_CREDENTIALS,
                HttpStatus.BAD_REQUEST, "User Bad Credentials Error");
    }

    /**
     * Handler for Signup Exception
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(SignUpException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleSignUpException(SignUpException ex) {
        logger.error("Handler for SignUpException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(AccountsResponseCodeEnum.SIGNUP_FAIL, HttpStatus.INTERNAL_SERVER_ERROR, "User Signup Failed");
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(RefreshTokenException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleRefreshTokenException(RefreshTokenException ex, HttpServletRequest request) {
        logger.error("Handler for RefreshTokenException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(AccountsResponseCodeEnum.REFRESH_TOKEN_FAIL,
                HttpStatus.INTERNAL_SERVER_ERROR, "User Refresh Token Fail");
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(ActivateAccountException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleActivateAccountException(ActivateAccountException ex, HttpServletRequest request) {
        logger.error("Handler for ActivateAccountException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(AccountsResponseCodeEnum.ACTIVATE_FAIL,
                HttpStatus.INTERNAL_SERVER_ERROR, "User activation Fail");
    }

}
