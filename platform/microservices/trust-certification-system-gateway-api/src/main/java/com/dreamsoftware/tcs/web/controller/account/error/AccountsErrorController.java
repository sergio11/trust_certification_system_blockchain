package com.dreamsoftware.tcs.web.controller.account.error;

import com.dreamsoftware.tcs.web.controller.account.AccountsResponseCodeEnum;
import com.dreamsoftware.tcs.web.controller.account.error.exception.RefreshTokenException;
import com.dreamsoftware.tcs.web.controller.account.error.exception.SignUpException;
import com.dreamsoftware.tcs.web.core.APIResponse;
import com.dreamsoftware.tcs.web.core.ErrorResponseDTO;
import com.dreamsoftware.tcs.web.controller.core.SupportController;
import com.dreamsoftware.tcs.web.controller.account.error.exception.ActivateAccountException;
import com.dreamsoftware.tcs.web.controller.account.error.exception.SignInFacebookException;
import com.dreamsoftware.tcs.web.controller.account.error.exception.SignInGoogleException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AccountsErrorController extends SupportController {

    /**
     * Handler for Bad Credentials Exception
     *
     * @param badCredentialsException
     * @param request
     * @return
     */
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleBadCredentialsException(final BadCredentialsException badCredentialsException, final HttpServletRequest request) {
        log.error("Handler for BadCredentialsException");
        return responseHelper.createAndSendErrorResponse(AccountsResponseCodeEnum.BAD_CREDENTIALS,
                HttpStatus.BAD_REQUEST, resolveString("bad_credentials", request));
    }

    /**
     * Handler for Signup Exception
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(SignUpException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleSignUpException(final SignUpException ex, final HttpServletRequest request) {
        log.error("Handler for SignUpException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(AccountsResponseCodeEnum.SIGNUP_FAIL,
                HttpStatus.INTERNAL_SERVER_ERROR, resolveString("signup_failed", request));
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(RefreshTokenException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleRefreshTokenException(final RefreshTokenException ex, final HttpServletRequest request) {
        log.error("Handler for RefreshTokenException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(AccountsResponseCodeEnum.REFRESH_TOKEN_FAIL,
                HttpStatus.INTERNAL_SERVER_ERROR, resolveString("refresh_token_failed", request));
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(ActivateAccountException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleActivateAccountException(final ActivateAccountException ex, final HttpServletRequest request) {
        log.error("Handler for ActivateAccountException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(AccountsResponseCodeEnum.ACTIVATE_FAIL,
                HttpStatus.INTERNAL_SERVER_ERROR, resolveString("user_activation_failed", request));
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(SignInFacebookException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleSignInFacebookException(final SignInFacebookException ex, final HttpServletRequest request) {
        log.error("Handler for SignInFacebookException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(AccountsResponseCodeEnum.SIGNIN_VIA_FACEBOOK_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, resolveString("sign_in_facebook_failed", request));
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(SignInGoogleException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleSignInGoogleException(final SignInGoogleException ex, final HttpServletRequest request) {
        log.error("Handler for SignInGoogleException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(AccountsResponseCodeEnum.SIGNIN_VIA_GOOGLE_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, resolveString("sign_in_google_failed", request));
    }

}
