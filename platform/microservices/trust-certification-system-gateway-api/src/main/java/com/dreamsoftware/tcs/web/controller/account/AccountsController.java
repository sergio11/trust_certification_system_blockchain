package com.dreamsoftware.tcs.web.controller.account;

import com.dreamsoftware.tcs.services.IAccountsService;
import com.dreamsoftware.tcs.web.controller.account.error.exception.RefreshTokenException;
import com.dreamsoftware.tcs.web.controller.account.error.exception.SignUpException;
import com.dreamsoftware.tcs.web.core.APIResponse;
import com.dreamsoftware.tcs.web.core.ErrorResponseDTO;
import com.dreamsoftware.tcs.web.controller.core.SupportController;
import com.dreamsoftware.tcs.web.controller.account.error.exception.ActivateAccountException;
import com.dreamsoftware.tcs.web.controller.account.error.exception.ResetPasswordRequestException;
import com.dreamsoftware.tcs.web.dto.internal.PasswordResetTokenDTO;
import com.dreamsoftware.tcs.web.dto.request.RefreshTokenDTO;
import com.dreamsoftware.tcs.web.dto.request.ResetPasswordRequestDTO;
import com.dreamsoftware.tcs.web.dto.request.SignInUserDTO;
import com.dreamsoftware.tcs.web.dto.request.SignUpUserDTO;
import com.dreamsoftware.tcs.web.dto.response.AuthenticationDTO;
import com.dreamsoftware.tcs.web.dto.response.SimpleUserDTO;
import com.dreamsoftware.tcs.web.validation.constraints.ICommonSequence;
import com.dreamsoftware.tcs.web.validation.constraints.group.IResetPasswordSequence;
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
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.security.authentication.BadCredentialsException;
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

    private final IAccountsService authenticationService;

    /**
     * Create Authentication Token
     *
     * @param signInUserDTO
     * @param device
     * @param userAgent
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
            @Parameter(hidden = true) Device device) throws Throwable {

        // Configure User Agent
        signInUserDTO.setUserAgent(userAgent);

        return Optional.ofNullable(authenticationService.signin(signInUserDTO, device))
                .map(jwtResponse -> responseHelper.createAndSendResponse(AccountsResponseCodeEnum.SIGNIN_SUCCESS, HttpStatus.OK, jwtResponse))
                .orElseThrow(() -> {
                    throw new BadCredentialsException("User Not Found");
                });
    }

    /**
     *
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
            final AuthenticationDTO authenticationDTO = authenticationService.refresh(refreshTokenDTO.getToken());
            return responseHelper.createAndSendResponse(
                    AccountsResponseCodeEnum.REFRESH_TOKEN, HttpStatus.OK, authenticationDTO);
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
            final SimpleUserDTO userSaved = authenticationService.signup(user);
            return responseHelper.<SimpleUserDTO>createAndSendResponse(AccountsResponseCodeEnum.SIGNUP_SUCCESS, HttpStatus.OK, userSaved);
        } catch (final Exception ex) {
            ex.printStackTrace();
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
            @RequestParam(name = "token") final String token) {
        try {
            final SimpleUserDTO userActivated = authenticationService.activate(token);
            return responseHelper.<SimpleUserDTO>createAndSendResponse(AccountsResponseCodeEnum.ACTIVATE_SUCCESS, HttpStatus.OK, userActivated);
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
            authenticationService.resetPassword(resetPasswordRequestDTO.getEmail());
            return responseHelper.<String>createAndSendResponse(
                    AccountsResponseCodeEnum.RESET_PASSWORD_REQUEST_SUCCESS,
                    HttpStatus.OK, resolveString("user_password_request_reset_success", request));

        } catch (final Exception ex) {
            throw new ResetPasswordRequestException(ex.getMessage(), ex.getCause());
        }

    }

}
