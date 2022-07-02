package com.dreamsoftware.blockchaingateway.web.controller.certification;

import com.dreamsoftware.blockchaingateway.services.ITrustCertificationService;
import com.dreamsoftware.blockchaingateway.web.controller.certification.error.exception.DisableCertificateException;
import com.dreamsoftware.blockchaingateway.web.controller.certification.error.exception.EnableCertificateException;
import com.dreamsoftware.blockchaingateway.web.core.APIResponse;
import com.dreamsoftware.blockchaingateway.web.controller.core.SupportController;
import com.dreamsoftware.blockchaingateway.web.dto.response.CertificateIssuedDTO;
import com.dreamsoftware.blockchaingateway.web.security.directives.CurrentUser;
import com.dreamsoftware.blockchaingateway.web.security.userdetails.ICommonUserDetailsAware;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    public ResponseEntity<APIResponse<CertificateIssuedDTO>> enable(
            @Parameter(name = "id", description = "Certificate Id", required = true)
            @PathVariable("id") String id,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<ObjectId> selfUser
    ) throws Throwable {
        try {
            final CertificateIssuedDTO certificateIssuedDTO = trustCertificationService.enable(selfUser.getWallet(), id);
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
    public ResponseEntity<APIResponse<CertificateIssuedDTO>> disable(
            @Parameter(name = "id", description = "Certificate Id", required = true)
            @PathVariable("id") String id,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<ObjectId> selfUser
    ) throws Throwable {
        try {
            final CertificateIssuedDTO certificateIssuedDTO = trustCertificationService.disable(selfUser.getWallet(), id);
            return responseHelper.<CertificateIssuedDTO>createAndSendResponse(TrustCertificationResponseCodeEnum.CERTIFICATE_ISSUED_DISABLED,
                    HttpStatus.OK, certificateIssuedDTO);
        } catch (final Exception ex) {
            throw new DisableCertificateException(ex.getMessage(), ex);
        }
    }
}
