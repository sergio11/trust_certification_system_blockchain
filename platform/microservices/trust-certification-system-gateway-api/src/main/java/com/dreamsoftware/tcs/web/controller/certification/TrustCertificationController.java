package com.dreamsoftware.tcs.web.controller.certification;

import com.dreamsoftware.tcs.services.ITrustCertificationService;
import com.dreamsoftware.tcs.web.controller.certification.error.exception.DisableCertificateException;
import com.dreamsoftware.tcs.web.controller.certification.error.exception.EnableCertificateException;
import com.dreamsoftware.tcs.web.controller.certification.error.exception.GetCertificateIssuedDetailException;
import com.dreamsoftware.tcs.web.controller.certification.error.exception.GetCertificatesException;
import com.dreamsoftware.tcs.web.controller.certification.error.exception.IssueCertificateException;
import com.dreamsoftware.tcs.web.controller.certification.error.exception.RenewCertificateException;
import com.dreamsoftware.tcs.web.controller.certification.error.exception.UpdateCertificateVisibilityException;
import com.dreamsoftware.tcs.web.core.APIResponse;
import com.dreamsoftware.tcs.web.controller.core.SupportController;
import com.dreamsoftware.tcs.web.core.ErrorResponseDTO;
import com.dreamsoftware.tcs.web.dto.request.IssueCertificateDTO;
import com.dreamsoftware.tcs.web.dto.response.CertificateIssuedDTO;
import com.dreamsoftware.tcs.web.security.directives.CurrentUser;
import com.dreamsoftware.tcs.web.security.directives.OnlyAccessForCA;
import com.dreamsoftware.tcs.web.security.directives.OnlyAccessForStudent;
import com.dreamsoftware.tcs.web.security.userdetails.ICommonUserDetailsAware;
import com.dreamsoftware.tcs.web.validation.constraints.ICommonSequence;
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
@RequestMapping("/api/v1/certification/")
@Tag(name = "certification", description = "/api/v1/certification/ (Code Response interval -> 4XX)")
@RequiredArgsConstructor
public class TrustCertificationController extends SupportController {

    /**
     * Trust Certification Service
     */
    private final ITrustCertificationService trustCertificationService;

