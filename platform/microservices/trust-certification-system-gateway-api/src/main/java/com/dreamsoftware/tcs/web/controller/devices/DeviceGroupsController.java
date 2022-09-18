package com.dreamsoftware.tcs.web.controller.devices;

import com.dreamsoftware.tcs.services.IDeviceGroupsService;
import com.dreamsoftware.tcs.web.core.APIResponse;
import com.dreamsoftware.tcs.web.controller.core.SupportController;
import com.dreamsoftware.tcs.web.controller.devices.error.exception.AddDeviceToGroupException;
import com.dreamsoftware.tcs.web.controller.devices.error.exception.GetDevicesByOwnerException;
import com.dreamsoftware.tcs.web.controller.devices.error.exception.RemoveDeviceFromGroupFailedException;
import com.dreamsoftware.tcs.web.controller.devices.error.exception.SaveDeviceException;
import com.dreamsoftware.tcs.web.controller.devices.error.exception.UpdateDeviceFailedException;
import com.dreamsoftware.tcs.web.core.ErrorResponseDTO;
import com.dreamsoftware.tcs.web.dto.request.AddDeviceDTO;
import com.dreamsoftware.tcs.web.dto.request.SaveDeviceDTO;
import com.dreamsoftware.tcs.web.dto.request.UpdateDeviceDTO;
import com.dreamsoftware.tcs.web.dto.response.DeviceDTO;
import com.dreamsoftware.tcs.web.security.directives.CurrentUser;
import com.dreamsoftware.tcs.web.security.userdetails.ICommonUserDetailsAware;
import com.dreamsoftware.tcs.web.validation.constraints.ICommonSequence;
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
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/api/v1/device-groups/")
@Tag(name = "devices-groups", description = "/api/v1/device-groups/ (Code Response interval -> 6XX)")
@RequiredArgsConstructor
@Slf4j
public class DeviceGroupsController extends SupportController {

    /**
     * Device Groups Service
     */
    private final IDeviceGroupsService deviceGroupsService;

