package com.dreamsoftware.tcs.web.controller.ca;

import com.dreamsoftware.tcs.services.ICertificationAuthorityService;
import com.dreamsoftware.tcs.web.controller.ca.error.exception.*;
import com.dreamsoftware.tcs.web.controller.core.SupportController;
import com.dreamsoftware.tcs.web.converter.mediatype.PatchMediaType;
import com.dreamsoftware.tcs.web.core.APIResponse;
import com.dreamsoftware.tcs.web.core.ErrorResponseDTO;
import com.dreamsoftware.tcs.web.dto.request.AddCaMemberDTO;
import com.dreamsoftware.tcs.web.dto.response.CertificationAuthorityDetailDTO;
import com.dreamsoftware.tcs.web.dto.response.SimpleCertificationAuthorityDetailDTO;
import com.dreamsoftware.tcs.web.dto.response.SimpleUserDTO;
import com.dreamsoftware.tcs.web.dto.response.UpdateCertificationAuthorityDTO;
import com.dreamsoftware.tcs.web.security.directives.CurrentUser;
import com.dreamsoftware.tcs.web.security.directives.OnlyAccessForAdmin;
import com.dreamsoftware.tcs.web.security.directives.OnlyAccessForCA;
import com.dreamsoftware.tcs.web.security.directives.OnlyAccessForCaAdmin;
import com.dreamsoftware.tcs.web.security.userdetails.ICommonUserDetailsAware;
import com.dreamsoftware.tcs.web.validation.constraints.CaMemberShouldExist;
import com.dreamsoftware.tcs.web.validation.constraints.CaShouldExist;
import com.dreamsoftware.tcs.web.validation.constraints.ICommonSequence;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.json.JsonMergePatch;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

