package com.dreamsoftware.tcs.web.controller.account;

import com.dreamsoftware.tcs.services.IAccountsService;
import com.dreamsoftware.tcs.web.controller.account.error.exception.*;
import com.dreamsoftware.tcs.web.core.APIResponse;
import com.dreamsoftware.tcs.web.core.ErrorResponseDTO;
import com.dreamsoftware.tcs.web.controller.core.SupportController;
import com.dreamsoftware.tcs.web.dto.request.*;
import com.dreamsoftware.tcs.web.dto.response.AuthenticationDTO;
import com.dreamsoftware.tcs.web.dto.response.SimpleUserDTO;
import com.dreamsoftware.tcs.web.validation.constraints.ICommonSequence;
import com.dreamsoftware.tcs.web.validation.constraints.group.IResetPasswordSequence;
import com.restfb.exception.FacebookOAuthException;
import io.micrometer.core.instrument.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.text.MessageFormat;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.mobile.device.Device;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Accounts Controller
 *
 * @author ssanchez
 */
@RestController
@Validated
@RequestMapping("/api/v1/accounts/")
@Tag(name = "accounts", description = "/api/v1/accounts/ (Code Response interval -> 1XX)")
@RequiredArgsConstructor
@Slf4j
public class AccountsController extends SupportController {

    /**
     * Accounts Service
     */
    private final IAccountsService accountsService;

