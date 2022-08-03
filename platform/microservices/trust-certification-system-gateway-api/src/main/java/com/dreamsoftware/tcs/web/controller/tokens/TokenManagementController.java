package com.dreamsoftware.tcs.web.controller.tokens;

import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.services.ITokenManagementService;
import com.dreamsoftware.tcs.web.controller.core.SupportController;
import com.dreamsoftware.tcs.web.controller.tokens.error.exception.GetClientTokensException;
import com.dreamsoftware.tcs.web.controller.tokens.error.exception.GetMyTokensException;
import com.dreamsoftware.tcs.web.controller.tokens.error.exception.PlaceTokensOrderException;
import com.dreamsoftware.tcs.web.core.APIResponse;
import com.dreamsoftware.tcs.web.dto.request.PlaceTokensOrderRequestDTO;
import com.dreamsoftware.tcs.web.dto.response.OrderDetailDTO;
import com.dreamsoftware.tcs.web.security.directives.CurrentUser;
import com.dreamsoftware.tcs.web.security.directives.OnlyAccessForAdmin;
import com.dreamsoftware.tcs.web.security.userdetails.ICommonUserDetailsAware;
import com.dreamsoftware.tcs.web.validation.constraints.ICommonSequence;
import com.dreamsoftware.tcs.web.validation.constraints.ShouldBeAValidObjectId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Notifications Controller
 *
 * @author ssanchez
 */
@RestController
@Validated
@RequestMapping("/api/v1/tokens-management/")
@Tag(name = "tokens-management", description = "/api/v1/tokens-management/ (Code Response interval -> 7XX)")
@RequiredArgsConstructor
public class TokenManagementController extends SupportController {

    /**
     * Token Management Service
     */
    private final ITokenManagementService tokenManagementService;

    /**
     *
     * @param selfUser
     * @return
     * @throws Throwable
     */
    @Operation(summary = "GET_MY_TOKENS - Get my current balance of TCS tokens", description = "Get my current balance of TCS tokens", tags = {"tokens-manadgement"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "My Current balance of TCS tokens",
                content = @Content(
                        schema = @Schema(implementation = Long.class)))
    })
    @RequestMapping(value = {"/"}, method = RequestMethod.GET, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponse<Long>> getMyTokens(
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<ObjectId> selfUser
    ) throws Throwable {
        try {
            final Long myTokens = tokenManagementService.getMyTokens(selfUser.getWalletHash());
            return responseHelper.<Long>createAndSendResponse(
                    TokenManagementResponseCodeEnum.GET_MY_TOKENS_SUCCESS,
                    HttpStatus.OK, myTokens);
        } catch (final RepositoryException ex) {
            throw new GetMyTokensException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param placeTokensOrderRequestDTO
     * @param selfUser
     * @return
     * @throws Throwable
     */
    @Operation(summary = "PLACE_TOKENS_ORDER - Place Tokens Order", description = "Place Tokens Order", tags = {"tokens-manadgement"})
    @RequestMapping(value = "/order", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<OrderDetailDTO>> placeTokensOrder(
            @Validated(ICommonSequence.class) PlaceTokensOrderRequestDTO placeTokensOrderRequestDTO,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<ObjectId> selfUser
    ) throws Throwable {
        try {
            placeTokensOrderRequestDTO.setWalletHash(selfUser.getWalletHash());
            final OrderDetailDTO orderDetail = tokenManagementService.placeTokensOrder(placeTokensOrderRequestDTO);
            return responseHelper.<OrderDetailDTO>createAndSendResponse(TokenManagementResponseCodeEnum.PLACE_TOKENS_ORDER_SUCCESS,
                    HttpStatus.OK, orderDetail);
        } catch (final Exception ex) {
            throw new PlaceTokensOrderException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param id
     * @param selfUser
     * @return
     * @throws Throwable
     */
    @Operation(summary = "GET_CLIENT_TOKENS - Get client balance of TCS tokens", description = "Get my current balance of TCS tokens", tags = {"tokens-manadgement"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Client balance of TCS tokens",
                content = @Content(
                        schema = @Schema(implementation = Long.class)))
    })
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.GET, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @OnlyAccessForAdmin
    public ResponseEntity<APIResponse<Long>> getClientTokens(
            @Parameter(name = "id", description = "User Id", required = true)
            @Valid @ShouldBeAValidObjectId(message = "user_id_valid") @PathVariable("id") String id,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<ObjectId> selfUser
    ) throws Throwable {
        try {
            final Long tokens = tokenManagementService.getTokensByClient(selfUser.getWalletHash(), new ObjectId(id));
            return responseHelper.<Long>createAndSendResponse(
                    TokenManagementResponseCodeEnum.GET_CLIENT_TOKENS_SUCCESS,
                    HttpStatus.OK, tokens);
        } catch (final RepositoryException ex) {
            throw new GetClientTokensException(ex.getMessage(), ex);
        }
    }

    /**
     * Private Methods
     */
    private URI buildReturnUrl(HttpServletRequest request) {
        try {
            URI requestUri = URI.create(request.getRequestURL().toString());
            return new URI(requestUri.getScheme(),
                    requestUri.getUserInfo(),
                    requestUri.getHost(),
                    requestUri.getPort(),
                    "/orders/capture",
                    null, null);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
