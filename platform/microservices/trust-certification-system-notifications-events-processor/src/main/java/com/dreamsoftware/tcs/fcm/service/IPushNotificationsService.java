package com.dreamsoftware.tcs.fcm.service;

import com.dreamsoftware.tcs.fcm.dto.request.FCMNotificationOperationDTO;
import com.dreamsoftware.tcs.fcm.dto.response.FirebaseResponseDTO;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.Map;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author ssanchez
 */
public interface IPushNotificationsService {

    /**
     *
     * @param notificationGroupName
     * @param deviceTokens
     * @return
     */
    CompletableFuture<ResponseEntity<Map<String, String>>> createNotificationGroup(final String notificationGroupName, final List<String> deviceTokens);

    /**
     *
     * @param notificationGroupName
     * @param notificationGroupKey
     * @param deviceTokens
     * @return
     */
    CompletableFuture<ResponseEntity<Map<String, String>>> addDevicesToGroup(final String notificationGroupName, final String notificationGroupKey, final List<String> deviceTokens);

    /**
     *
     * @param notificationGroupName
     * @param notificationGroupKey
     * @param deviceToken
     * @return
     */
    CompletableFuture<ResponseEntity<Map<String, String>>> addDeviceToGroup(final String notificationGroupName, final String notificationGroupKey, final String deviceToken);

    /**
     *
     * @param notificationGroupName
     * @param notificationGroupKey
     * @param deviceTokens
     * @return
     */
    CompletableFuture<ResponseEntity<Map<String, String>>> removeDevicesFromGroup(final String notificationGroupName, final String notificationGroupKey, final List<String> deviceTokens);

    /**
     *
     * @param notificationGroupName
     * @param notificationGroupKey
     * @param deviceToken
     * @return
     */
    CompletableFuture<ResponseEntity<Map<String, String>>> removeDeviceFromGroup(final String notificationGroupName, final String notificationGroupKey, final String deviceToken);

    /**
     *
     * @param fcmNotificationOperation
     * @return
     */
    CompletableFuture<ResponseEntity<FirebaseResponseDTO>> send(final FCMNotificationOperationDTO fcmNotificationOperation);

    /**
     *
     * @param notificationGroupName
     * @param notificationGroupKey
     * @param oldDeviceToken
     * @param newDeviceToken
     * @return
     */
    CompletableFuture<Void> updateDeviceToken(final String notificationGroupName, final String notificationGroupKey, final String oldDeviceToken, final String newDeviceToken);
}