    /**
     *
     * @param selfUser
     * @return
     * @throws Throwable
     */
    @Operation(summary = "GET_DEVICES_BY_OWNER - Get all devices registered owned by the user", description = "Get all devices registered owned by the user", tags = {"device-groups"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "All devices registered in the group",
                content = @Content(schema = @Schema(implementation = DeviceDTO.class))),
        @ApiResponse(responseCode = "500", description = "An exception ocurred",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = {"/", "/all"}, method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<APIResponse<Iterable<DeviceDTO>>> getDevicesIntoGroup(
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser) throws Throwable {
        try {
            final Iterable<DeviceDTO> devices = deviceGroupsService.getDevicesByOwner(selfUser.getUserId());
            return responseHelper.createAndSendResponse(DeviceGroupsResponseCodeEnum.GET_DEVICES_BY_OWNER, HttpStatus.OK, devices);
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
            throw new GetDevicesByOwnerException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param device
     * @param selfUser
     * @param request
     * @return
     */
    @Operation(summary = "ADD_DEVICE_TO_GROUP - Add device into devices group", description = "Add device into devices group", tags = {"device-groups"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Device Added",
                content = @Content(schema = @Schema(implementation = DeviceDTO.class))),
        @ApiResponse(responseCode = "500", description = "An exception ocurred",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = "/devices/add", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<String>> addDeviceToGroup(
            @Parameter(name = "device", description = "Device to be added",
                    required = true, schema = @Schema(implementation = AddDeviceDTO.class))
            @Validated(ICommonSequence.class) AddDeviceDTO device,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser,
            @Parameter(hidden = true) final HttpServletRequest request
    ) {
        try {
            deviceGroupsService.addDeviceToGroup(selfUser.getUserId(), device.getDeviceId(), device.getRegistrationToken());
            return responseHelper.createAndSendResponse(DeviceGroupsResponseCodeEnum.DEVICE_ADDED_TO_GROUP,
                    HttpStatus.OK, resolveString("device_added_successfully", request));
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
            throw new AddDeviceToGroupException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param updateDevice
     * @param selfUser
     * @param request
     * @return
     */
    @Operation(summary = "UPDATE_DEVICE_REGISTRATION_TOKEN - Update Device Registration Token", description = "Update Device Registration Token", tags = {"device-groups"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Device Updated",
                content = @Content(schema = @Schema(implementation = DeviceDTO.class))),
        @ApiResponse(responseCode = "500", description = "An exception ocurred",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = "/devices/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<String>> updateDeviceRegistrationToken(
            @Parameter(name = "device", description = "Device to be updated",
                    required = true, schema = @Schema(implementation = UpdateDeviceDTO.class))
            @Validated(ICommonSequence.class) UpdateDeviceDTO updateDevice,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser,
            @Parameter(hidden = true) final HttpServletRequest request) {
        try {
            deviceGroupsService.updateDevice(selfUser.getUserId(), updateDevice.getDeviceId(), updateDevice.getRegistrationToken());
            return responseHelper.createAndSendResponse(DeviceGroupsResponseCodeEnum.DEVICE_TOKEN_UPDATED,
                    HttpStatus.OK, resolveString("device_updated_successfully", request));
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
            throw new UpdateDeviceFailedException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param deviceToSave
     * @param selfUser
     * @param request
     * @return
     */
    @Operation(summary = "SAVE_DEVICE - Save device into the group", description = "Save device into the group", tags = {"device-groups"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Device Saved",
                content = @Content(schema = @Schema(implementation = DeviceDTO.class))),
        @ApiResponse(responseCode = "500", description = "An exception ocurred",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = "/devices/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<String>> saveDevices(
            @Parameter(name = "device", description = "Device to be saved",
                    required = true, schema = @Schema(implementation = UpdateDeviceDTO.class))
            @Validated(ICommonSequence.class) SaveDeviceDTO deviceToSave,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser,
            @Parameter(hidden = true) final HttpServletRequest request) {
        try {
            log.debug("Save Device with Id " + deviceToSave.getDeviceId() + " And Registration Token " + deviceToSave.getRegistrationToken());
            deviceGroupsService.createOrUpdateDevice(selfUser.getUserId(), deviceToSave.getDeviceId(), deviceToSave.getRegistrationToken());
            return responseHelper.createAndSendResponse(DeviceGroupsResponseCodeEnum.DEVICE_TOKEN_SAVED,
                    HttpStatus.OK, resolveString("device_saved_successfully", request));
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
            throw new SaveDeviceException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param id
     * @param selfUser
     * @param request
     * @return
     */
    @Operation(summary = "DELETE_DEVICE_FROM_GROUP - Delete Device From Group", description = "Delete Device From Group", tags = {"device-groups"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Device Deleted",
                content = @Content(schema = @Schema(implementation = DeviceDTO.class))),
        @ApiResponse(responseCode = "500", description = "An exception ocurred",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @RequestMapping(value = "/devices/{id}/delete", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse<String>> deleteDeviceFromGroup(
            @Parameter(name = "id", description = "Device Id", required = true)
            @PathVariable("id") String id,
            @Parameter(hidden = true) @CurrentUser ICommonUserDetailsAware<String> selfUser,
            @Parameter(hidden = true) final HttpServletRequest request
    ) {
        try {
            log.debug("Remove device with id " + id);
            deviceGroupsService.removeDeviceFromGroup(id);
            return responseHelper.createAndSendResponse(DeviceGroupsResponseCodeEnum.DEVICE_REMOVED_FROM_GROUP,
                    HttpStatus.OK, resolveString("device_deleted_successfully", request));
        } catch (final ConstraintViolationException ex) {
            throw ex;
        } catch (final Throwable ex) {
            throw new RemoveDeviceFromGroupFailedException(ex.getMessage(), ex);
        }
    }
}
