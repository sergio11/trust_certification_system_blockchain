package com.dreamsoftware.blockchaingateway.web.controller.course;

import com.dreamsoftware.blockchaingateway.services.ICertificationCourseService;
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
@RequestMapping("/api/v1/course/")
@Tag(name = "course", description = "/api/v1/course/ (Code Response interval -> 3XX)")
@RequiredArgsConstructor
public class CertificationCourseController extends SupportController {

    /**
     * Certification Course Service
     */
    private final ICertificationCourseService certificationCourseService;

    /**
     * Save Certification Course
     *
     * @param certificationCourse
     * @param selfUser
     * @return
     * @throws Throwable
     */
    @Operation(summary = "SAVE_CERTIFICATION_COURSE - Save Certification Course (only access for CA users)", description = "Save Certification Course", tags = {"course"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Certification Course Detail",
                content = @Content(schema = @Schema(implementation = CertificationCourseDetailDTO.class))),
        @ApiResponse(responseCode = "500", description = "Save Certification Course exception",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = "/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForCA
    public ResponseEntity<APIResponse<String>> save(
            @Parameter(name = "certification_course", description = "Certification Course Data. Cannot null or empty.",
                    required = true, schema = @Schema(implementation = SaveCertificationCourseDTO.class))
            @Validated(ICommonSequence.class) SaveCertificationCourseDTO certificationCourse,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<ObjectId> selfUser) throws Throwable {
        try {
            certificationCourse.setCaWallet(selfUser.getWallet());
            certificationCourseService.save(certificationCourse);
            return responseHelper.createAndSendResponse(
                    CertificationCourseResponseCodeEnum.SAVE_CERTIFICATION_COURSE, HttpStatus.OK,
                    "Course certificate registration request made");
        } catch (final Exception ex) {
            throw new SaveCertificationCourseException(ex.getMessage(), ex);
        }
    }

    /**
     * Enable Certification Course
     *
     * @param id
     * @param selfUser
     * @return
     * @throws java.lang.Throwable
     */
    @Operation(summary = "ENABLE_COURSE - Enable Certification Course", description = "Enable Certification Course", tags = {"COURSE"})
    @RequestMapping(value = "/{id}/enable", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForCA
    public ResponseEntity<APIResponse<CertificationCourseDetailDTO>> enable(
            @Parameter(name = "id", description = "Course Id", required = true)
            @PathVariable("id") String id,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<ObjectId> selfUser
    ) throws Throwable {
        try {
            final CertificationCourseDetailDTO certificationCourseDTO = certificationCourseService.enable(selfUser.getWallet(), id);
            return responseHelper.<CertificationCourseDetailDTO>createAndSendResponse(CertificationCourseResponseCodeEnum.CERTIFICATION_COURSE_ENABLED,
                    HttpStatus.OK, certificationCourseDTO);
        } catch (final Exception ex) {
            throw new EnableCertificationCourseException(ex.getMessage(), ex);
        }
    }

    /**
     * Disable Certification Course
     *
     * @param id
     * @param selfUser
     * @return
     * @throws Throwable
     */
    @Operation(summary = "DISABLE_COURSE - Disable Certification Course", description = "Disable Certification Course", tags = {"COURSE"})
    @RequestMapping(value = "/{id}/disable", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForCA
    public ResponseEntity<APIResponse<CertificationCourseDetailDTO>> disable(
            @Parameter(name = "id", description = "Course Id", required = true)
            @PathVariable("id") String id,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<ObjectId> selfUser
    ) throws Throwable {
        try {
            final CertificationCourseDetailDTO certificationCourseDetailDTO = certificationCourseService.disable(selfUser.getWallet(), id);
            return responseHelper.<CertificationCourseDetailDTO>createAndSendResponse(
                    CertificationCourseResponseCodeEnum.CERTIFICATION_COURSE_DISABLED,
                    HttpStatus.OK, certificationCourseDetailDTO);
        } catch (final Exception ex) {
            throw new DisableCertificationCourseException(ex.getMessage(), ex);
        }
    }
}
