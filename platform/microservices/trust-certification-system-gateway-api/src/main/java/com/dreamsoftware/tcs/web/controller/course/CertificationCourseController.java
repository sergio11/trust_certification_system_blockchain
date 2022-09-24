package com.dreamsoftware.tcs.web.controller.course;

import com.dreamsoftware.tcs.services.ICertificationCourseService;
import com.dreamsoftware.tcs.web.core.APIResponse;
import com.dreamsoftware.tcs.web.controller.core.SupportController;
import com.dreamsoftware.tcs.web.controller.course.error.exception.CertificationCourseNotFoundException;
import com.dreamsoftware.tcs.web.controller.course.error.exception.DeleteCertificationCourseException;
import com.dreamsoftware.tcs.web.controller.course.error.exception.DisableCertificationCourseException;
import com.dreamsoftware.tcs.web.controller.course.error.exception.EnableCertificationCourseException;
import com.dreamsoftware.tcs.web.controller.course.error.exception.GetAllCertificationCoursesException;
import com.dreamsoftware.tcs.web.controller.course.error.exception.GetAllCoursesByCaException;
import com.dreamsoftware.tcs.web.controller.course.error.exception.GetCertificationCourseDetailException;
import com.dreamsoftware.tcs.web.controller.course.error.exception.PartialUpdateCertificationCourseException;
import com.dreamsoftware.tcs.web.controller.course.error.exception.SaveCertificationCourseException;
import com.dreamsoftware.tcs.web.converter.mediatype.PatchMediaType;
import com.dreamsoftware.tcs.web.core.ErrorResponseDTO;
import com.dreamsoftware.tcs.web.dto.request.SaveCertificationCourseDTO;
import com.dreamsoftware.tcs.web.dto.response.CertificationCourseDetailDTO;
import com.dreamsoftware.tcs.web.dto.response.UpdateCertificationCourseDTO;
import com.dreamsoftware.tcs.web.security.directives.CurrentUser;
import com.dreamsoftware.tcs.web.security.directives.OnlyAccessForAdmin;
import com.dreamsoftware.tcs.web.security.directives.OnlyAccessForAdminOrCourseOwner;
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
import javax.json.JsonMergePatch;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
     *
     * @return @throws Throwable
     */
    @Operation(summary = "GET_ALL_COURSES - Get All courses (Only Access For Admin)", description = "Get All courses", tags = {"COURSE"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Course Detail",
                content = @Content(schema = @Schema(implementation = CertificationCourseDetailDTO.class))),
        @ApiResponse(responseCode = "404", description = "Course Not Found",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = {"/all"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForAdmin
    public ResponseEntity<APIResponse<Iterable<CertificationCourseDetailDTO>>> getAllCourses() throws Throwable {
        try {
            final Iterable<CertificationCourseDetailDTO> courseList = certificationCourseService.getAll();
            return responseHelper.<Iterable<CertificationCourseDetailDTO>>createAndSendResponse(
                    CertificationCourseResponseCodeEnum.GET_ALL_CERTIFICATION_COURSES,
                    HttpStatus.OK, courseList);
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
            throw new GetAllCertificationCoursesException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param selfUser
     * @return
     * @throws Throwable
     */
    @Operation(summary = "GET_ALL_COURSES_BY_CA - Get All courses by CA", description = "Get All courses by CA", tags = {"COURSE"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Course Detail",
                content = @Content(schema = @Schema(implementation = CertificationCourseDetailDTO.class))),
        @ApiResponse(responseCode = "404", description = "Course Not Found",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = {"/"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForCA
    public ResponseEntity<APIResponse<Iterable<CertificationCourseDetailDTO>>> getAllByCA(
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser) throws Throwable {
        try {
            final Iterable<CertificationCourseDetailDTO> courseListByCa = certificationCourseService.getAllByCA(selfUser.getWalletHash());
            return responseHelper.<Iterable<CertificationCourseDetailDTO>>createAndSendResponse(
                    CertificationCourseResponseCodeEnum.GET_CERTIFICATION_COURSES_BY_CA,
                    HttpStatus.OK, courseListByCa);
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
            throw new GetAllCoursesByCaException(ex.getMessage(), ex);
        }
    }

    /**
     * Save Certification Course
     *
     * @param certificationCourse
     * @param selfUser
     * @param request
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
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser,
            @Parameter(hidden = true) HttpServletRequest request) throws Throwable {
        try {
            certificationCourse.setCaWalletHash(selfUser.getWalletHash());
            certificationCourseService.save(certificationCourse);
            return responseHelper.createAndSendResponse(
                    CertificationCourseResponseCodeEnum.SAVE_CERTIFICATION_COURSE, HttpStatus.OK,
                    resolveString("new_course_registration_requested", request));
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
            throw new SaveCertificationCourseException(ex.getMessage(), ex);
        }
    }

    /**
     * Partial Update Certification Course
     *
     * @param selfUser
     * @param id
     * @param mergePatchDocument
     * @return
     */
    @Operation(summary = "PARTIAL_UPDATE_CERTIFICATION_COURSE", description = "Update Certification Course")
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH,
            consumes = PatchMediaType.APPLICATION_MERGE_PATCH_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForCA
    public ResponseEntity<APIResponse<CertificationCourseDetailDTO>> partialUpdateCertificationCourse(
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser,
            @Parameter(name = "id", description = "Course Id", required = true)
            @PathVariable("id") String id,
            @RequestBody JsonMergePatch mergePatchDocument) {

        try {
            final CertificationCourseDetailDTO certificationCourseDetailDTO = certificationCourseService.editById(id)
                    .map(updateCertificationCourse -> patchHelper.mergePatch(mergePatchDocument, updateCertificationCourse, UpdateCertificationCourseDTO.class))
                    .map(updateCertificationCourse -> certificationCourseService.update(id, updateCertificationCourse, selfUser.getWalletHash()).orElseThrow(CertificationCourseNotFoundException::new))
                    .orElseThrow(CertificationCourseNotFoundException::new);
            return responseHelper.createAndSendResponse(CertificationCourseResponseCodeEnum.PARTIAL_CERTIFICATION_COURSE_UPDATE, HttpStatus.OK, certificationCourseDetailDTO);
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
            throw new PartialUpdateCertificationCourseException(ex.getMessage(), ex);
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
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser
    ) throws Throwable {
        try {
            final CertificationCourseDetailDTO certificationCourseDTO = certificationCourseService.enable(selfUser.getWalletHash(), id);
            return responseHelper.<CertificationCourseDetailDTO>createAndSendResponse(CertificationCourseResponseCodeEnum.CERTIFICATION_COURSE_ENABLED,
                    HttpStatus.OK, certificationCourseDTO);
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
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
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser
    ) throws Throwable {
        try {
            final CertificationCourseDetailDTO certificationCourseDetailDTO = certificationCourseService.disable(selfUser.getWalletHash(), id);
            return responseHelper.<CertificationCourseDetailDTO>createAndSendResponse(
                    CertificationCourseResponseCodeEnum.CERTIFICATION_COURSE_DISABLED,
                    HttpStatus.OK, certificationCourseDetailDTO);
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
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
            @PathVariable("id") String id,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser
    ) throws Throwable {
        try {
            final CertificationCourseDetailDTO courseDetailDTO = certificationCourseService.getDetail(selfUser.getWalletHash(), id);
            return responseHelper.<CertificationCourseDetailDTO>createAndSendResponse(
                    CertificationCourseResponseCodeEnum.CERTIFICATION_COURSE_DETAIL,
                    HttpStatus.OK, courseDetailDTO);
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
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
            @PathVariable("id") String id,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser
    ) throws Throwable {
        try {
            final Boolean canBeIssued = certificationCourseService.canBeIssued(selfUser.getWalletHash(), id);
            return responseHelper.<Boolean>createAndSendResponse(
                    canBeIssued ? CertificationCourseResponseCodeEnum.CERTIFICATION_COURSE_CAN_BE_ISSUED
                            : CertificationCourseResponseCodeEnum.CERTIFICATION_COURSE_CANNOT_BE_ISSUED,
                    HttpStatus.OK, canBeIssued);
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
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
            @PathVariable("id") String id,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser
    ) throws Throwable {
        try {
            final Boolean canBeRenewed = certificationCourseService.canBeRenewed(selfUser.getWalletHash(), id);
            return responseHelper.<Boolean>createAndSendResponse(
                    canBeRenewed
                            ? CertificationCourseResponseCodeEnum.CERTIFICATION_COURSE_CAN_BE_RENEWED
                            : CertificationCourseResponseCodeEnum.CERTIFICATION_COURSE_CANNOT_BE_RENEWED,
                    HttpStatus.OK, canBeRenewed);
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
            throw new GetCertificationCourseDetailException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param id
     * @param selfUser
     * @return
     * @throws Throwable
     */
    @Operation(summary = "DELETE_CERTIFICATION_COURSE - Delete Certification Course (Only Access For Admin Or Course Owner)", description = "Delete Certification Course", tags = {"COURSE"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Certification Course Deleted",
                content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "404", description = "Certification Course Not Found",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForAdminOrCourseOwner
    public ResponseEntity<APIResponse<CertificationCourseDetailDTO>> deleteById(
            @Parameter(name = "id", description = "Course Id", required = true)
            @PathVariable("id") String id,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser
    ) throws Throwable {
        try {
            final CertificationCourseDetailDTO certificationCourseDetailDTO = certificationCourseService.remove(selfUser.getWalletHash(), id);
            return responseHelper.<CertificationCourseDetailDTO>createAndSendResponse(
                    CertificationCourseResponseCodeEnum.CERTIFICATION_COURSE_DELETED,
                    HttpStatus.OK, certificationCourseDetailDTO);
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
            throw new DeleteCertificationCourseException(ex.getMessage(), ex);
        }
    }
}
