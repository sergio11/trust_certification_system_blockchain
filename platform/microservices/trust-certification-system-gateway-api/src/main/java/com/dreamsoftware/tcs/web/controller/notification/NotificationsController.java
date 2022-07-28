package com.dreamsoftware.tcs.web.controller.notification;

import com.dreamsoftware.tcs.services.INotificationService;
import com.dreamsoftware.tcs.web.controller.core.SupportController;
import com.dreamsoftware.tcs.web.controller.notification.error.exception.NoNotificationsFoundException;
import com.dreamsoftware.tcs.web.core.APIResponse;
import com.dreamsoftware.tcs.web.core.ErrorResponseDTO;
import com.dreamsoftware.tcs.web.dto.response.NotificationDTO;
import com.dreamsoftware.tcs.web.security.directives.OnlyAccessForAdmin;
import com.dreamsoftware.tcs.web.validation.constraints.ShouldBeAValidObjectId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    private final INotificationService notificationService;

    /**
     *
     * @param page
     * @param size
     * @param id
     * @return
     * @throws Throwable
     */
    @Operation(summary = "GET_ALL_USER_ALERTS - Get all Alerts for the user", description = "Get all Alerts for the user", tags = {"alerts"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "All alerts for the user",
                content = @Content(
                        schema = @Schema(implementation = Page.class))),
        @ApiResponse(responseCode = "404", description = "No Alerts found",
                content = @Content(
                        schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = {"/{id}"}, method = RequestMethod.GET, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @OnlyAccessForAdmin
    public ResponseEntity<APIResponse<Page<NotificationDTO>>> getAllAlerts(
            @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "20") final Integer size,
            @PathVariable @Valid @ShouldBeAValidObjectId(message = "Invalid Id Format") final String id) throws Throwable {

        final Page<NotificationDTO> notificationsPage = notificationService.findPaginated(new ObjectId(id), page, size);

        if (!notificationsPage.hasContent()) {
            throw new NoNotificationsFoundException();
        }

        return responseHelper.createAndSendResponse(
                NotificationsResponseCodeEnum.GET_ALL_USER_NOTIFICATIONS_SUCCESS,
                HttpStatus.OK, notificationsPage);
    }

}
