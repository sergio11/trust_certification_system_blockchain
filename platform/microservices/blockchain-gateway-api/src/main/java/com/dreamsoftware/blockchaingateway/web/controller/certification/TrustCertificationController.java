package com.dreamsoftware.blockchaingateway.web.controller.certification;

import com.dreamsoftware.blockchaingateway.web.controller.course.*;
import com.dreamsoftware.blockchaingateway.services.ICertificationCourseService;
import com.dreamsoftware.blockchaingateway.services.ITrustCertificationService;
import com.dreamsoftware.blockchaingateway.web.core.APIResponse;
import com.dreamsoftware.blockchaingateway.web.controller.core.SupportController;
import com.dreamsoftware.blockchaingateway.web.controller.course.error.exception.DisableCertificationCourseException;
import com.dreamsoftware.blockchaingateway.web.controller.course.error.exception.EnableCertificationCourseException;
import com.dreamsoftware.blockchaingateway.web.controller.course.error.exception.SaveCertificationCourseException;
import com.dreamsoftware.blockchaingateway.web.core.ErrorResponseDTO;
import com.dreamsoftware.blockchaingateway.web.dto.request.SaveCertificationCourseDTO;
import com.dreamsoftware.blockchaingateway.web.dto.response.CertificationCourseDetailDTO;
import com.dreamsoftware.blockchaingateway.web.security.directives.CurrentUser;
import com.dreamsoftware.blockchaingateway.web.security.directives.OnlyAccessForCA;
import com.dreamsoftware.blockchaingateway.web.security.userdetails.ICommonUserDetailsAware;
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

}