/**
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
     * Get All certification authorities have been registered
     *
     * @return
     * @throws Throwable
     */
    @Operation(summary = "GET_ALL - Get All certification authorities have been registered", description = "Get All certification authorities have been registered", tags = {"ca"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All CAs registered",
                    content = @Content(schema = @Schema(implementation = SimpleCertificationAuthorityDetailDTO.class))),
            @ApiResponse(responseCode = "404", description = "User Not Found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = "/all", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<Iterable<SimpleCertificationAuthorityDetailDTO>>> getAll() throws Throwable {
        try {
            final Iterable<SimpleCertificationAuthorityDetailDTO> caListDTO = certificationAuthorityService.getAll();
            return responseHelper.createAndSendResponse(
                    CertificationAuthorityResponseCodeEnum.ALL_CERTIFICATION_AUTHORITIES_SUCCESS,
                    HttpStatus.OK, caListDTO);
        } catch (final Exception ex) {
            throw new GetAllCertificationAuthoritiesException(ex.getMessage(), ex);
        }
    }

    /**
     * Get Certification Authority Detail
     *
     * @param id
     * @return
     * @throws Throwable
     */
    @Operation(summary = "GET_CA_DETAIL - Get Certification Authority Detail", description = "Get Certification Authority Detail", tags = {"ca"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CA Detail",
                    content = @Content(schema = @Schema(implementation = CertificationAuthorityDetailDTO.class))),
            @ApiResponse(responseCode = "404", description = "Ca Not Found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<CertificationAuthorityDetailDTO>> getDetailById(
            @Parameter(name = "id", description = "CA Id", required = true)
            @Valid @CaShouldExist(message = "{ca_should_exist}")
            @PathVariable("id") String id
    ) throws Throwable {
        try {
            final CertificationAuthorityDetailDTO caDetailDTO = certificationAuthorityService.getDetail(id);
            return responseHelper.createAndSendResponse(
                    CertificationAuthorityResponseCodeEnum.CERTIFICATION_AUTHORITY_DETAIL,
                    HttpStatus.OK, caDetailDTO);
        } catch (final ConstraintViolationException ex) {
            throw ex;
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
    @Operation(summary = "GET_CA_DETAIL - Get Current Certification Authority Detail", description = "Get Certification Authority Detail", tags = {"ca"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CA Detail",
                    content = @Content(schema = @Schema(implementation = CertificationAuthorityDetailDTO.class))),
            @ApiResponse(responseCode = "404", description = "Ca Not Found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForCA
    public ResponseEntity<APIResponse<CertificationAuthorityDetailDTO>> getDetail(
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser
    ) throws Throwable {
        try {
            final CertificationAuthorityDetailDTO caDetailDTO = certificationAuthorityService.getDetail(selfUser.getUserId());
            return responseHelper.createAndSendResponse(
                    CertificationAuthorityResponseCodeEnum.CERTIFICATION_AUTHORITY_DETAIL,
                    HttpStatus.OK, caDetailDTO);
        } catch (final Exception ex) {
            throw new GetCertificationAuthorityException(ex.getMessage(), ex);
        }
    }

    /**
     * @param selfUser
     * @param id
     * @param mergePatchDocument
     * @return
     */
    @Operation(summary = "PARTIAL_UPDATE_CA", description = "Update CA")
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH,
            consumes = PatchMediaType.APPLICATION_MERGE_PATCH_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForCaAdmin
    public ResponseEntity<APIResponse<SimpleCertificationAuthorityDetailDTO>> partialUpdateCertificationCourse(
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser,
            @Parameter(name = "id", description = "CA Id", required = true)
            @Valid @CaShouldExist(message = "{ca_should_exist}")
            @PathVariable("id") String id,
            @RequestBody JsonMergePatch mergePatchDocument) {

        try {
            final SimpleCertificationAuthorityDetailDTO certificationAuthorityDetailDTO = certificationAuthorityService.editById(id)
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
     * Enable Certification Authority
     *
     * @param id
     * @return
     * @throws java.lang.Throwable
     */
    @Operation(summary = "ENABLE_CA - Enable Certification Authority", description = "Enable Certification Authority", tags = {"ca"})
    @RequestMapping(value = "/{id}/enable", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForAdmin
    public ResponseEntity<APIResponse<String>> enable(
            @Parameter(name = "id", description = "CA Id", required = true)
            @Valid @CaShouldExist(message = "{ca_should_exist}")
            @PathVariable("id") String id,
            @Parameter(hidden = true) HttpServletRequest request
    ) throws Throwable {
        try {
            certificationAuthorityService.enable(id);
            return responseHelper.createAndSendResponse(
                    CertificationAuthorityResponseCodeEnum.CERTIFICATION_AUTHORITY_ENABLED,
                    HttpStatus.OK, resolveString("ca_enabled_successfully", request));
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Exception ex) {
            throw new EnableCertificationAuthorityException(ex.getMessage(), ex);
        }
    }

    /**
     * Enable Certification Authority Member
     *
     * @param selfUser
     * @param caId
     * @param memberId
     * @return
     * @throws java.lang.Throwable
     */
    @Operation(summary = "ENABLE_CA_MEMBER - Enable Certification Authority Member", description = "Enable Certification Authority Member", tags = {"ca"})
    @RequestMapping(value = "/{caId}/member/{memberId}/enable", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForCaAdmin
    public ResponseEntity<APIResponse<String>> enableMember(
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser,
            @Parameter(name = "caId", description = "Certification Authority Id", required = true)
            @Valid @CaShouldExist(message = "{ca_should_exist}")
            @PathVariable("caId") String caId,
            @Parameter(name = "memberId", description = "Member Id", required = true)
            @Valid @CaMemberShouldExist(message = "{ca_member_should_exist}")
            @PathVariable("memberId") String memberId,
            @Parameter(hidden = true) HttpServletRequest request
    ) throws Throwable {
        try {
            certificationAuthorityService.enableMember(caId, memberId, selfUser.getUserId());
            return responseHelper.createAndSendResponse(
                    CertificationAuthorityResponseCodeEnum.CERTIFICATION_AUTHORITY_MEMBER_ENABLED,
                    HttpStatus.OK, resolveString("ca_member_enabled_successfully", request));
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Exception ex) {
            throw new EnableCertificationAuthorityMemberException(ex.getMessage(), ex);
        }
    }

    /**
     * Disable Certification Authority
     *
     * @param id
     * @return
     * @throws Throwable
     */
    @Operation(summary = "DISABLE_CA - Disable Certification Authority", description = "Disable Certification Authority Detail", tags = {"ca"})
    @RequestMapping(value = "/{id}/disable", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForAdmin
    public ResponseEntity<APIResponse<String>> disable(
            @Parameter(name = "id", description = "CA Id", required = true)
            @Valid @CaShouldExist(message = "{ca_should_exist}")
            @PathVariable("id") String id,
            @Parameter(hidden = true) HttpServletRequest request
    ) throws Throwable {
        try {
            certificationAuthorityService.disable(id);
            return responseHelper.createAndSendResponse(
                    CertificationAuthorityResponseCodeEnum.CERTIFICATION_AUTHORITY_DISABLED,
                    HttpStatus.OK, resolveString("ca_disabled_successfully", request));
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Exception ex) {
            throw new DisableCertificationAuthorityException(ex.getMessage(), ex);
        }
    }

    /**
     * Disable Certification Authority Member
     *
     * @param selfUser
     * @param caId
     * @param memberId
     * @return
     * @throws java.lang.Throwable
     */
    @Operation(summary = "DISABLE_CA_MEMBER - Disable Certification Authority Member", description = "Disable Certification Authority Member", tags = {"ca"})
    @RequestMapping(value = "/{caId}/member/{memberId}/disable", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForCaAdmin
    public ResponseEntity<APIResponse<String>> disableMember(
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser,
            @Parameter(name = "caId", description = "Certification Authority Id", required = true)
            @Valid @CaShouldExist(message = "{ca_should_exist}")
            @PathVariable("caId") String caId,
            @Parameter(name = "memberId", description = "Member Id", required = true)
            @Valid @CaMemberShouldExist(message = "{ca_member_should_exist}")
            @PathVariable("memberId") String memberId,
            @Parameter(hidden = true) HttpServletRequest request
    ) throws Throwable {
        try {
            certificationAuthorityService.disableMember(caId, memberId, selfUser.getUserId());
            return responseHelper.createAndSendResponse(
                    CertificationAuthorityResponseCodeEnum.CERTIFICATION_AUTHORITY_MEMBER_DISABLED,
                    HttpStatus.OK, resolveString("ca_member_disabled_successfully", request));
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Exception ex) {
            throw new DisableCertificationAuthorityMemberException(ex.getMessage(), ex);
        }
    }

    /**
     * Add Certification Authority Member
     *
     * @param selfUser
     * @param caId
     * @return
     * @throws Throwable
     */
    @Operation(summary = "ADD_CA_MEMBER - Add member to certification authority", description = "Add member to certification authority", tags = {"ca"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CA Member added succesfully",
                    content = @Content(schema = @Schema(implementation = CertificationAuthorityDetailDTO.class))),
            @ApiResponse(responseCode = "500", description = "An error ocurred when adding CA member",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = "/{id}/member/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForCaAdmin
    public ResponseEntity<APIResponse<SimpleUserDTO>> addCaMember(
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser,
            @Parameter(name = "id", description = "Certification Authority Id", required = true)
            @Valid @CaShouldExist(message = "{ca_should_exist}")
            @PathVariable("id") String caId,
            @Parameter(name = "member", description = "CA Member data. Cannot null or empty.",
                    required = true, schema = @Schema(implementation = AddCaMemberDTO.class))
            @Validated(ICommonSequence.class) AddCaMemberDTO caMember) throws Throwable {
        try {
            final SimpleUserDTO caMemberDTO = certificationAuthorityService.addMember(caId, caMember, selfUser.getWalletHash());
            return responseHelper.createAndSendResponse(
                    CertificationAuthorityResponseCodeEnum.ADD_CA_MEMBER_SUCCESSFULLY,
                    HttpStatus.OK, caMemberDTO);
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Exception ex) {
            throw new AddCaMemberException(ex.getMessage(), ex);
        }
    }

    /**
     * Remove Certification Authority Member
     *
     * @param selfUser
     * @param caId
     * @param memberId
     * @return
     * @throws Throwable
     */
    @Operation(summary = "REMOVE_CA_MEMBER - Remove member from certification authority", description = "Remove member from certification authority", tags = {"ca"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CA Member removed successfully",
                    content = @Content(schema = @Schema(implementation = CertificationAuthorityDetailDTO.class))),
            @ApiResponse(responseCode = "500", description = "An error occurred when removing CA member",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = "/{caId}/member/{memberId}/remove", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForCaAdmin
    public ResponseEntity<APIResponse<String>> removeCaMember(
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser,
            @Parameter(name = "caId", description = "Certification Authority Id", required = true)
            @Valid @CaShouldExist(message = "{ca_should_exist}")
            @PathVariable("caId") String caId,
            @Parameter(name = "memberId", description = "CA Member Id", required = true)
            @Valid @CaMemberShouldExist(message = "{ca_member_should_exist}")
            @PathVariable("memberId") String memberId,
            @Parameter(hidden = true) HttpServletRequest request) throws Throwable {
        try {
            certificationAuthorityService.removeMember(caId, memberId, selfUser.getUserId());
            return responseHelper.createAndSendResponse(
                    CertificationAuthorityResponseCodeEnum.REMOVE_CA_MEMBER_SUCCESSFULLY,
                    HttpStatus.OK, resolveString("ca_member_removed_successfully", request));
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Exception ex) {
            throw new RemoveCaMemberException(ex.getMessage(), ex);
        }
    }

    /**
     * Remove Certification Authority
     *
     * @param caId
     * @return
     * @throws Throwable
     */
    @Operation(summary = "REMOVE_CA - Remove Certification authority (Only ADMIN)", description = "Remove  certification authority", tags = {"ca"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CA removed successfully",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "An error occurred when removing CA",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = "/{caId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForAdmin
    public ResponseEntity<APIResponse<String>> removeCa(
            @Parameter(name = "caId", description = "Certification Authority Id", required = true)
            @Valid @CaShouldExist(message = "{ca_should_exist}")
            @PathVariable("caId") String caId,
            @Parameter(hidden = true) HttpServletRequest request) throws Throwable {
        try {
            certificationAuthorityService.remove(caId);
            return responseHelper.createAndSendResponse(
                    CertificationAuthorityResponseCodeEnum.REMOVE_CA_SUCCESSFULLY,
                    HttpStatus.OK, resolveString("ca_removed_successfully", request));
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Exception ex) {
            throw new RemoveCaException(ex.getMessage(), ex);
        }
    }
}