    /**
     * Create Authentication Token
     *
     * @param signInUserDTO
     * @param device
     * @param userAgent
     * @param request
     * @return
     * @throws Throwable
     */
    @Operation(summary = "SIGN_IN - Create Access Token (JWT format)", description = "Create Access Token (JWT format)", tags = {"accounts"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication Success",
                    content = @Content(schema = @Schema(implementation = AuthenticationDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Credentials",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @SecurityRequirements
    @RequestMapping(value = "/signin", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<AuthenticationDTO>> signin(
            @Parameter(description = "User authentication data. Cannot null or empty.",
                    required = true, schema = @Schema(implementation = SignInUserDTO.class))
            @Validated(ICommonSequence.class) SignInUserDTO signInUserDTO,
            @Parameter(hidden = true) @RequestHeader("User-Agent") String userAgent,
            @Parameter(hidden = true) Device device,
            @Parameter(hidden = true) HttpServletRequest request) throws Throwable {
        try {
            // Configure remote addr
            signInUserDTO.setRemoteAddr(request.getRemoteAddr());
            // Configure User Agent
            signInUserDTO.setUserAgent(userAgent);
            final AuthenticationDTO authenticationDTO = accountsService.signin(signInUserDTO, device);
            return responseHelper.createAndSendResponse(AccountsResponseCodeEnum.SIGNIN_SUCCESS,
                    HttpStatus.OK, authenticationDTO);
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Exception ex) {
            throw new SignInException(ex.getMessage(), ex.getCause());
        }
    }

    /**
     * Create Authentication Token for Admin users
     *
     * @param signInAdminUserDTO
     * @param userAgent
     * @param device
     * @param request
     * @return
     * @throws Throwable
     */
    @Operation(summary = "SIGN_IN_ADMIN - Create Access Token for an Admin user (JWT format)", description = "Create Access Token for an Admin user (JWT format)", tags = {"accounts"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication Success",
                    content = @Content(schema = @Schema(implementation = AuthenticationDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Credentials",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @SecurityRequirements
    @RequestMapping(value = "/signin/admin", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<AuthenticationDTO>> signinAdmin(
            @Parameter(description = "Admin authentication data. Cannot null or empty.",
                    required = true, schema = @Schema(implementation = SignInAdminUserDTO.class))
            @Validated(ICommonSequence.class) SignInAdminUserDTO signInAdminUserDTO,
            @Parameter(hidden = true) @RequestHeader("User-Agent") String userAgent,
            @Parameter(hidden = true) Device device,
            @Parameter(hidden = true) HttpServletRequest request) throws Throwable {
        try {
            // Configure remote addr
            signInAdminUserDTO.setRemoteAddr(request.getRemoteAddr());
            signInAdminUserDTO.setUserAgent(userAgent);
            final AuthenticationDTO authenticationDTO = accountsService.signin(signInAdminUserDTO, device);
            return responseHelper.createAndSendResponse(AccountsResponseCodeEnum.SIGNIN_ADMIN_SUCCESS,
                    HttpStatus.OK, authenticationDTO);
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Exception ex) {
            throw new SignInException(ex.getMessage(), ex.getCause());
        }
    }

    /**
     * @param externalProviderAuthRequest
     * @param userAgent
     * @param locale
     * @param device
     * @param request
     * @return
     * @throws Throwable
     */
    @Operation(summary = "SIGN_IN_EXTERNAL_ACCOUNT - Get Authorization Token through external social account", description = "Get Authorization Token through external social account, The user account is automatically validated", tags = {"accounts"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sign in through external social account Success",
                    content = @Content(schema = @Schema(implementation = AuthenticationDTO.class))),
            @ApiResponse(responseCode = "500", description = "Sign in through external social account fail",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @SecurityRequirements
    @RequestMapping(value = "/signin/external", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<AuthenticationDTO>> signinViaFacebook(
            @Parameter(description = "Authentication Data. Cannot null or empty.",
                    required = true, schema = @Schema(implementation = SignInUserExternalProviderDTO.class))
            @Valid SignInUserExternalProviderDTO externalProviderAuthRequest,
            @Parameter(hidden = true) @RequestHeader("User-Agent") String userAgent,
            @Parameter(hidden = true) Locale locale,
            @Parameter(hidden = true) Device device,
            @Parameter(hidden = true) HttpServletRequest request) throws Throwable {
        try {
            externalProviderAuthRequest.setRemoteAddr(request.getRemoteAddr());
            externalProviderAuthRequest.setUserAgent(userAgent);
            final AuthenticationDTO authenticationDTO = accountsService.signin(externalProviderAuthRequest, device);
            return responseHelper.createAndSendResponse(AccountsResponseCodeEnum.SIGN_IN_EXTERNAL_ACCOUNT_SUCCESS,
                    HttpStatus.OK, authenticationDTO);
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final FacebookOAuthException ex) {
            throw new SignInExternalProviderException(ex.getMessage(), ex.getCause());
        }
    }

    /**
     * @param refreshTokenDTO
     * @return
     */
    @Operation(summary = "REFRESH - Refresh Access Token (JWT format)", description = "Refresh Access Token (JWT format)", tags = {"accounts"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Refresh Token Success",
                    content = @Content(schema = @Schema(implementation = AuthenticationDTO.class))),
            @ApiResponse(responseCode = "500", description = "Refresh Token Fail",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @SecurityRequirements
    @RequestMapping(value = "/refresh", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<AuthenticationDTO>> refresh(
            @Parameter(description = "Current Authentication Data. Cannot null or empty.",
                    required = true, schema = @Schema(implementation = RefreshTokenDTO.class))
            @Valid RefreshTokenDTO refreshTokenDTO) {
        try {
            final AuthenticationDTO authenticationDTO = accountsService.refresh(refreshTokenDTO.getToken());
            return responseHelper.createAndSendResponse(
                    AccountsResponseCodeEnum.REFRESH_TOKEN, HttpStatus.OK, authenticationDTO);
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Exception ex) {
            throw new RefreshTokenException(ex.getMessage(), ex);
        }
    }

    /**
     * @param user
     * @param userAgent
     * @param locale
     * @return
     * @throws Throwable
     */
    @Operation(summary = "SIGN_UP - Create user into platform", description = "Create user into platform, It is necessary to verify the account through the email sent.", tags = {"accounts"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sign Up Success",
                    content = @Content(schema = @Schema(implementation = SimpleUserDTO.class))),
            @ApiResponse(responseCode = "500", description = "Sign up fail",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @SecurityRequirements
    @RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<SimpleUserDTO>> signup(
            @Parameter(name = "user", description = "New User data. Cannot null or empty.",
                    required = true, schema = @Schema(implementation = SignUpUserDTO.class))
            @Validated(ICommonSequence.class) SignUpUserDTO user,
            @Parameter(hidden = true) @RequestHeader("User-Agent") String userAgent,
            @Parameter(hidden = true) Locale locale) throws Throwable {

        try {

            // Configure ISO3 Language
            if (StringUtils.isBlank(user.getLanguage())) {
                user.setLanguage(MessageFormat.format("{0}_{1}", locale.getLanguage(), locale.getCountry()));
            }
            // Configure User Agent from HTTP Header
            user.setUserAgent(userAgent);
            final SimpleUserDTO userSaved = accountsService.signup(user);
            return responseHelper.createAndSendResponse(AccountsResponseCodeEnum.SIGNUP_SUCCESS,
                    HttpStatus.OK, userSaved);
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Exception ex) {
            ex.printStackTrace();
            log.debug("signup exception: " + ex);
            throw new SignUpException(ex.getMessage(), ex);
        }
    }

    /**
     * @param user
     * @param userAgent
     * @param locale
     * @return
     * @throws Throwable
     */
    @Operation(summary = "SIGN_UP_EXTERNAL_ACCOUNT - Create user into platform through external social account", description = "Create user into platform through external social account.", tags = {"accounts"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sign Up Success",
                    content = @Content(schema = @Schema(implementation = SimpleUserDTO.class))),
            @ApiResponse(responseCode = "500", description = "Sign up fail",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @SecurityRequirements
    @RequestMapping(value = "/signup/external", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<SimpleUserDTO>> signupSocial(
            @Parameter(name = "user", description = "New User data. Cannot null or empty.",
                    required = true, schema = @Schema(implementation = SignUpExternalProviderDTO.class))
            @Validated(ICommonSequence.class) SignUpExternalProviderDTO user,
            @Parameter(hidden = true) @RequestHeader("User-Agent") String userAgent,
            @Parameter(hidden = true) Locale locale) throws Throwable {
        try {
            user.setUserAgent(userAgent);
            user.setLanguage(MessageFormat.format("{0}_{1}", locale.getLanguage(), locale.getCountry()));
            // Configure User Agent from HTTP Header
            final SimpleUserDTO userSaved = accountsService.signup(user);
            return responseHelper.createAndSendResponse(AccountsResponseCodeEnum.SIGNUP_EXTERNAL_ACCOUNT_SUCCESS,
                    HttpStatus.OK, userSaved);
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Exception ex) {
            ex.printStackTrace();
            log.debug("signup exception: " + ex);
            throw new SignUpExternalProviderException(ex.getMessage(), ex);
        }
    }

    /**
     * @param ca
     * @param userAgent
     * @param locale
     * @return
     * @throws Throwable
     */
    @Operation(summary = "SIGN_UP_CA_ADMIN - Allows you to register a Certification Authority along with the administrator user", description = "Allows you to register a Certification Authority along with the administrator user.", tags = {"accounts"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sign Up Success",
                    content = @Content(schema = @Schema(implementation = SimpleUserDTO.class))),
            @ApiResponse(responseCode = "500", description = "Sign up fail",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @SecurityRequirements
    @RequestMapping(value = "/signup/ca", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<SimpleUserDTO>> signup(
            @Parameter(name = "ca", description = "Certification Authority Data. Cannot null or empty.",
                    required = true, schema = @Schema(implementation = SignUpAsCaAdminDTO.class))
            @Validated(ICommonSequence.class) SignUpAsCaAdminDTO ca,
            @Parameter(hidden = true) @RequestHeader("User-Agent") String userAgent,
            @Parameter(hidden = true) Locale locale) throws Throwable {

        try {

            // Configure ISO3 Language
            if (StringUtils.isBlank(ca.getLanguage())) {
                ca.setLanguage(MessageFormat.format("{0}_{1}", locale.getLanguage(), locale.getCountry()));
            }
            // Configure User Agent from HTTP Header
            ca.setUserAgent(userAgent);
            final SimpleUserDTO userSaved = accountsService.signup(ca);
            return responseHelper.createAndSendResponse(AccountsResponseCodeEnum.SIGNUP_AS_CA_ADMIN_SUCCESS,
                    HttpStatus.OK, userSaved);
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Exception ex) {
            log.debug("signup exception: " + ex);
            throw new SignUpException(ex.getMessage(), ex);
        }
    }

    /**
     * Activate Account
     *
     * @param token
     * @return
     */
    @Operation(summary = "ACTIVATE - Activate user into platform", description = "Activate user into platform, It is necessary to verify the account through the email sent.", tags = {"accounts"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activate Success",
                    content = @Content(schema = @Schema(implementation = SimpleUserDTO.class))),
            @ApiResponse(responseCode = "500", description = "Activate fail",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @SecurityRequirements
    @RequestMapping(value = "/activate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<SimpleUserDTO>> activate(
            @Parameter(name = "token", description = "Activate Token, it can not be null",
                    required = true, schema = @Schema(implementation = String.class))
            @RequestParam(name = "token") final String token) {
        try {
            final SimpleUserDTO userActivated = accountsService.activate(token);
            return responseHelper.createAndSendResponse(AccountsResponseCodeEnum.ACTIVATE_SUCCESS, HttpStatus.OK, userActivated);
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
            throw new ActivateAccountException(ex.getMessage(), ex.getCause());
        }
    }

    /**
     * Reset Password
     *
     * @param resetPasswordRequestDTO
     * @param request
     * @return
     */
    @Operation(summary = "RESET_PASSWORD - Reset Password", description = "Reset Password", tags = {"accounts"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reset Password Success",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Reset Password fail",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @SecurityRequirements
    @RequestMapping(value = "/reset-password", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<String>> resetPassword(
            @Parameter(name = "reset_password_request", description = "Reset Password Request. Cannot null or empty.",
                    required = true, schema = @Schema(implementation = ResetPasswordRequestDTO.class))
            @Validated(IResetPasswordSequence.class) ResetPasswordRequestDTO resetPasswordRequestDTO,
            @Parameter(hidden = true) HttpServletRequest request) {

        try {
            log.debug("resetPassword for -> " + resetPasswordRequestDTO.getEmail());
            accountsService.resetPassword(resetPasswordRequestDTO.getEmail());
            return responseHelper.createAndSendResponse(
                    AccountsResponseCodeEnum.RESET_PASSWORD_REQUEST_SUCCESS,
                    HttpStatus.OK, resolveString("user_password_request_reset_success", request));
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Exception ex) {
            throw new ResetPasswordRequestException(ex.getMessage(), ex.getCause());
        }
    }

    /**
     *
     * @param changePasswordRequestDTO
     * @param request
     * @return
     */
    @Operation(summary = "CHANGE_PASSWORD - Change Password", description = "Change Password", tags = {"accounts"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Change Password Success",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Change Password fail",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @SecurityRequirements
    @RequestMapping(value = "/change-password", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<String>> changePassword(
            @Parameter(name = "change_password_request", description = "Change Password Request. Cannot null or empty.",
                    required = true, schema = @Schema(implementation = ChangePasswordRequestDTO.class))
            @Validated(ICommonSequence.class) ChangePasswordRequestDTO changePasswordRequestDTO,
            @Parameter(hidden = true) HttpServletRequest request) {
        try {
            accountsService.changePassword(changePasswordRequestDTO);
            return responseHelper.createAndSendResponse(
                    AccountsResponseCodeEnum.CHANGE_PASSWORD_REQUEST_SUCCESS,
                    HttpStatus.OK, resolveString("change_user_password_success", request));
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Exception ex) {
            throw new ChangePasswordRequestException(ex.getMessage(), ex.getCause());
        }
    }

}
