package com.dreamsoftware.blockchaingateway.web.controller;

import com.dreamsoftware.blockchaingateway.services.ICertificationAuthorityService;
import com.dreamsoftware.blockchaingateway.web.core.APIResponse;
import com.dreamsoftware.blockchaingateway.web.core.SupportController;
import com.dreamsoftware.blockchaingateway.web.dto.request.SaveCertificationAuthorityDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    final ICertificationAuthorityService certificationAuthorityService;

    @Operation(summary = "REGISTER_CERTIFICATION_AUTHORITY", description = "Save User Address")
    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<String>> save(
            @Parameter(name = "certificationAuthority", description = "Certification Authority Data", required = true)
            @Validated SaveCertificationAuthorityDTO certificationAuthority) throws Throwable {

        return null;
    }
}
