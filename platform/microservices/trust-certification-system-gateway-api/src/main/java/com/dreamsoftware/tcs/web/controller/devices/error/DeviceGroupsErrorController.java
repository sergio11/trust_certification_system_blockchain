package com.dreamsoftware.tcs.web.controller.devices.error;

import com.dreamsoftware.tcs.web.controller.core.SupportController;
import com.dreamsoftware.tcs.web.controller.devices.DeviceGroupsResponseCodeEnum;
import com.dreamsoftware.tcs.web.controller.devices.error.exception.AddDeviceToGroupException;
import com.dreamsoftware.tcs.web.controller.devices.error.exception.GetDevicesByOwnerException;
import com.dreamsoftware.tcs.web.controller.devices.error.exception.RemoveDeviceFromGroupFailedException;
import com.dreamsoftware.tcs.web.controller.devices.error.exception.SaveDeviceException;
import com.dreamsoftware.tcs.web.controller.devices.error.exception.UpdateDeviceFailedException;
import com.dreamsoftware.tcs.web.core.APIResponse;
import com.dreamsoftware.tcs.web.core.ErrorResponseDTO;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author ssanchez
 */
@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DeviceGroupsErrorController extends SupportController {

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(AddDeviceToGroupException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleAddDeviceToGroupException(final AddDeviceToGroupException ex, final HttpServletRequest request) {
        log.error("Handler for AddDeviceToGroupException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(DeviceGroupsResponseCodeEnum.ADD_DEVICE_TO_GROUP_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, resolveString("add_device_to_group_failed", request));
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(GetDevicesByOwnerException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleGetDevicesByOwnerException(final GetDevicesByOwnerException ex, final HttpServletRequest request) {
        log.error("Handler for GetDevicesByOwnerException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(DeviceGroupsResponseCodeEnum.GET_DEVICES_BY_OWNER_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, resolveString("get_devices_by_owner_failed", request));
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(RemoveDeviceFromGroupFailedException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleRemoveDeviceFromGroupFailedException(final RemoveDeviceFromGroupFailedException ex, final HttpServletRequest request) {
        log.error("Handler for RemoveDeviceFromGroupFailedException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(DeviceGroupsResponseCodeEnum.REMOVE_DEVICE_FROM_GROUP_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, resolveString("remove_device_from_group_failed", request));
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(SaveDeviceException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleSaveDeviceException(final SaveDeviceException ex, final HttpServletRequest request) {
        log.error("Handler for SaveDeviceException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(DeviceGroupsResponseCodeEnum.SAVE_DEVICE_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, resolveString("save_device_failed", request));
    }

    /**
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(UpdateDeviceFailedException.class)
    @ResponseBody
    protected ResponseEntity<APIResponse<ErrorResponseDTO>> handleUpdateDeviceFailedException(final UpdateDeviceFailedException ex, final HttpServletRequest request) {
        log.error("Handler for UpdateDeviceFailedException -> " + ex.getMessage());
        return responseHelper.createAndSendErrorResponse(DeviceGroupsResponseCodeEnum.UPDATE_DEVICE_FAILED,
                HttpStatus.INTERNAL_SERVER_ERROR, resolveString("update_device_failed", request));
    }
}
