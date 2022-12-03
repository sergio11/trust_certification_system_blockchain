package com.dreamsoftware.tcs.web.controller.certification;

import com.dreamsoftware.tcs.services.ITrustCertificationService;
import com.dreamsoftware.tcs.web.controller.certification.error.exception.*;
import com.dreamsoftware.tcs.web.core.APIResponse;
import com.dreamsoftware.tcs.web.controller.core.SupportController;
import com.dreamsoftware.tcs.web.core.ErrorResponseDTO;
import com.dreamsoftware.tcs.web.core.FileInfoDTO;
import com.dreamsoftware.tcs.web.dto.request.IssueCertificateRequestDTO;
import com.dreamsoftware.tcs.web.dto.request.ValidateCertificateDTO;
import com.dreamsoftware.tcs.web.dto.response.CertificateIssuanceRequestDTO;
import com.dreamsoftware.tcs.web.dto.response.CertificateIssuedDetailDTO;
import com.dreamsoftware.tcs.web.dto.response.SimpleCertificateIssuedDTO;
import com.dreamsoftware.tcs.web.security.directives.CurrentUser;
import com.dreamsoftware.tcs.web.security.directives.OnlyAccessForCA;
import com.dreamsoftware.tcs.web.security.directives.OnlyAccessForStudent;
import com.dreamsoftware.tcs.web.security.userdetails.ICommonUserDetailsAware;
import com.dreamsoftware.tcs.web.validation.constraints.CertificateShouldExist;
import com.dreamsoftware.tcs.web.validation.constraints.ICommonSequence;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.dreamsoftware.tcs.web.validation.constraints.CertificateShouldBePendingReview;
import javax.servlet.http.HttpServletRequest;

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
     * @param certId
     * @param selfUser
     * @return
     * @throws java.lang.Throwable
     */
    @Operation(summary = "ENABLE_CERTIFICATE - Enable Certificate", description = "Enable Certificate", tags = {"certification"})
    @RequestMapping(value = "/{certId}/enable", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForStudent
    public ResponseEntity<APIResponse<String>> enable(
            @Parameter(name = "certId", description = "Certificate Id", required = true)
            @Valid @CertificateShouldExist(message = "{certificate_not_exist}")
            @PathVariable("certId") String certId,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser,
            @Parameter(hidden = true) HttpServletRequest request
    ) throws Throwable {
        try {
            trustCertificationService.enable(selfUser.getWalletHash(), certId);
            return responseHelper.createAndSendResponse(TrustCertificationResponseCodeEnum.CERTIFICATE_ISSUED_ENABLED,
                    HttpStatus.OK, resolveString("certificate_enabled_successfully", request));
        } catch (final Exception ex) {
            throw new EnableCertificateException(ex.getMessage(), ex);
        }
    }

    /**
     * Disable Certificate
     *
     * @param certId
     * @param selfUser
     * @return
     * @throws java.lang.Throwable
     */
    @Operation(summary = "DISABLE_CERTIFICATE - Disable Certificate", description = "Disable Certificate", tags = {"certification"})
    @RequestMapping(value = "/{certId}/disable", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForStudent
    public ResponseEntity<APIResponse<String>> disable(
            @Parameter(name = "certId", description = "Certificate Id", required = true)
            @Valid @CertificateShouldExist(message = "{certificate_not_exist}")
            @PathVariable("certId") String certId,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser,
            @Parameter(hidden = true) HttpServletRequest request
    ) throws Throwable {
        try {
            trustCertificationService.disable(selfUser.getWalletHash(), certId);
            return responseHelper.createAndSendResponse(TrustCertificationResponseCodeEnum.CERTIFICATE_ISSUED_DISABLED,
                    HttpStatus.OK, resolveString("certificate_disabled_successfully", request));
        } catch (final Exception ex) {
            throw new DisableCertificateException(ex.getMessage(), ex);
        }
    }

    /**
     * Enable Certificate Visibility
     *
     * @param certId
     * @param selfUser
     * @return
     * @throws java.lang.Throwable
     */
    @Operation(summary = "ENABLE_CERTIFICATE_VISIBILITY - Enable Certificate Visibility", description = "Enable Certificate Visibility", tags = {"certification"})
    @RequestMapping(value = "/{certId}/visible", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForStudent
    public ResponseEntity<APIResponse<String>> enableCertificateVisibility(
            @Parameter(name = "certId", description = "Certificate Id", required = true)
            @Valid @CertificateShouldExist(message = "{certificate_not_exist}")
            @PathVariable("certId") String certId,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser,
            @Parameter(hidden = true) HttpServletRequest request
    ) throws Throwable {
        try {
            trustCertificationService.updateVisibility(selfUser.getWalletHash(), certId, true);
            return responseHelper.createAndSendResponse(TrustCertificationResponseCodeEnum.CERTIFICATE_ISSUED_VISIBILITY_UPDATED,
                    HttpStatus.OK, resolveString("certificate_visibility_updated_successfully", request));
        } catch (final Exception ex) {
            throw new UpdateCertificateVisibilityException(ex.getMessage(), ex);
        }
    }

    /**
     * Disable Certificate Visibility
     *
     * @param certId
     * @param selfUser
     * @return
     * @throws java.lang.Throwable
     */
    @Operation(summary = "DISABLE_CERTIFICATE_VISIBILITY - Disable Certificate Visibility", description = "Disable Certificate Visibility", tags = {"certification"})
    @RequestMapping(value = "/{certId}/invisible", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForStudent
    public ResponseEntity<APIResponse<String>> disableCertificateVisibility(
            @Parameter(name = "certId", description = "Certificate Id", required = true)
            @Valid @CertificateShouldExist(message = "{certificate_not_exist}")
            @PathVariable("certId") String certId,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser,
            @Parameter(hidden = true) HttpServletRequest request
    ) throws Throwable {
        try {
            trustCertificationService.updateVisibility(selfUser.getWalletHash(), certId, false);
            return responseHelper.createAndSendResponse(TrustCertificationResponseCodeEnum.CERTIFICATE_ISSUED_VISIBILITY_UPDATED,
                    HttpStatus.OK, resolveString("certificate_visibility_updated_successfully", request));
        } catch (final Exception ex) {
            throw new UpdateCertificateVisibilityException(ex.getMessage(), ex);
        }
    }

    /**
     * Get Certificate Issued Detail
     *
     * @param certId
     * @return
     * @throws Throwable
     */
    @Operation(summary = "GET_CERTIFICATE_ISSUED_DETAIL - Get Certificate Issued Detail", description = "Get Certificate Detail", tags = {"certification"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Certificate Issued Detail",
                content = @Content(schema = @Schema(implementation = SimpleCertificateIssuedDTO.class))),
        @ApiResponse(responseCode = "404", description = "Certificate Issued Not Found",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = {"/{certId}/detail"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<CertificateIssuedDetailDTO>> getDetailById(
            @Parameter(name = "certId", description = "Certificate Id", required = true)
            @Valid @CertificateShouldExist(message = "{certificate_not_exist}")
            @PathVariable("certId") String certId
    ) throws Throwable {
        try {
            final CertificateIssuedDetailDTO certificateIssuedDTO = trustCertificationService.getDetail(certId);
            return responseHelper.createAndSendResponse(TrustCertificationResponseCodeEnum.CERTIFICATE_ISSUED_DETAIL,
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
    @Operation(summary = "GET_MY_CERTIFICATES_AS_RECIPIENT - Get My Certificates As Recipient", description = "Get My Certificates As Recipient", tags = {"certification"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Certificates As Recipient",
                content = @Content(schema = @Schema(implementation = SimpleCertificateIssuedDTO.class))),
        @ApiResponse(responseCode = "404", description = "No Certificates Found",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = {"/certificates/recipient"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForStudent
    public ResponseEntity<APIResponse<Iterable<SimpleCertificateIssuedDTO>>> getMyCertificatesAsRecipient(
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser
    ) throws Throwable {
        try {
            final Iterable<SimpleCertificateIssuedDTO> certificateIssuedDTOList = trustCertificationService.getMyCertificatesAsRecipient(selfUser.getWalletHash());
            return responseHelper.createAndSendResponse(TrustCertificationResponseCodeEnum.GET_MY_CERTIFICATES_AS_RECIPIENT,
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
    @Operation(summary = "GET_CERTIFICATES_ISSUANCE_REQUESTS_FROM_STUDENT - Get certificates issuance requests from student", description = "Get certificates issuance requests from student", tags = {"certification"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Certificates Issuance request",
                content = @Content(schema = @Schema(implementation = CertificateIssuanceRequestDTO.class))),
        @ApiResponse(responseCode = "404", description = "No Certificates request Found",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = {"/certificates/request/student"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForStudent
    public ResponseEntity<APIResponse<Iterable<CertificateIssuanceRequestDTO>>> getCertificatesIssuanceRequestsFromStudent(
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser
    ) throws Throwable {
        try {
            final Iterable<CertificateIssuanceRequestDTO> certificateIssuanceRequestDTOList = trustCertificationService.getCertificatesIssuanceRequestsFromStudent(selfUser.getUserId());
            return responseHelper.createAndSendResponse(TrustCertificationResponseCodeEnum.GET_CERTIFICATES_ISSUANCE_REQUEST_SUCCESS,
                    HttpStatus.OK, certificateIssuanceRequestDTOList);
        } catch (final Exception ex) {
            throw new GetCertificatesIssuanceRequestsException(ex.getMessage(), ex);
        }
    }

    /**
     * Get My Certificates As Recipient
     *
     * @param selfUser
     * @return
     * @throws Throwable
     */
    @Operation(summary = "GET_CERTIFICATES_ISSUANCE_REQUESTS_FROM_CA - Get certificates issuance requests from CA", description = "Get certificates issuance requests from CA", tags = {"certification"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Certificates Issuance request",
                content = @Content(schema = @Schema(implementation = CertificateIssuanceRequestDTO.class))),
        @ApiResponse(responseCode = "404", description = "No Certificates request Found",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = {"/certificates/request/ca"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForCA
    public ResponseEntity<APIResponse<Iterable<CertificateIssuanceRequestDTO>>> getCertificatesIssuanceRequestsFromCa(
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser
    ) throws Throwable {
        try {
            final Iterable<CertificateIssuanceRequestDTO> certificateIssuanceRequestDTOList = trustCertificationService.getCertificatesIssuanceRequestsFromCa(selfUser.getUserId());
            return responseHelper.createAndSendResponse(TrustCertificationResponseCodeEnum.GET_CERTIFICATES_ISSUANCE_REQUEST_SUCCESS,
                    HttpStatus.OK, certificateIssuanceRequestDTOList);
        } catch (final Exception ex) {
            throw new GetCertificatesIssuanceRequestsException(ex.getMessage(), ex);
        }
    }

    /**
     * Get My Certificates As Recipient
     *
     * @param selfUser
     * @return
     * @throws Throwable
     */
    @Operation(summary = "GET_MY_CERTIFICATES_AS_ISSUER - Get My Certificates As Issuer", description = "Get My Certificates As Issuer", tags = {"certification"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Certificates As Issuer",
                content = @Content(schema = @Schema(implementation = SimpleCertificateIssuedDTO.class))),
        @ApiResponse(responseCode = "404", description = "No Certificates Found",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = {"/certificates/issuer"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForCA
    public ResponseEntity<APIResponse<Iterable<SimpleCertificateIssuedDTO>>> getMyCertificatesAsIssuer(
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser
    ) throws Throwable {
        try {
            final Iterable<SimpleCertificateIssuedDTO> certificateIssuedDTOList = trustCertificationService.getMyCertificatesAsIssuer(selfUser.getWalletHash());
            return responseHelper.createAndSendResponse(TrustCertificationResponseCodeEnum.GET_MY_CERTIFICATES_AS_ISSUER,
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
    @Operation(summary = "ISSUE_CERTIFICATE_REQUEST - Issue Certificate Request", description = "Issue Certificate Request", tags = {"certification"})
    @RequestMapping(value = "/request", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForStudent
    public ResponseEntity<APIResponse<CertificateIssuanceRequestDTO>> issueCertificateRequest(
            @Parameter(name = "issue_certificate_request", description = "Issue Certificate Request. Cannot null or empty.",
                    required = true, schema = @Schema(implementation = IssueCertificateRequestDTO.class))
            @Validated(ICommonSequence.class) IssueCertificateRequestDTO issueCertificate,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser
    ) throws Throwable {
        try {
            issueCertificate.setStudentWalletHash(selfUser.getWalletHash());
            final CertificateIssuanceRequestDTO certificateIssuanceRequestDTO = trustCertificationService.issueCertificateRequest(issueCertificate);
            return responseHelper.createAndSendResponse(TrustCertificationResponseCodeEnum.NEW_ISSUED_CERTIFICATE_REQUESTED,
                    HttpStatus.OK, certificateIssuanceRequestDTO);
        } catch (final Exception ex) {
            throw new IssueCertificateRequestException(ex.getMessage(), ex);
        }
    }

    /**
     * Accept request for certificate issuance
     *
     * @param certId
     * @return
     * @throws java.lang.Throwable
     */
    @Operation(summary = "ACCEPT_CERTIFICATE_REQUEST - Accept request for certificate issuance", description = "Accept request for certificate issuance", tags = {"certification"})
    @RequestMapping(value = "/{certId}/accept", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForCA
    public ResponseEntity<APIResponse<CertificateIssuanceRequestDTO>> acceptCertificateRequest(
            @Parameter(name = "certId", description = "Certificate Id", required = true)
            @Valid @CertificateShouldBePendingReview(message = "{certificate_should_be_pending_review}")
            @PathVariable("certId") String certId
    ) throws Throwable {
        try {
            final CertificateIssuanceRequestDTO certificateIssuanceRequestDTO = trustCertificationService.acceptCertificateRequest(certId);
            return responseHelper.createAndSendResponse(TrustCertificationResponseCodeEnum.ISSUE_CERTIFICATE_REQUEST_ACCEPTED,
                    HttpStatus.OK, certificateIssuanceRequestDTO);
        } catch (final Exception ex) {
            throw new AcceptCertificateRequestException(ex.getMessage(), ex);
        }
    }

    /**
     * Reject Certificate
     *
     * @param certId
     * @return
     * @throws java.lang.Throwable
     */
    @Operation(summary = "REJECT_CERTIFICATE_REQUEST - Reject request for certificate issuance", description = "Reject request for certificate issuance", tags = {"certification"})
    @RequestMapping(value = "/{certId}/reject", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForCA
    public ResponseEntity<APIResponse<CertificateIssuanceRequestDTO>> rejectCertificateRequest(
            @Parameter(name = "certId", description = "Certificate Id", required = true)
            @Valid @CertificateShouldBePendingReview(message = "{certificate_should_be_pending_review}")
            @PathVariable("certId") String certId
    ) throws Throwable {
        try {
            final CertificateIssuanceRequestDTO certificateIssuanceRequestDTO = trustCertificationService.rejectCertificateRequest(certId);
            return responseHelper.createAndSendResponse(TrustCertificationResponseCodeEnum.ISSUE_CERTIFICATE_REQUEST_REJECTED,
                    HttpStatus.OK, certificateIssuanceRequestDTO);
        } catch (final Exception ex) {
            throw new RejectCertificateRequestException(ex.getMessage(), ex);
        }
    }

    /**
     * Renew Certificate
     * @param certId
     * @param selfUser
     * @return
     * @throws java.lang.Throwable
     */
    @Operation(summary = "RENEW_CERTIFICATE - Renew Certificate", description = "Issue Certificate", tags = {"certification"})
    @RequestMapping(value = "/{certId}/renew", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForStudent
    public ResponseEntity<APIResponse<CertificateIssuedDetailDTO>> renewCertificate(
            @Parameter(name = "certId", description = "Certificate Id", required = true)
            @Valid @CertificateShouldExist(message = "{certificate_not_exist}")
            @PathVariable("certId") String certId,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser
    ) throws Throwable {
        try {
            final CertificateIssuedDetailDTO certificateIssued = trustCertificationService.renewCertificate(selfUser.getWalletHash(), certId);
            return responseHelper.createAndSendResponse(TrustCertificationResponseCodeEnum.RENEW_CERTIFICATE_FAILED,
                    HttpStatus.OK, certificateIssued);
        } catch (final Exception ex) {
            throw new RenewCertificateException(ex.getMessage(), ex);
        }
    }

    /**
     * Download Certificate File
     *
     * @param certId
     * @return
     */
    @Operation(summary = "DOWNLOAD_CERTIFICATE_FILE - Download Certificate file", description = "Download certificate file", tags = {"certification"})
    @RequestMapping(value = "/{certId}/download", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> downloadCertificateFile(
            @Parameter(name = "certId", description = "Certificate Id", required = true)
            @Valid @CertificateShouldExist(message = "{certificate_not_exist}")
            @PathVariable("certId") String certId
    ) {
        try {
            final FileInfoDTO fileInfo = trustCertificationService.getCertificateFile(certId);
            return responseHelper.createAndSendMediaResponse(fileInfo);
        } catch (final Exception ex) {
            throw new DownloadCertificateFileException(ex.getMessage(), ex);
        }
    }

    /**
     * Download Certificate Image
     *
     * @param certId
     * @return
     */
    @Operation(summary = "DOWNLOAD_CERTIFICATE_IMAGE - Download Certificate Image", description = "Download certificate image", tags = {"certification"})
    @RequestMapping(value = "/{certId}/download", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> downloadCertificateImage(
            @Parameter(name = "certId", description = "Certificate Id", required = true)
            @Valid @CertificateShouldExist(message = "{certificate_not_exist}")
            @PathVariable("certId") String certId
    ) {
        try {
            final FileInfoDTO fileInfo = trustCertificationService.getCertificateImage(certId);
            return responseHelper.createAndSendMediaResponse(fileInfo);
        } catch (final Exception ex) {
            throw new DownloadCertificateFileException(ex.getMessage(), ex);
        }
    }

    /**
     * Generate Certificate QR
     * @param certId
     * @param qrWidth
     * @param qrHeight
     * @return
     */
    @Operation(summary = "GENERATE_CERTIFICATE_QR - Get Certificate QR", description = "Generate Certificate QR", tags = {"certification"})
    @RequestMapping(value = "/{certId}/qr", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    @OnlyAccessForStudent
    public ResponseEntity<byte[]> generateCertificateQR(
            @Parameter(name = "certId", description = "Certificate Id", required = true)
            @Valid @CertificateShouldExist(message = "{certificate_not_exist}")
            @PathVariable("certId") String certId,
            @Parameter(name = "width", description = "QR Width")
            @RequestParam(name = "width", defaultValue = "200")
            final Integer qrWidth,
            @Parameter(name = "height", description = "QR Height")
            @RequestParam(name = "height", defaultValue = "200")
            final Integer qrHeight
    ) {
        try {
            final FileInfoDTO fileInfo = trustCertificationService.generateCertificateQr(certId, qrWidth, qrHeight);
            return responseHelper.createAndSendMediaResponse(fileInfo);
        } catch (final Exception ex) {
            throw new GenerateCertificateQRException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param validateCertificateDTO
     * @return
     * @throws Throwable
     */
    @Operation(summary = "VALIDATE_CERTIFICATE - Validate Certificate", description = "Validate Certificate", tags = {"certification"})
    @RequestMapping(value = "/validate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<CertificateIssuedDetailDTO>> validateCertificate(
            @Parameter(description = "Certificate Payload",
                    required = true, schema = @Schema(implementation = ValidateCertificateDTO.class))
            @Validated(ICommonSequence.class) ValidateCertificateDTO validateCertificateDTO) throws Throwable {
        try {
            final CertificateIssuedDetailDTO certificateIssuedDTO = trustCertificationService.validateCertificate(validateCertificateDTO.getPayload());
            return responseHelper.createAndSendResponse(TrustCertificationResponseCodeEnum.CERTIFICATE_IS_VALID, HttpStatus.OK,
                    certificateIssuedDTO);
        } catch (final CertificateInvalidException ex) {
            throw ex;
        } catch (final Exception ex) {
            throw new ValidateCertificateException(ex.getMessage(), ex);
        }
    }
}
