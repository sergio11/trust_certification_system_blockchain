package com.dreamsoftware.tcs.web.controller.ca;

import com.dreamsoftware.tcs.services.ICertificationAuthorityService;
import com.dreamsoftware.tcs.web.controller.ca.error.exception.DisableCertificationAuthorityException;
import com.dreamsoftware.tcs.web.controller.ca.error.exception.EnableCertificationAuthorityException;
import com.dreamsoftware.tcs.web.controller.ca.error.exception.GetCertificationAuthorityException;
import com.dreamsoftware.tcs.web.controller.ca.error.exception.PartialUpdateCAException;
import com.dreamsoftware.tcs.web.core.APIResponse;
import com.dreamsoftware.tcs.web.core.ErrorResponseDTO;
import com.dreamsoftware.tcs.web.controller.core.SupportController;
import com.dreamsoftware.tcs.web.converter.mediatype.PatchMediaType;
import com.dreamsoftware.tcs.web.dto.response.CertificationAuthorityDetailDTO;
import com.dreamsoftware.tcs.web.dto.response.UpdateCertificationAuthorityDTO;
import com.dreamsoftware.tcs.web.security.directives.CurrentUser;
import com.dreamsoftware.tcs.web.security.directives.OnlyAccessForAdmin;
import com.dreamsoftware.tcs.web.security.directives.OnlyAccessForCA;
import com.dreamsoftware.tcs.web.security.userdetails.ICommonUserDetailsAware;
import com.dreamsoftware.tcs.web.validation.constraints.ShouldBeAValidObjectId;
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
import javax.json.JsonMergePatch;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
            @Valid @ShouldBeAValidObjectId(message = "{user_id_not_valid}") @PathVariable("id") String id
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
     *
     * @param selfUser
     * @param mergePatchDocument
     * @return
     */
    @Operation(summary = "PARTIAL_UPDATE_CA", description = "Update CA")
    @RequestMapping(value = "/", method = RequestMethod.PATCH,
            consumes = PatchMediaType.APPLICATION_MERGE_PATCH_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForCA
    public ResponseEntity<APIResponse<CertificationAuthorityDetailDTO>> partialUpdateCertificationCourse(
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<ObjectId> selfUser,
            @RequestBody JsonMergePatch mergePatchDocument) {

        try {
            final CertificationAuthorityDetailDTO certificationAuthorityDetailDTO = certificationAuthorityService.editByWalletHash(selfUser.getWalletHash())
                    .map(updateCertificationAuthority -> patchHelper.mergePatch(mergePatchDocument, updateCertificationAuthority, UpdateCertificationAuthorityDTO.class))
                    .map(updateCertificationAuthority -> certificationAuthorityService.update(selfUser.getWalletHash(), updateCertificationAuthority).orElseThrow(GetCertificationAuthorityException::new))
                    .orElseThrow(GetCertificationAuthorityException::new);
            return responseHelper.createAndSendResponse(CertificationAuthorityResponseCodeEnum.PARTIAL_CERTIFICATION_AUTHORITY_UPDATE, HttpStatus.OK, certificationAuthorityDetailDTO);
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
            throw new PartialUpdateCAException(ex.getMessage(), ex);
        }
    }

    /**
     * Get Certification Authority Detail
     *
     * @param selfUser
     * @return
     * @throws Throwable
     */
    @Operation(summary = "GET_CA_DETAIL - Get Current Certification Authority Detail", description = "Get Certification Authority Detail", tags = {"CA"})
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

    /**
     * Enable Certification Authority
     *
     * @param id
     * @return
     * @throws java.lang.Throwable
     */
    @Operation(summary = "ENABLE_CA - Enable Certification Authority", description = "Enable Certification Authority Detail", tags = {"CA"})
    @RequestMapping(value = "/{id}/enable", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForAdmin
    public ResponseEntity<APIResponse<CertificationAuthorityDetailDTO>> enable(
            @Parameter(name = "id", description = "CA Id", required = true)
            @Valid @ShouldBeAValidObjectId(message = "{user_id_not_valid}") @PathVariable("id") String id
    ) throws Throwable {
        try {

            final CertificationAuthorityDetailDTO caDetailDTO = certificationAuthorityService.enable(id);
            return responseHelper.<CertificationAuthorityDetailDTO>createAndSendResponse(
                    CertificationAuthorityResponseCodeEnum.CERTIFICATION_AUTHORITY_ENABLED,
                    HttpStatus.OK, caDetailDTO);
        } catch (final Exception ex) {
            throw new EnableCertificationAuthorityException(ex.getMessage(), ex);
        }
    }

    /**
     * Disable Certification Authority
     *
     * @param id
     * @return
     * @throws Throwable
     */
    @Operation(summary = "DISABLE_CA - Disable Certification Authority", description = "Disable Certification Authority Detail", tags = {"CA"})
    @RequestMapping(value = "/{id}/disable", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForAdmin
    public ResponseEntity<APIResponse<CertificationAuthorityDetailDTO>> disable(
            @Parameter(name = "id", description = "CA Id", required = true)
            @Valid @ShouldBeAValidObjectId(message = "{user_id_not_valid}") @PathVariable("id") String id
    ) throws Throwable {
        try {
            final CertificationAuthorityDetailDTO caDetailDTO = certificationAuthorityService.disable(id);
            return responseHelper.<CertificationAuthorityDetailDTO>createAndSendResponse(
                    CertificationAuthorityResponseCodeEnum.CERTIFICATION_AUTHORITY_DISABLED,
                    HttpStatus.OK, caDetailDTO);
        } catch (final Exception ex) {
            throw new DisableCertificationAuthorityException(ex.getMessage(), ex);
        }
    }
}
