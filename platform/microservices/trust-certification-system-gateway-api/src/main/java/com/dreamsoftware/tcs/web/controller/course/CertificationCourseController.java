package com.dreamsoftware.tcs.web.controller.course;

import com.dreamsoftware.tcs.services.ICertificationCourseService;
import com.dreamsoftware.tcs.web.core.APIResponse;
import com.dreamsoftware.tcs.web.controller.core.SupportController;
import com.dreamsoftware.tcs.web.controller.course.error.exception.DisableCertificationCourseException;
import com.dreamsoftware.tcs.web.controller.course.error.exception.EnableCertificationCourseException;
import com.dreamsoftware.tcs.web.controller.course.error.exception.GetCertificationCourseDetailException;
import com.dreamsoftware.tcs.web.controller.course.error.exception.SaveCertificationCourseException;
import com.dreamsoftware.tcs.web.core.ErrorResponseDTO;
import com.dreamsoftware.tcs.web.dto.request.SaveCertificationCourseDTO;
import com.dreamsoftware.tcs.web.dto.response.CertificationCourseDetailDTO;
import com.dreamsoftware.tcs.web.security.directives.CurrentUser;
import com.dreamsoftware.tcs.web.security.directives.OnlyAccessForCA;
import com.dreamsoftware.tcs.web.security.userdetails.ICommonUserDetailsAware;
import com.dreamsoftware.tcs.web.validation.constraints.ICommonSequence;
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
import javax.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Certification Course Controller
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
            certificationCourse.setCaWalletHash(selfUser.getWalletHash());
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
            final CertificationCourseDetailDTO certificationCourseDTO = certificationCourseService.enable(selfUser.getWalletHash(), id);
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
            final CertificationCourseDetailDTO certificationCourseDetailDTO = certificationCourseService.disable(selfUser.getWalletHash(), id);
            return responseHelper.<CertificationCourseDetailDTO>createAndSendResponse(
                    CertificationCourseResponseCodeEnum.CERTIFICATION_COURSE_DISABLED,
                    HttpStatus.OK, certificationCourseDetailDTO);
        } catch (final Exception ex) {
            throw new DisableCertificationCourseException(ex.getMessage(), ex);
        }
    }

    /**
     * Get Certification Course Detail
     *
     * @param id
     * @param selfUser
     * @return
     * @throws Throwable
     */
    @Operation(summary = "GET_COURSE_DETAIL - Get Course Detail", description = "Get Certification Course Detail", tags = {"COURSE"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Course Detail",
                content = @Content(schema = @Schema(implementation = CertificationCourseDetailDTO.class))),
        @ApiResponse(responseCode = "404", description = "Course Not Found",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForCA
    public ResponseEntity<APIResponse<CertificationCourseDetailDTO>> getDetailById(
            @Parameter(name = "id", description = "Course Id", required = true)
            @Valid @ShouldBeAValidObjectId(message = "course_id_valid") @PathVariable("id") String id,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<ObjectId> selfUser
    ) throws Throwable {
        try {
            final CertificationCourseDetailDTO courseDetailDTO = certificationCourseService.getDetail(selfUser.getWalletHash(), id);
            return responseHelper.<CertificationCourseDetailDTO>createAndSendResponse(
                    CertificationCourseResponseCodeEnum.CERTIFICATION_COURSE_DETAIL,
                    HttpStatus.OK, courseDetailDTO);
        } catch (final Exception ex) {
            throw new GetCertificationCourseDetailException(ex.getMessage(), ex);
        }
    }

    /**
     * Certificate Course can be issued
     *
     * @param id
     * @param selfUser
     * @return
     * @throws Throwable
     */
    @Operation(summary = "CERTIFICATE_COURSE_CAN_BE_ISSUED - Certificate Course Can be issued", description = "Certificate Course Can be issued", tags = {"COURSE"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Course Detail",
                content = @Content(schema = @Schema(implementation = CertificationCourseDetailDTO.class))),
        @ApiResponse(responseCode = "404", description = "Course Not Found",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = {"/{id}/canBeIssued"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForCA
    public ResponseEntity<APIResponse<Boolean>> canBeIssued(
            @Parameter(name = "id", description = "Course Id", required = true)
            @Valid @ShouldBeAValidObjectId(message = "course_id_valid") @PathVariable("id") String id,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<ObjectId> selfUser
    ) throws Throwable {
        try {
            final Boolean canBeIssued = certificationCourseService.canBeIssued(selfUser.getWalletHash(), id);
            return responseHelper.<Boolean>createAndSendResponse(
                    canBeIssued ? CertificationCourseResponseCodeEnum.CERTIFICATION_COURSE_CAN_BE_ISSUED
                            : CertificationCourseResponseCodeEnum.CERTIFICATION_COURSE_CANNOT_BE_ISSUED,
                    HttpStatus.OK, canBeIssued);
        } catch (final Exception ex) {
            throw new GetCertificationCourseDetailException(ex.getMessage(), ex);
        }
    }

    /**
     * Certificate Course can be renewed
     *
     * @param id
     * @param selfUser
     * @return
     * @throws Throwable
     */
    @Operation(summary = "CERTIFICATE_COURSE_CAN_BE_RENEWED - Certificate Course Can be renewed", description = "Certificate Course Can be issued", tags = {"COURSE"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Course Detail",
                content = @Content(schema = @Schema(implementation = CertificationCourseDetailDTO.class))),
        @ApiResponse(responseCode = "404", description = "Course Not Found",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = {"/{id}/canBeRenewed"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForCA
    public ResponseEntity<APIResponse<Boolean>> canBeRenewed(
            @Parameter(name = "id", description = "Course Id", required = true)
            @Valid @ShouldBeAValidObjectId(message = "course_id_valid") @PathVariable("id") String id,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<ObjectId> selfUser
    ) throws Throwable {
        try {
            final Boolean canBeRenewed = certificationCourseService.canBeRenewed(selfUser.getWalletHash(), id);
            return responseHelper.<Boolean>createAndSendResponse(
                    canBeRenewed
                            ? CertificationCourseResponseCodeEnum.CERTIFICATION_COURSE_CAN_BE_RENEWED
                            : CertificationCourseResponseCodeEnum.CERTIFICATION_COURSE_CANNOT_BE_RENEWED,
                    HttpStatus.OK, canBeRenewed);
        } catch (final Exception ex) {
            throw new GetCertificationCourseDetailException(ex.getMessage(), ex);
        }
    }
}
