package com.dreamsoftware.blockchaingateway.web.controller;

import com.dreamsoftware.blockchaingateway.services.ICertificationAuthorityService;
import com.dreamsoftware.blockchaingateway.web.controller.error.exception.RegisterCertificationAuthorityException;
import com.dreamsoftware.blockchaingateway.web.core.APIResponse;
import com.dreamsoftware.blockchaingateway.web.core.SupportController;
import com.dreamsoftware.blockchaingateway.web.dto.request.RegisterCertificationAuthorityDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
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
            @Validated RegisterCertificationAuthorityDTO registerCertificationAuthority) throws Throwable {
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
