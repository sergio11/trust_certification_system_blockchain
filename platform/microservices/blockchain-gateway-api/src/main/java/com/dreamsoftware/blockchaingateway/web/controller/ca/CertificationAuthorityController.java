package com.dreamsoftware.blockchaingateway.web.controller.ca;

import com.dreamsoftware.blockchaingateway.services.ICertificationAuthorityService;
import com.dreamsoftware.blockchaingateway.web.controller.ca.error.exception.GetCertificationAuthorityException;
import com.dreamsoftware.blockchaingateway.web.core.APIResponse;
import com.dreamsoftware.blockchaingateway.web.core.ErrorResponseDTO;
import com.dreamsoftware.blockchaingateway.web.controller.core.SupportController;
import com.dreamsoftware.blockchaingateway.web.dto.response.CertificationAuthorityDetailDTO;
import com.dreamsoftware.blockchaingateway.web.security.directives.CurrentUser;
import com.dreamsoftware.blockchaingateway.web.security.directives.OnlyAccessForAdmin;
import com.dreamsoftware.blockchaingateway.web.security.directives.OnlyAccessForCA;
import com.dreamsoftware.blockchaingateway.web.security.userdetails.ICommonUserDetailsAware;
import com.dreamsoftware.blockchaingateway.web.validation.constraints.ShouldBeAValidObjectId;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author ssanchez
 */
@RestController
@Validated
@RequestMapping("/api/v1/ca/")
@Tag(name = "ca", description = "/api/v1/ca/ (Code Response interval -> 2XX)")
@RequiredArgsConstructor
public class CertificationAuthorityController extends SupportController {

    /**
     * Certification Authority Service
     */
    private final ICertificationAuthorityService certificationAuthorityService;

    /**
     * Get Certification Authority Detail
     *
     * @param id
     * @return
     * @throws Throwable
     */
    @Operation(summary = "GET_CA_DETAIL - Get Certification Authority Detail", description = "Get Certification Authority Detail", tags = {"CA"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Simple Profile Detail",
                content = @Content(schema = @Schema(implementation = CertificationAuthorityDetailDTO.class))),
        @ApiResponse(responseCode = "404", description = "User Not Found",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForAdmin
    public ResponseEntity<APIResponse<CertificationAuthorityDetailDTO>> getDetailById(
            @Parameter(name = "id", description = "CA Id", required = true)
            @Valid @ShouldBeAValidObjectId(message = "user.id.not.valid") @PathVariable("id") String id
    ) throws Throwable {

        try {
            final CertificationAuthorityDetailDTO caDetailDTO = certificationAuthorityService.getDetail(id);
            return responseHelper.<CertificationAuthorityDetailDTO>createAndSendResponse(
                    CertificationAuthorityResponseCodeEnum.CERTIFICATION_AUTHORITY_DETAIL,
                    HttpStatus.OK, caDetailDTO);
        } catch (final Exception ex) {
            throw new GetCertificationAuthorityException(ex.getMessage(), ex);
        }
    }

    /**
     * Get Certification Authority Detail
     *
     * @param selfUser
     * @return
     * @throws Throwable
     */
    @Operation(summary = "GET_CA_DETAIL - Get Curretn Certification Authority Detail", description = "Get Certification Authority Detail", tags = {"CA"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Simple Profile Detail",
                content = @Content(schema = @Schema(implementation = CertificationAuthorityDetailDTO.class))),
        @ApiResponse(responseCode = "404", description = "User Not Found",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = {"/"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForCA
    public ResponseEntity<APIResponse<CertificationAuthorityDetailDTO>> getDetail(
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<ObjectId> selfUser
    ) throws Throwable {

        try {
            final CertificationAuthorityDetailDTO caDetailDTO = certificationAuthorityService.getDetail(selfUser.getUserId().toString());
            return responseHelper.<CertificationAuthorityDetailDTO>createAndSendResponse(
                    CertificationAuthorityResponseCodeEnum.CERTIFICATION_AUTHORITY_DETAIL,
                    HttpStatus.OK, caDetailDTO);
        } catch (final Exception ex) {
            throw new GetCertificationAuthorityException(ex.getMessage(), ex);
        }
    }
}
