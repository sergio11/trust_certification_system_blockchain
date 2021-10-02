package com.dreamsoftware.blockchaingateway.web.controller.account;

import com.dreamsoftware.blockchaingateway.scheduling.events.account.UserActivatedEvent;
import com.dreamsoftware.blockchaingateway.scheduling.events.account.UserPendingValidationEvent;
import com.dreamsoftware.blockchaingateway.services.IAccountsService;
import com.dreamsoftware.blockchaingateway.web.controller.error.exception.RefreshTokenException;
import com.dreamsoftware.blockchaingateway.web.controller.error.exception.SignUpException;
import com.dreamsoftware.blockchaingateway.web.core.APIResponse;
import com.dreamsoftware.blockchaingateway.web.core.ErrorResponseDTO;
import com.dreamsoftware.blockchaingateway.web.controller.core.SupportController;
import com.dreamsoftware.blockchaingateway.web.controller.error.exception.ActivateAccountException;
import com.dreamsoftware.blockchaingateway.web.dto.request.RefreshTokenDTO;
import com.dreamsoftware.blockchaingateway.web.dto.request.SignInUserDTO;
import com.dreamsoftware.blockchaingateway.web.dto.request.SignUpUserDTO;
import com.dreamsoftware.blockchaingateway.web.dto.response.AuthenticationDTO;
import com.dreamsoftware.blockchaingateway.web.dto.response.SimpleUserDTO;
import com.dreamsoftware.blockchaingateway.web.validation.constraints.ICommonSequence;
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
import javax.validation.Valid;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.mobile.device.Device;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

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
public class AccountsController extends SupportController {

    private static final Logger logger = LoggerFactory.getLogger(AccountsController.class);

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
            if (StringUtils.isEmptyOrWhitespace(user.getLanguage())) {
                user.setLanguage(MessageFormat.format("{0}_{1}", locale.getLanguage(), locale.getCountry()));
            }

            // Configure User Agent from HTTP Header
            user.setUserAgent(userAgent);

            final SimpleUserDTO userSaved = authenticationService.signup(user);

            if (userSaved.getState().equals(SimpleUserDTO.PENDING_ACTIVATE_STATE)) {
                applicationEventPublisher.publishEvent(new UserPendingValidationEvent(this, userSaved.getIdentity()));
            }
            return responseHelper.<SimpleUserDTO>createAndSendResponse(AccountsResponseCodeEnum.SIGNUP_SUCCESS, HttpStatus.OK, userSaved);
        } catch (final Exception ex) {
            throw new SignUpException(ex.getMessage(), ex.getCause());
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
            applicationEventPublisher.publishEvent(new UserActivatedEvent(this, userActivated.getIdentity()));
            return responseHelper.<SimpleUserDTO>createAndSendResponse(AccountsResponseCodeEnum.ACTIVATE_SUCCESS, HttpStatus.OK, userActivated);
        } catch (final Exception ex) {
            throw new ActivateAccountException(ex.getMessage(), ex.getCause());
        }
    }

}
