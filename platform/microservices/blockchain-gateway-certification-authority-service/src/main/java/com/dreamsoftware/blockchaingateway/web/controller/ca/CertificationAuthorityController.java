package com.dreamsoftware.blockchaingateway.web.controller.ca;

import com.dreamsoftware.blockchaingateway.services.ICertificationAuthorityService;
import com.dreamsoftware.blockchaingateway.web.controller.error.exception.RegisterCertificationAuthorityException;
import com.dreamsoftware.blockchaingateway.web.core.APIResponse;
import com.dreamsoftware.blockchaingateway.web.core.ErrorResponseDTO;
import com.dreamsoftware.blockchaingateway.web.controller.core.SupportController;
import com.dreamsoftware.blockchaingateway.web.dto.request.SignUpUserDTO;
import com.dreamsoftware.blockchaingateway.web.dto.response.CertificationAuthorityDetailDTO;
import com.dreamsoftware.blockchaingateway.web.validation.constraints.ICommonSequence;
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
@RequestMapping("/api/v1/certification-authority/")
@Tag(name = "certification_authority", description = "/api/v1/certification-authority/ (Code Response interval -> 1XX)")
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
    public ResponseEntity<APIResponse<CertificationAuthorityDetailDTO>> getDetailById(
            @Parameter(name = "id", description = "CA Id", required = true)
            @PathVariable("id") String id
    ) throws Throwable {

        /*return userService.findById(selfUser.getUserId())
                .map(userResource -> responseHelper.<SimpleUserDTO>createAndSendVersionableResponse(ProfileResponseCodeEnum.GET_SIMPLE_PROFILE_DETAIL, HttpStatus.OK, userResource))
                .orElseThrow(() -> {
                    throw new UserNotFoundException();
                });*/
        return null;
    }

    /**
     * Register Certification Authority
     *
     * @param registerCertificationAuthority
     * @return
     * @throws Throwable
     */
    @Operation(summary = "REGISTER_CERTIFICATION_AUTHORITY", description = "Save User Address")
    @RequestMapping(value = "/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<String>> registerCertificationAuthority(
            @Parameter(name = "certificationAuthority", description = "Certification Authority Data", required = true)
            @Validated(ICommonSequence.class) SignUpUserDTO registerCertificationAuthority) throws Throwable {
        try {
            certificationAuthorityService.register(registerCertificationAuthority);
            // Create And Send Response
            return responseHelper.createAndSendResponse(CertificationAuthorityResponseCodeEnum.CERTIFICATION_AUTHORITY_REGISTRATION_REQUESTED,
                    HttpStatus.OK, "Certification Authority Registration Requested");
        } catch (final Exception ex) {
            throw new RegisterCertificationAuthorityException(ex.getMessage(), ex);
        }
    }
}
