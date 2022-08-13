package com.dreamsoftware.tcs.web.controller.notification;

import com.dreamsoftware.tcs.services.INotificationService;
import com.dreamsoftware.tcs.web.controller.core.SupportController;
import com.dreamsoftware.tcs.web.controller.notification.error.exception.DeleteNotificationException;
import com.dreamsoftware.tcs.web.controller.notification.error.exception.GetNotificationDetailException;
import com.dreamsoftware.tcs.web.controller.notification.error.exception.NoNotificationsFoundException;
import com.dreamsoftware.tcs.web.core.APIResponse;
import com.dreamsoftware.tcs.web.core.ErrorResponseDTO;
import com.dreamsoftware.tcs.web.dto.response.NotificationDTO;
import com.dreamsoftware.tcs.web.security.directives.CurrentUser;
import com.dreamsoftware.tcs.web.security.directives.OnlyAccessForAdmin;
import com.dreamsoftware.tcs.web.security.directives.OnlyAccessForAdminOrNotificationOwner;
import com.dreamsoftware.tcs.web.security.userdetails.ICommonUserDetailsAware;
import com.dreamsoftware.tcs.web.validation.constraints.ShouldBeAValidObjectId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Notifications Controller
 *
 * @author ssanchez
 */
@RestController
@Validated
@RequestMapping("/api/v1/notifications/")
@Tag(name = "notifications", description = "/api/v1/notifications/ (Code Response interval -> 6XX)")
@RequiredArgsConstructor
public class NotificationsController extends SupportController {

    /**
     * Notification Service
     */
    private final INotificationService notificationService;

    /**
     *
     * @param page
     * @param size
     * @param id
     * @return
     * @throws Throwable
     */
    @Operation(summary = "GET_NOTIFICATIONS_BY_USER - Get notifications for specific user (only access for ADMIN users)", description = "Get notifications for specific user", tags = {"notifications"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "All notifications for the user",
                content = @Content(
                        schema = @Schema(implementation = Page.class))),
        @ApiResponse(responseCode = "404", description = "No notifications found",
                content = @Content(
                        schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.GET, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @OnlyAccessForAdmin
    public ResponseEntity<APIResponse<Page<NotificationDTO>>> getNotificationsForUser(
            @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20") final Integer size,
            @PathVariable @Valid @ShouldBeAValidObjectId(message = "{notification_id_not_valid}") final String id) throws Throwable {

        return getNotifications(new ObjectId(id), page, size);
    }

    /**
     *
     * @param page
     * @param size
     * @param selfUser
     * @return
     * @throws Throwable
     */
    @Operation(summary = "GET_MY_NOTIFICATIONS - Get my notifications", description = "Get my notifications", tags = {"notifications"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "All my notifications",
                content = @Content(
                        schema = @Schema(implementation = Page.class))),
        @ApiResponse(responseCode = "404", description = "No notifications found",
                content = @Content(
                        schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.GET, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponse<Page<NotificationDTO>>> getMyNotifications(
            @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20") final Integer size,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<ObjectId> selfUser) throws Throwable {

        return getNotifications(selfUser.getUserId(), page, size);
    }

    /**
     * Get Notification Detail
     *
     * @param id
     * @param selfUser
     * @return
     * @throws Throwable
     */
    @Operation(summary = "GET_NOTIFICATION_DETAIL - Get Notification Detail", description = "Get Notification Detail", tags = {"notifications"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notification Detail",
                content = @Content(schema = @Schema(implementation = NotificationDTO.class))),
        @ApiResponse(responseCode = "404", description = "Notification Not Found",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForAdminOrNotificationOwner
    public ResponseEntity<APIResponse<NotificationDTO>> getById(
            @Parameter(name = "id", description = "Notification Id", required = true)
            @Valid @ShouldBeAValidObjectId(message = "{notification_id_not_valid}") @PathVariable("id") String id,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<ObjectId> selfUser
    ) throws Throwable {
        try {
            final NotificationDTO notification = notificationService.findById(new ObjectId(id));
            return responseHelper.<NotificationDTO>createAndSendResponse(
                    NotificationsResponseCodeEnum.NOTIFICATION_DETAIL,
                    HttpStatus.OK, notification);
        } catch (final Exception ex) {
            throw new GetNotificationDetailException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param id
     * @param selfUser
     * @param request
     * @return
     * @throws Throwable
     */
    @Operation(summary = "DELETE_NOTIFICATION - Delete Notification", description = "Delete Notification", tags = {"notifications"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notification Deleted",
                content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "404", description = "Notification Not Found",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @OnlyAccessForAdminOrNotificationOwner
    public ResponseEntity<APIResponse<String>> deleteById(
            @Parameter(name = "id", description = "Notification Id", required = true)
            @Valid @ShouldBeAValidObjectId(message = "{notification_id_not_valid}") @PathVariable("id") String id,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<ObjectId> selfUser,
            @Parameter(hidden = true) HttpServletRequest request
    ) throws Throwable {
        try {
            notificationService.deleteById(new ObjectId(id));
            return responseHelper.<String>createAndSendResponse(
                    NotificationsResponseCodeEnum.NOTIFICATION_DELETED,
                    HttpStatus.OK, resolveString("notification_deleted", request));
        } catch (final Exception ex) {
            throw new DeleteNotificationException(ex.getMessage(), ex);
        }
    }

    /**
     * Private Methods
     */
    /**
     *
     * @param userId
     * @param page
     * @param size
     * @return
     */
    private ResponseEntity<APIResponse<Page<NotificationDTO>>> getNotifications(final ObjectId userId, final Integer page, final Integer size) {
        final Page<NotificationDTO> notificationsPage = notificationService.findPaginated(userId, page, size);

        if (!notificationsPage.hasContent()) {
            throw new NoNotificationsFoundException();
        }

        return responseHelper.createAndSendResponse(NotificationsResponseCodeEnum.GET_USER_NOTIFICATIONS_SUCCESS,
                HttpStatus.OK, notificationsPage);
    }

}