    /**
     * Enable Certificate
     *
     * @param id
     * @param selfUser
     * @return
     * @throws java.lang.Throwable
     */
    @Operation(summary = "ENABLE_CERTIFICATE - Enable Certificate", description = "Enable Certificate", tags = {"CERTIFICATE_ISSUED"})
    @RequestMapping(value = "/{id}/enable", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForStudent
    public ResponseEntity<APIResponse<CertificateIssuedDTO>> enable(
            @Parameter(name = "id", description = "Certificate Id", required = true)
            @PathVariable("id") String id,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<ObjectId> selfUser
    ) throws Throwable {
        try {
            final CertificateIssuedDTO certificateIssuedDTO = trustCertificationService.enable(selfUser.getWalletHash(), id);
            return responseHelper.<CertificateIssuedDTO>createAndSendResponse(TrustCertificationResponseCodeEnum.CERTIFICATE_ISSUED_ENABLED,
                    HttpStatus.OK, certificateIssuedDTO);
        } catch (final Exception ex) {
            throw new EnableCertificateException(ex.getMessage(), ex);
        }
    }

    /**
     * Disable Certificate
     *
     * @param id
     * @param selfUser
     * @return
     * @throws java.lang.Throwable
     */
    @Operation(summary = "DISABLE_CERTIFICATE - Disable Certificate", description = "Disable Certificate", tags = {"CERTIFICATE_ISSUED"})
    @RequestMapping(value = "/{id}/disable", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForStudent
    public ResponseEntity<APIResponse<CertificateIssuedDTO>> disable(
            @Parameter(name = "id", description = "Certificate Id", required = true)
            @PathVariable("id") String id,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<ObjectId> selfUser
    ) throws Throwable {
        try {
            final CertificateIssuedDTO certificateIssuedDTO = trustCertificationService.disable(selfUser.getWalletHash(), id);
            return responseHelper.<CertificateIssuedDTO>createAndSendResponse(TrustCertificationResponseCodeEnum.CERTIFICATE_ISSUED_DISABLED,
                    HttpStatus.OK, certificateIssuedDTO);
        } catch (final Exception ex) {
            throw new DisableCertificateException(ex.getMessage(), ex);
        }
    }

    /**
     * Enable Certificate Visibility
     *
     * @param id
     * @param selfUser
     * @return
     * @throws java.lang.Throwable
     */
    @Operation(summary = "ENABLE_CERTIFICATE_VISIBILITY - Enable Certificate Visibility", description = "Enable Certificate Visibility", tags = {"CERTIFICATE_ISSUED"})
    @RequestMapping(value = "/{id}/visible", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForStudent
    public ResponseEntity<APIResponse<CertificateIssuedDTO>> visible(
            @Parameter(name = "id", description = "Certificate Id", required = true)
            @PathVariable("id") String id,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<ObjectId> selfUser
    ) throws Throwable {
        try {
            final CertificateIssuedDTO certificateIssuedDTO = trustCertificationService.updateVisibility(selfUser.getWalletHash(), id, true);
            return responseHelper.<CertificateIssuedDTO>createAndSendResponse(TrustCertificationResponseCodeEnum.CERTIFICATE_ISSUED_VISIBILITY_UPDATED,
                    HttpStatus.OK, certificateIssuedDTO);
        } catch (final Exception ex) {
            throw new UpdateCertificateVisibilityException(ex.getMessage(), ex);
        }
    }

    /**
     * Disable Certificate Visibility
     *
     * @param id
     * @param selfUser
     * @return
     * @throws java.lang.Throwable
     */
    @Operation(summary = "DISABLE_CERTIFICATE_VISIBILITY - Disable Certificate Visibility", description = "Disable Certificate Visibility", tags = {"CERTIFICATE_ISSUED"})
    @RequestMapping(value = "/{id}/invisible", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForStudent
    public ResponseEntity<APIResponse<CertificateIssuedDTO>> invisible(
            @Parameter(name = "id", description = "Certificate Id", required = true)
            @PathVariable("id") String id,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<ObjectId> selfUser
    ) throws Throwable {
        try {
            final CertificateIssuedDTO certificateIssuedDTO = trustCertificationService.updateVisibility(selfUser.getWalletHash(), id, false);
            return responseHelper.<CertificateIssuedDTO>createAndSendResponse(TrustCertificationResponseCodeEnum.CERTIFICATE_ISSUED_VISIBILITY_UPDATED,
                    HttpStatus.OK, certificateIssuedDTO);
        } catch (final Exception ex) {
            throw new UpdateCertificateVisibilityException(ex.getMessage(), ex);
        }
    }

    /**
     * Get Certificate Issued Detail
     *
     * @param id
     * @param selfUser
     * @return
     * @throws Throwable
     */
    @Operation(summary = "GET_CERTIFICATE_ISSUED_DETAIL - Get Certificate Issued Detail", description = "Get Certificate Detail", tags = {"CERTIFICATE_ISSUED"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Certificate Issued Detail",
                content = @Content(schema = @Schema(implementation = CertificateIssuedDTO.class))),
        @ApiResponse(responseCode = "404", description = "Certificate Issued Not Found",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<CertificateIssuedDTO>> getDetailById(
            @Parameter(name = "id", description = "Certificate Id", required = true)
            @PathVariable("id") String id,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<ObjectId> selfUser
    ) throws Throwable {
        try {
            final CertificateIssuedDTO certificateIssuedDTO = trustCertificationService.getDetail(selfUser.getWalletHash(), id);
            return responseHelper.<CertificateIssuedDTO>createAndSendResponse(
                    TrustCertificationResponseCodeEnum.CERTIFICATE_ISSUED_DETAIL,
                    HttpStatus.OK, certificateIssuedDTO);
        } catch (final Exception ex) {
            throw new GetCertificateIssuedDetailException(ex.getMessage(), ex);
        }
    }

    /**
     * Get My Certificates As Recipient
     *
     * @param selfUser
     * @return
     * @throws Throwable
     */
    @Operation(summary = "GET_MY_CERTIFICATES_AS_RECIPIENT - Get My Certificates As Recipient", description = "Get My Certificates As Recipient", tags = {"CERTIFICATE_ISSUED"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Certificates As Recipient",
                content = @Content(schema = @Schema(implementation = CertificateIssuedDTO.class))),
        @ApiResponse(responseCode = "404", description = "No Certificates Found",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = {"/certificates/recipient"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForStudent
    public ResponseEntity<APIResponse<Iterable<CertificateIssuedDTO>>> getMyCertificatesAsRecipient(
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<ObjectId> selfUser
    ) throws Throwable {
        try {
            final Iterable<CertificateIssuedDTO> certificateIssuedDTOList = trustCertificationService.getMyCertificatesAsRecipient(selfUser.getWalletHash());
            return responseHelper.<Iterable<CertificateIssuedDTO>>createAndSendResponse(
                    TrustCertificationResponseCodeEnum.GET_MY_CERTIFICATES_AS_RECIPIENT,
                    HttpStatus.OK, certificateIssuedDTOList);
        } catch (final Exception ex) {
            throw new GetCertificatesException(ex.getMessage(), ex);
        }
    }

    /**
     * Get My Certificates As Recipient
     *
     * @param selfUser
     * @return
     * @throws Throwable
     */
    @Operation(summary = "GET_MY_CERTIFICATES_AS_ISSUER - Get My Certificates As Issuer", description = "Get My Certificates As Issuer", tags = {"CERTIFICATE_ISSUED"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Certificates As Issuer",
                content = @Content(schema = @Schema(implementation = CertificateIssuedDTO.class))),
        @ApiResponse(responseCode = "404", description = "No Certificates Found",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = {"/certificates/issuer"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForCA
    public ResponseEntity<APIResponse<Iterable<CertificateIssuedDTO>>> getMyCertificatesAsIssuer(
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<ObjectId> selfUser
    ) throws Throwable {
        try {
            final Iterable<CertificateIssuedDTO> certificateIssuedDTOList = trustCertificationService.getMyCertificatesAsIssuer(selfUser.getWalletHash());
            return responseHelper.<Iterable<CertificateIssuedDTO>>createAndSendResponse(
                    TrustCertificationResponseCodeEnum.GET_MY_CERTIFICATES_AS_ISSUER,
                    HttpStatus.OK, certificateIssuedDTOList);
        } catch (final Exception ex) {
            throw new GetCertificatesException(ex.getMessage(), ex);
        }
    }

    /**
     * Issue Certificate
     *
     * @param issueCertificate
     * @param selfUser
     * @return
     * @throws java.lang.Throwable
     */
    @Operation(summary = "ISSUE_CERTIFICATE - Issue Certificate", description = "Issue Certificate", tags = {"CERTIFICATE_ISSUED"})
    @RequestMapping(value = "/issue", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForStudent
    public ResponseEntity<APIResponse<String>> issueCertificate(
            @Validated(ICommonSequence.class) IssueCertificateDTO issueCertificate,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<ObjectId> selfUser
    ) throws Throwable {
        try {
            issueCertificate.setStudentWalletHash(selfUser.getWalletHash());
            trustCertificationService.issueCertificate(issueCertificate);
            return responseHelper.<String>createAndSendResponse(TrustCertificationResponseCodeEnum.NEW_CERTIFICATE_ISSUED,
                    HttpStatus.OK, "New Certificate Issued");
        } catch (final Exception ex) {
            throw new IssueCertificateException(ex.getMessage(), ex);
        }
    }

    /**
     * Renew Certificate
     *
     *
     * @param id
     * @param selfUser
     * @return
     * @throws java.lang.Throwable
     */
    @Operation(summary = "RENEW_CERTIFICATE - Renew Certificate", description = "Issue Certificate", tags = {"CERTIFICATE_ISSUED"})
    @RequestMapping(value = "/{id}/renew", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForStudent
    public ResponseEntity<APIResponse<CertificateIssuedDTO>> renewCertificate(
            @Parameter(name = "id", description = "Certificate Id", required = true)
            @PathVariable("id") String id,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<ObjectId> selfUser
    ) throws Throwable {
        try {
            final CertificateIssuedDTO certificateIssued = trustCertificationService.renewCertificate(selfUser.getWalletHash(), id);
            return responseHelper.<CertificateIssuedDTO>createAndSendResponse(TrustCertificationResponseCodeEnum.RENEW_CERTIFICATE_FAILED,
                    HttpStatus.OK, certificateIssued);
        } catch (final Exception ex) {
            throw new RenewCertificateException(ex.getMessage(), ex);
        }
    }
}
