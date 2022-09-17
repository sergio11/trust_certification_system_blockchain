package com.dreamsoftware.tcs.web.controller.users;

import com.dreamsoftware.tcs.services.IUploadImagesService;
import com.dreamsoftware.tcs.services.IUserService;
import com.dreamsoftware.tcs.web.controller.core.SupportController;
import com.dreamsoftware.tcs.web.controller.users.error.exception.DeleteLoginHistoryException;
import com.dreamsoftware.tcs.web.controller.users.error.exception.DeleteProfileAvatarException;
import com.dreamsoftware.tcs.web.controller.users.error.exception.GetLoginHistoryException;
import com.dreamsoftware.tcs.web.controller.users.error.exception.UploadProfileImageException;
import com.dreamsoftware.tcs.web.core.APIResponse;
import com.dreamsoftware.tcs.web.dto.response.UserLoginDTO;
import com.dreamsoftware.tcs.web.dto.response.UserPhotoDTO;
import com.dreamsoftware.tcs.web.security.directives.CurrentUser;
import com.dreamsoftware.tcs.web.security.directives.OnlyAccessForAdmin;
import com.dreamsoftware.tcs.web.security.directives.OnlyAccessForCaOrStudent;
import com.dreamsoftware.tcs.web.security.userdetails.ICommonUserDetailsAware;
import com.dreamsoftware.tcs.web.uploads.models.RequestUploadFile;
import com.dreamsoftware.tcs.web.uploads.models.UploadFileInfo;
import com.dreamsoftware.tcs.web.validation.constraints.ValidPhoto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * Accounts Controller
 *
 * @author ssanchez
 */
@RestController
@Validated
@RequestMapping("/api/v1/users/")
@Tag(name = "users", description = "/api/v1/users/ (Code Response interval -> 8XX)")
@RequiredArgsConstructor
@Slf4j
public class UsersController extends SupportController {

    @Qualifier("UploadUserAvatarService")
    private final IUploadImagesService uploadUserAvatarService;
    private final IUserService userService;
    private final ResourceLoader resourceLoader;

    /**
     *
     * @param id
     * @param page
     * @param size
     * @return
     * @throws Throwable
     */
    @Operation(summary = "GET_LOGIN_HISTORY", description = "Get Login History for current user authenticated")
    @RequestMapping(value = "{id}/login_history", method = RequestMethod.GET)
    @OnlyAccessForAdmin
    public ResponseEntity<APIResponse<Page<UserLoginDTO>>> getLoginHistory(
            @Parameter(name = "id", description = "User Id", required = true)
            @PathVariable @Valid
            final String id,
            @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20") final Integer size) throws Throwable {
        try {
            final Page<UserLoginDTO> loginHistoryPage = userService.findLoginHistoryByUserIdPaginated(new ObjectId(id), page, size);
            return responseHelper.createAndSendResponse(UsersResponseCodeEnum.GET_LOGIN_HISTORY_SUCCESSFULLY,
                    HttpStatus.OK, loginHistoryPage);
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
            throw new GetLoginHistoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param id
     * @param request
     * @return
     * @throws Throwable
     */
    @Operation(summary = "DELETE_LOGIN_HISTORY", description = "Delete Login History for current user authenticated")
    @RequestMapping(value = "{id}/login_history", method = RequestMethod.DELETE)
    @OnlyAccessForAdmin
    public ResponseEntity<APIResponse<String>> deleteLoginHistory(
            @Parameter(name = "id", description = "User Id", required = true)
            @PathVariable @Valid
            final String id,
            @Parameter(hidden = true) HttpServletRequest request) throws Throwable {

        try {
            userService.deleteLoginHistoryForUserId(new ObjectId(id));
            return responseHelper.createAndSendResponse(UsersResponseCodeEnum.DELETE_LOGIN_HISTORY_SUCCESSFULLY,
                    HttpStatus.OK, resolveString("login_history_deleted", request));
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
            throw new DeleteLoginHistoryException(ex.getMessage(), ex);
        }

    }

    /**
     * Upload Profile Image for Self User
     *
     * @param avatar
     * @param selfUser
     * @return
     * @throws Throwable
     */
    @Operation(summary = "UPLOAD_PROFILE_AVATAR - Upload Profile Image", description = "Upload Profile Image", tags = {"profile"})
    @RequestMapping(value = "/avatar", method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForCaOrStudent
    public ResponseEntity<APIResponse<UserPhotoDTO>> uploadProfileImage(
            @Valid @RequestPart("avatar") @ValidPhoto(message = "{avatar_file_has_not_an_invalid_format}") MultipartFile avatar,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser) throws Throwable {

        try {
            final RequestUploadFile uploadProfileImage = RequestUploadFile.builder()
                    .bytes(avatar.getBytes())
                    .contentType(avatar.getContentType() != null ? avatar.getContentType() : MediaType.IMAGE_PNG_VALUE)
                    .originalName(avatar.getOriginalFilename())
                    .build();

            // Upload Profile Image
            UserPhotoDTO imageDTO = uploadUserAvatarService.upload(
                    selfUser.getUserId(), uploadProfileImage);

            // Create and send response
            return responseHelper.createAndSendResponse(UsersResponseCodeEnum.AVATAR_UPLOAD_SUCCESSFULLY,
                    HttpStatus.CREATED, imageDTO);
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
            throw new UploadProfileImageException(ex.getMessage(), ex);
        }

    }

    /**
     * Download Self Profile Image
     *
     * @param selfUser
     * @return
     * @throws IOException
     */
    @Operation(summary = "DOWNLOAD_SELF_PROFILE_AVATAR - Download Self Profile Image", description = "Download Self Profile Image", tags = {"profile"})
    @RequestMapping(value = "/avatar", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> downloadProfileImage(
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser
    ) throws IOException {

        UploadFileInfo uploadFileInfo;
        try {
            uploadFileInfo = uploadUserAvatarService.getInfoForUser(selfUser.getUserId());
        } catch (final Exception ex) {
            uploadFileInfo = getUserDefaultAvatarImage();
        }
        return responseHelper.createAndSendMediaResponse(uploadFileInfo);

    }

    /**
     * Delete Self Profile Avatar
     *
     * @param selfUser
     * @param request
     * @return
     */
    @Operation(summary = "DELETE_SELF_PROFILE_AVATAR - Delete Self Profile Avatar", description = "Delete Self Profile Avatar", tags = {"profile"})
    @RequestMapping(value = "/avatar", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForCaOrStudent
    public ResponseEntity<APIResponse<String>> deleteProfileAvatar(
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser,
            @Parameter(hidden = true) HttpServletRequest request) {

        try {
            uploadUserAvatarService.deleteForUser(selfUser.getUserId());
            // Create And Send Response
            return responseHelper.createAndSendResponse(UsersResponseCodeEnum.AVATAR_DELETED_SUCCESSFULLY,
                    HttpStatus.OK, resolveString("profile_image_deleted_successfully", request));
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
            throw new DeleteProfileAvatarException(ex.getMessage(), ex);
        }
    }

    /**
     * Get User Default Avatar Image
     *
     * @return
     * @throws IOException
     */
    private UploadFileInfo getUserDefaultAvatarImage() throws IOException {
        final org.springframework.core.io.Resource userDefault = resourceLoader.getResource("classpath:user_default_avatar.png");
        return new UploadFileInfo(1, userDefault.contentLength(), MediaType.IMAGE_PNG_VALUE,
                IOUtils.toByteArray(userDefault.getInputStream()));
    }

}
