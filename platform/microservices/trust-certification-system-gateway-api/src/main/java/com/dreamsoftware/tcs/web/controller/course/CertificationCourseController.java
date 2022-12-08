package com.dreamsoftware.tcs.web.controller.course;

import com.dreamsoftware.tcs.services.ICertificationCourseService;
import com.dreamsoftware.tcs.web.controller.core.SupportController;
import com.dreamsoftware.tcs.web.controller.course.error.exception.*;
import com.dreamsoftware.tcs.web.converter.mediatype.PatchMediaType;
import com.dreamsoftware.tcs.web.core.APIResponse;
import com.dreamsoftware.tcs.web.core.ErrorResponseDTO;
import com.dreamsoftware.tcs.web.core.FileInfoDTO;
import com.dreamsoftware.tcs.web.dto.request.CourseEditionCheckInDTO;
import com.dreamsoftware.tcs.web.dto.request.SaveCertificationCourseDTO;
import com.dreamsoftware.tcs.web.dto.request.SaveCertificationCourseEditionDTO;
import com.dreamsoftware.tcs.web.dto.response.*;
import com.dreamsoftware.tcs.web.security.directives.CurrentUser;
import com.dreamsoftware.tcs.web.security.directives.OnlyAccessForAdminOrCourseOwner;
import com.dreamsoftware.tcs.web.security.directives.OnlyAccessForCA;
import com.dreamsoftware.tcs.web.security.directives.OnlyAccessForStudent;
import com.dreamsoftware.tcs.web.security.userdetails.ICommonUserDetailsAware;
import com.dreamsoftware.tcs.web.validation.constraints.*;
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
     * @return
     * @throws Throwable
     */
    @Operation(summary = "GET_ALL_COURSES - Get All courses", description = "Get All courses", tags = {"course"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Course Detail",
                content = @Content(schema = @Schema(implementation = SimpleCertificationCourseDetailDTO.class))),
        @ApiResponse(responseCode = "404", description = "Course Not Found",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = {"/all"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<Iterable<SimpleCertificationCourseDetailDTO>>> getAllCourses() throws Throwable {
        try {
            final Iterable<SimpleCertificationCourseDetailDTO> courseList = certificationCourseService.getAll();
            return responseHelper.createAndSendResponse(CertificationCourseResponseCodeEnum.GET_ALL_CERTIFICATION_COURSES,
                    HttpStatus.OK, courseList);
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
            throw new GetAllCertificationCoursesException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @return
     * @throws Throwable
     */
    @Operation(summary = "SEARCH_COURSES - Searching courses by term", description = "Searching courses by term", tags = {"course"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course Detail",
                    content = @Content(schema = @Schema(implementation = SimpleCertificationCourseDetailDTO.class))),
            @ApiResponse(responseCode = "404", description = "Course Not Found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = {"/search/{term}"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<Iterable<SimpleCertificationCourseDetailDTO>>> searchCourses(
            @Parameter(name = "term", description = "Query term", required = true)
            @PathVariable("term") String term
    ) throws Throwable {
        try {
            final Iterable<SimpleCertificationCourseDetailDTO> courseList = certificationCourseService.searchCourses(term);
            return responseHelper.createAndSendResponse(CertificationCourseResponseCodeEnum.SEARCH_CERTIFICATION_COURSES_SUCCESSFULLY,
                    HttpStatus.OK, courseList);
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
            throw new SearchCertificationCoursesException(ex.getMessage(), ex);
        }
    }

    /**
     * Get Certification Course Detail
     * @param courseId
     * @param selfUser
     * @return
     * @throws Throwable
     */
    @Operation(summary = "GET_COURSE_DETAIL - Get Course Detail", description = "Get Certification Course Detail", tags = {"course"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Course Detail",
                    content = @Content(schema = @Schema(implementation = SimpleCertificationCourseDetailDTO.class))),
            @ApiResponse(responseCode = "404", description = "Course Not Found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = {"/{courseId}"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<CertificationCourseDetailDTO>> getDetailById(
            @Parameter(name = "courseId", description = "Course Id", required = true)
            @Valid @CourseShouldExist(message = "{course_should_exist}")
            @PathVariable("courseId") String courseId,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser
    ) throws Throwable {
        try {
            final CertificationCourseDetailDTO courseDetailDTO = certificationCourseService.getDetail(selfUser.getWalletHash(), courseId);
            return responseHelper.createAndSendResponse(CertificationCourseResponseCodeEnum.CERTIFICATION_COURSE_DETAIL,
                    HttpStatus.OK, courseDetailDTO);
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
            throw new GetCertificationCourseDetailException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param selfUser
     * @return
     * @throws Throwable
     */
    @Operation(summary = "GET_ALL_COURSES_BY_CA - Get All courses by CA", description = "Get All courses by CA", tags = {"course"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Course Detail",
                content = @Content(schema = @Schema(implementation = SimpleCertificationCourseDetailDTO.class))),
        @ApiResponse(responseCode = "404", description = "Course Not Found",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = {"/"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForCA
    public ResponseEntity<APIResponse<Iterable<CertificationCourseDetailDTO>>> getAllByCA(
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser) throws Throwable {
        try {
            final Iterable<CertificationCourseDetailDTO> courseListByCa = certificationCourseService.getAllByCA(selfUser.getWalletHash());
            return responseHelper.createAndSendResponse(CertificationCourseResponseCodeEnum.GET_CERTIFICATION_COURSES_BY_CA,
                    HttpStatus.OK, courseListByCa);
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
            throw new GetAllCoursesByCaException(ex.getMessage(), ex);
        }
    }

    /**
     * Save Certification Course
     * @param certificationCourse
     * @param selfUser
     * @return
     * @throws Throwable
     */
    @Operation(summary = "SAVE_CERTIFICATION_COURSE - Save Certification Course (only access for CA users)", description = "Save Certification Course", tags = {"course"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Certification Course Detail",
                content = @Content(schema = @Schema(implementation = SimpleCertificationCourseDetailDTO.class))),
        @ApiResponse(responseCode = "500", description = "Save Certification Course exception",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = "/", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForCA
    public ResponseEntity<APIResponse<SimpleCertificationCourseDetailDTO>> save(
            @Parameter(name = "certification_course", description = "Certification Course Data. Cannot null or empty.",
                    required = true, schema = @Schema(implementation = SaveCertificationCourseDTO.class))
            @Validated(ICommonSequence.class) SaveCertificationCourseDTO certificationCourse,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser) throws Throwable {
        try {
            certificationCourse.setCaWalletHash(selfUser.getWalletHash());
            final SimpleCertificationCourseDetailDTO certificationCourseDetailDTO = certificationCourseService.save(certificationCourse);
            return responseHelper.createAndSendResponse(
                    CertificationCourseResponseCodeEnum.SAVE_CERTIFICATION_COURSE, HttpStatus.OK, certificationCourseDetailDTO);
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
            throw new SaveCertificationCourseException(ex.getMessage(), ex);
        }
    }

    /**
     * Partial Update Certification Course
     * @param selfUser
     * @param courseId
     * @param mergePatchDocument
     * @return
     */
    @Operation(summary = "PARTIAL_UPDATE_CERTIFICATION_COURSE", description = "Update Certification Course")
    @RequestMapping(value = "/{courseId}", method = RequestMethod.PATCH,
            consumes = PatchMediaType.APPLICATION_MERGE_PATCH_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForAdminOrCourseOwner
    public ResponseEntity<APIResponse<SimpleCertificationCourseDetailDTO>> partialUpdateCertificationCourse(
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser,
            @Parameter(name = "courseId", description = "Course Id", required = true)
            @Valid @CourseShouldExist(message = "{course_should_exist}")
            @PathVariable("courseId") String courseId,
            @RequestBody JsonMergePatch mergePatchDocument) {
        try {
            final SimpleCertificationCourseDetailDTO certificationCourseDetailDTO = certificationCourseService.editCertificationCourseById(courseId)
                    .map(updateCertificationCourse -> patchHelper.mergePatch(mergePatchDocument, updateCertificationCourse, UpdateCertificationCourseDTO.class))
                    .map(updateCertificationCourse -> certificationCourseService.update(courseId, updateCertificationCourse, selfUser.getWalletHash()).orElseThrow(CertificationCourseNotFoundException::new))
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
     * @param courseId
     * @param selfUser
     * @param request
     * @return
     * @throws Throwable
     */
    @Operation(summary = "ENABLE_COURSE - Enable Certification Course", description = "Enable Certification Course", tags = {"course"})
    @RequestMapping(value = "/{courseId}/enable", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForAdminOrCourseOwner
    public ResponseEntity<APIResponse<String>> enable(
            @Parameter(name = "courseId", description = "Course Id", required = true)
            @Valid @CourseShouldExist(message = "{course_should_exist}")
            @PathVariable("courseId") String courseId,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser,
            @Parameter(hidden = true) HttpServletRequest request
    ) throws Throwable {
        try {
           certificationCourseService.enable(selfUser.getWalletHash(), courseId);
            return responseHelper.createAndSendResponse(CertificationCourseResponseCodeEnum.CERTIFICATION_COURSE_ENABLED,
                    HttpStatus.OK, resolveString("enable_certification_course_success", request));
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
            throw new EnableCertificationCourseException(ex.getMessage(), ex);
        }
    }

    /**
     * Disable Certification Course
     * @param courseId
     * @param selfUser
     * @param request
     * @return
     * @throws Throwable
     */
    @Operation(summary = "DISABLE_COURSE - Disable Certification Course", description = "Disable Certification Course", tags = {"course"})
    @RequestMapping(value = "/{courseId}/disable", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForAdminOrCourseOwner
    public ResponseEntity<APIResponse<String>> disable(
            @Parameter(name = "courseId", description = "Course Id", required = true)
            @Valid @CourseShouldExist(message = "{course_should_exist}")
            @PathVariable("courseId") String courseId,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser,
            @Parameter(hidden = true) HttpServletRequest request
    ) throws Throwable {
        try {
            certificationCourseService.disable(selfUser.getWalletHash(), courseId);
            return responseHelper.createAndSendResponse(
                    CertificationCourseResponseCodeEnum.CERTIFICATION_COURSE_DISABLED,
                    HttpStatus.OK, resolveString("disable_certification_course_success", request));
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
            throw new DisableCertificationCourseException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param courseId
     * @param selfUser
     * @param request
     * @return
     * @throws Throwable
     */
    @Operation(summary = "DELETE_CERTIFICATION_COURSE - Delete Certification Course (Only Access For Admin Or Course Owner)", description = "Delete Certification Course", tags = {"course"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Certification Course Deleted",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Certification Course Not Found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = {"/{courseId}"}, method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForAdminOrCourseOwner
    public ResponseEntity<APIResponse<String>> deleteById(
            @Parameter(name = "courseId", description = "Course Id", required = true)
            @Valid @CourseShouldExist(message = "{course_should_exist}")
            @PathVariable("courseId") String courseId,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser,
            @Parameter(hidden = true) HttpServletRequest request
    ) throws Throwable {
        try {
            certificationCourseService.remove(selfUser.getWalletHash(), courseId);
            return responseHelper.createAndSendResponse(
                    CertificationCourseResponseCodeEnum.CERTIFICATION_COURSE_DELETED,
                    HttpStatus.OK, resolveString("delete_certification_course_success", request));
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
            throw new DeleteCertificationCourseException(ex.getMessage(), ex);
        }
    }

    /**
     * Save Certification Course Edition
     * @param courseId
     * @param certificationCourseEdition
     * @param selfUser
     * @return
     * @throws Throwable
     */
    @Operation(summary = "SAVE_CERTIFICATION_COURSE_EDITION - Save Certification Course Edition (only access for CA users)", description = "Save Certification Course Editions", tags = {"course"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Certification Course Edition Detail",
                    content = @Content(schema = @Schema(implementation = CertificationCourseEditionDetailDTO.class))),
            @ApiResponse(responseCode = "500", description = "Save Certification Course Edition exception",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = "/{courseId}/editions", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForAdminOrCourseOwner
    public ResponseEntity<APIResponse<CertificationCourseEditionDetailDTO>> save(
            @Parameter(name = "courseId", description = "Course Id", required = true)
            @Valid @CourseShouldExist(message = "{course_should_exist}")
            @PathVariable("courseId") String courseId,
            @Parameter(name = "certification_course_edition", description = "Certification Course Data. Cannot null or empty.",
                    required = true, schema = @Schema(implementation = SaveCertificationCourseEditionDTO.class))
            @Validated(ICommonSequence.class) SaveCertificationCourseEditionDTO certificationCourseEdition,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser) throws Throwable {
        try {
            certificationCourseEdition.setCaWalletHash(selfUser.getWalletHash());
            certificationCourseEdition.setCertificationCourseId(courseId);
            final CertificationCourseEditionDetailDTO certificationCourseEditionDetailDTO = certificationCourseService.save(certificationCourseEdition);
            return responseHelper.createAndSendResponse(CertificationCourseResponseCodeEnum.SAVE_CERTIFICATION_COURSE_EDITION_SUCCESSFULLY, HttpStatus.OK,
                    certificationCourseEditionDetailDTO);
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
            throw new SaveCertificationCourseEditionException(ex.getMessage(), ex);
        }
    }

    /**
     * Partial Update Certification Course Edition
     *
     * @param selfUser
     * @param courseId
     * @param mergePatchDocument
     * @return
     */
    @Operation(summary = "PARTIAL_UPDATE_CERTIFICATION_COURSE_EDITION", description = "Update Certification Course Edition")
    @RequestMapping(value = "/{courseId}/editions/{editionId}", method = RequestMethod.PATCH,
            consumes = PatchMediaType.APPLICATION_MERGE_PATCH_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForAdminOrCourseOwner
    public ResponseEntity<APIResponse<CertificationCourseEditionDetailDTO>> partialUpdateCertificationCourseEdition(
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser,
            @Parameter(name = "courseId", description = "Course Id", required = true)
            @Valid @CourseShouldExist(message = "{course_should_exist}")
            @PathVariable("courseId") String courseId,
            @Parameter(name = "editionId", description = "Edition Id", required = true)
            @Valid @CourseEditionShouldExist(message = "{course_edition_should_exist}")
            @PathVariable("editionId") String editionId,
            @RequestBody JsonMergePatch mergePatchDocument) {
        try {
            final CertificationCourseEditionDetailDTO certificationCourseDetailDTO = certificationCourseService.editCertificationCourseEditionById(editionId)
                    .map(updateCertificationCourseEdition -> patchHelper.mergePatch(mergePatchDocument, updateCertificationCourseEdition, UpdateCertificationCourseEditionDTO.class))
                    .map(updateCertificationCourseEdition -> certificationCourseService.update(courseId, editionId, updateCertificationCourseEdition, selfUser.getWalletHash()).orElseThrow(CertificationCourseEditionNotFoundException::new))
                    .orElseThrow(CertificationCourseEditionNotFoundException::new);
            return responseHelper.createAndSendResponse(CertificationCourseResponseCodeEnum.PARTIAL_CERTIFICATION_COURSE_EDITION_UPDATED, HttpStatus.OK, certificationCourseDetailDTO);
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
            throw new PartialUpdateCertificationCourseEditionException(ex.getMessage(), ex);
        }
    }

    /**
     * Enable Certification Course Edition
     * @param courseId
     * @param editionId
     * @param selfUser
     * @param request
     * @return
     * @throws Throwable
     */
    @Operation(summary = "ENABLE_COURSE_EDITION - Enable Certification Course Edition", description = "Enable Certification Course Edition", tags = {"course"})
    @RequestMapping(value = "/{courseId}/editions/{editionId}/enable", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForAdminOrCourseOwner
    public ResponseEntity<APIResponse<String>> enable(
            @Parameter(name = "courseId", description = "Course Id", required = true)
            @Valid @CourseShouldExist(message = "{course_should_exist}")
            @PathVariable("courseId") String courseId,
            @Parameter(name = "editionId", description = "Edition Id", required = true)
            @Valid @CourseEditionShouldExist(message = "{course_edition_should_exist}")
            @PathVariable("editionId") String editionId,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser,
            @Parameter(hidden = true) HttpServletRequest request
    ) throws Throwable {
        try {
            certificationCourseService.enable(selfUser.getWalletHash(), courseId, editionId);
            return responseHelper.createAndSendResponse(CertificationCourseResponseCodeEnum.CERTIFICATION_COURSE_EDITION_ENABLED,
                    HttpStatus.OK, resolveString("enable_certification_course_edition_success", request));
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
            throw new EnableCertificationCourseEditionException(ex.getMessage(), ex);
        }
    }

    /**
     * Disable Certification Course Edition Edition
     *
     * @param courseId
     * @param selfUser
     * @return
     * @throws Throwable
     */
    @Operation(summary = "DISABLE_COURSE_EDITION - Disable Certification Course Edition", description = "Disable Certification Course Edition", tags = {"course"})
    @RequestMapping(value = "/{courseId}/editions/{editionId}/disable", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForAdminOrCourseOwner
    public ResponseEntity<APIResponse<String>> disable(
            @Parameter(name = "courseId", description = "Course Id", required = true)
            @Valid @CourseShouldExist(message = "{course_should_exist}")
            @PathVariable("courseId") String courseId,
            @Parameter(name = "editionId", description = "Edition Id", required = true)
            @Valid @CourseEditionShouldExist(message = "{course_edition_should_exist}")
            @PathVariable("editionId") String editionId,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser,
            @Parameter(hidden = true) HttpServletRequest request
    ) throws Throwable {
        try {
            certificationCourseService.disable(selfUser.getWalletHash(), courseId, editionId);
            return responseHelper.createAndSendResponse(
                    CertificationCourseResponseCodeEnum.CERTIFICATION_COURSE_EDITION_DISABLED,
                    HttpStatus.OK, resolveString("disable_certification_course_edition_success", request));
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
            throw new DisableCertificationCourseEditionException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param courseId
     * @param editionId
     * @param selfUser
     * @param request
     * @return
     * @throws Throwable
     */
    @Operation(summary = "COURSE_EDITION_ENROLL - Allows you to enroll in a course edition that requires attendance control ", description = "Allows you to enroll in a course that requires attendance control", tags = {"course"})
    @RequestMapping(value = "/{courseId}/editions/{editionId}/enroll", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForStudent
    public ResponseEntity<APIResponse<String>> enroll(
            @Parameter(name = "courseId", description = "Course Id", required = true)
            @Valid @CourseShouldExist(message = "{course_should_exist}")
            @PathVariable("courseId") String courseId,
            @Parameter(name = "editionId", description = "Edition Id", required = true)
            @Validated(ICommonSequence.class)
            @CourseEditionShouldExist(message = "{course_edition_should_exist}")
            @CourseEditionMustAllowEnrollment(message = "{course_edition_should_allow_enrollment}", groups = {IExtended.class})
            @UserMustNotYetBeEnrolled(message = "{user_must_not_yet_be_enrolled}", groups = {IExtended.class})
            @PathVariable("editionId") String editionId,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser,
            @Parameter(hidden = true) HttpServletRequest request
    ) throws Throwable {
        try {
            certificationCourseService.enroll(selfUser.getWalletHash(), courseId, editionId);
            return responseHelper.createAndSendResponse(
                    CertificationCourseResponseCodeEnum.COURSE_EDITION_ENROLLMENT_REQUEST_COMPLETED,
                    HttpStatus.OK, resolveString("course_edition_enrollment_request_completed", request));
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
            throw new CourseEnrollmentException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param courseId
     * @param editionId
     * @param courseEditionCheckInDTO
     * @param selfUser
     * @return
     * @throws Throwable
     */
    @Operation(summary = "COURSE_EDITION_ENROLL - Allows you to justify attendance at a session of a course edition", description = "Allows you to justify attendance at a session of a course edition", tags = {"course"})
    @RequestMapping(value = "/{courseId}/editions/{editionId}/check-in", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<String>> checkIn(
            @Parameter(name = "courseId", description = "Course Id", required = true)
            @Valid @CourseShouldExist(message = "{course_should_exist}")
            @PathVariable("courseId") String courseId,
            @Parameter(name = "editionId", description = "Edition Id", required = true)
            @Validated(ICommonSequence.class)
            @CourseEditionShouldExist(message = "{course_edition_should_exist}")
            @UserMustBeEnrolled(message = "{user_must_be_enrolled}", groups = {IExtended.class})
            @CourseEditionMustAllowCheckIn(message = "{course_edition_must_allow_check_in}", groups = {IExtended.class})
            @PathVariable("editionId") String editionId,
            @Parameter(description = "Security Token",
                    required = true, schema = @Schema(implementation = CourseEditionCheckInDTO.class))
            @Validated(ICommonSequence.class) CourseEditionCheckInDTO courseEditionCheckInDTO,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser,
            @Parameter(hidden = true) HttpServletRequest request) throws Throwable {
        try {
            certificationCourseService.checkIn(selfUser.getWalletHash(), courseId, editionId, courseEditionCheckInDTO.getPayload());
            return responseHelper.createAndSendResponse(
                    CertificationCourseResponseCodeEnum.COURSE_EDITION_CHECK_IN_SUCCESSFULLY,
                    HttpStatus.OK, resolveString("course_edition_check_in_successfully", request));
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Exception ex) {
            throw new CourseEditionCheckInException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param courseId
     * @param editionId
     * @param qrWidth
     * @param qrHeight
     * @param selfUser
     * @return
     */
    @Operation(summary = "GENERATE_CERTIFICATE_QR - Get Enrollment QR", description = "Get Enrollment QR", tags = {"course"})
    @RequestMapping(value = "/{courseId}/editions/{editionId}/enrollmentQR", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    @OnlyAccessForStudent
    public ResponseEntity<byte[]> getEnrollmentQR(
            @Valid @CourseShouldExist(message = "{course_should_exist}")
            @Parameter(name = "courseId", description = "Course Id", required = true)
            @PathVariable("courseId") String courseId,
            @Validated(ICommonSequence.class)
            @CourseEditionShouldExist(message = "{course_edition_should_exist}")
            @CourseEditionMustAllowEnrollment(message = "{course_edition_should_allow_enrollment}", groups = {IExtended.class})
            @Parameter(name = "editionId", description = "Edition Id", required = true)
            @PathVariable("editionId") String editionId,
            @Parameter(name = "width", description = "QR Width")
            @RequestParam(name = "width", defaultValue = "200")
            final Integer qrWidth,
            @Parameter(name = "height", description = "QR Height")
            @RequestParam(name = "height", defaultValue = "200")
            final Integer qrHeight,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser
    ) {
        try {
            final FileInfoDTO fileInfo = certificationCourseService.getEnrollmentQR(courseId, editionId, selfUser.getWalletHash(), qrWidth, qrHeight);
            return responseHelper.createAndSendMediaResponse(fileInfo);
        } catch (final Exception ex) {
            throw new GetEnrollmentQRException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param courseId
     * @param editionId
     * @param selfUser
     * @param request
     * @return
     * @throws Throwable
     */
    @Operation(summary = "DELETE_CERTIFICATION_COURSE_EDITION - Delete Certification Course Edition (Only Access For Admin Or Course Owner)", description = "Delete Certification Course Edition", tags = {"course"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Certification Course Edition Deleted",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Certification Course Edition Not Found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = {"/{courseId}/editions/{editionId}"}, method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForAdminOrCourseOwner
    public ResponseEntity<APIResponse<String>> deleteById(
            @Parameter(name = "courseId", description = "Course Id", required = true)
            @Valid @CourseShouldExist(message = "{course_should_exist}")
            @PathVariable("courseId") String courseId,
            @Parameter(name = "editionId", description = "Edition Id", required = true)
            @Valid @CourseEditionShouldExist(message = "{course_edition_should_exist}")
            @PathVariable("editionId") String editionId,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser,
            @Parameter(hidden = true) HttpServletRequest request
    ) throws Throwable {
        try {
            certificationCourseService.remove(selfUser.getWalletHash(), courseId, editionId);
            return responseHelper.createAndSendResponse(
                    CertificationCourseResponseCodeEnum.CERTIFICATION_COURSE_EDITION_DELETED,
                    HttpStatus.OK, resolveString("delete_certification_course_edition_success", request));
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
            throw new DeleteCertificationCourseEditionException(ex.getMessage(), ex);
        }
    }

    /**
     *  Check if it is possible issue certificate for this course edition
     * @param courseEditionId
     * @return
     * @throws Throwable
     */
    @Operation(summary = "CERTIFICATE_COURSE_CAN_BE_ISSUED - Check if it is possible issue certificate for this course edition", description = "Check if it is possible issue certificate for this course edition", tags = {"course"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Can be issued or not",
                    content = @Content(schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "404", description = "Course Not Found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = {"/{courseId}/editions/{editionId}/canBeIssued"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<Boolean>> canBeIssued(
            @Parameter(name = "courseId", description = "Course Id", required = true)
            @Valid @CourseShouldExist(message = "{course_should_exist}")
            @PathVariable("courseId") String courseId,
            @Parameter(name = "editionId", description = "Course Edition Id", required = true)
            @Valid @CourseEditionShouldExist(message = "{course_edition_should_exist}")
            @PathVariable("editionId") String courseEditionId
    ) throws Throwable {
        try {
            final Boolean canBeIssued = certificationCourseService.canBeIssued(courseEditionId);
            return responseHelper.createAndSendResponse(
                    canBeIssued ? CertificationCourseResponseCodeEnum.CERTIFICATION_COURSE_CAN_BE_ISSUED
                            : CertificationCourseResponseCodeEnum.CERTIFICATION_COURSE_CANNOT_BE_ISSUED,
                    HttpStatus.OK, canBeIssued);
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
            throw new CheckCertificateCourseCanBeIssuedException(ex.getMessage(), ex);
        }
    }

    /**
     *  Check if it is possible issue certificate for this course edition
     * @param courseEditionId
     * @return
     * @throws Throwable
     */
    @Operation(summary = "CERTIFICATE_COURSE_CAN_BE_RENEWED - Check if it is possible renew certificate for this course edition", description = "Check if it is possible renew certificate for this course edition", tags = {"course"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Can be issued or not",
                    content = @Content(schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "404", description = "Course Not Found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = {"/{courseId}/editions/{editionId}/canBeRenewed"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<Boolean>> canBeRenewed(
            @Parameter(name = "courseId", description = "Course Id", required = true)
            @Valid @CourseShouldExist(message = "{course_should_exist}")
            @PathVariable("courseId") String courseId,
            @Parameter(name = "editionId", description = "Course Edition Id", required = true)
            @Valid @CourseEditionShouldExist(message = "{course_edition_should_exist}")
            @PathVariable("editionId") String courseEditionId
    ) throws Throwable {
        try {
            final Boolean canBeRenewed = certificationCourseService.canBeRenewed(courseEditionId);
            return responseHelper.createAndSendResponse(
                    canBeRenewed ? CertificationCourseResponseCodeEnum.CERTIFICATION_COURSE_CAN_BE_RENEWED
                            : CertificationCourseResponseCodeEnum.CERTIFICATION_COURSE_CANNOT_BE_RENEWED,
                    HttpStatus.OK, canBeRenewed);
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
            throw new CheckCertificateCourseCanBeRenewedException(ex.getMessage(), ex);
        }
    }
}
