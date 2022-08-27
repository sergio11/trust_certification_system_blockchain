package com.dreamsoftware.tcs.fcm.service.impl;

import com.dreamsoftware.tcs.fcm.dto.request.DevicesGroupOperationDTO;
import com.dreamsoftware.tcs.fcm.dto.request.DevicesGroupOperationType;
import com.dreamsoftware.tcs.fcm.dto.request.FCMNotificationOperationDTO;
import com.dreamsoftware.tcs.fcm.dto.response.FirebaseResponseDTO;
import com.dreamsoftware.tcs.fcm.properties.FCMCustomProperties;
import com.dreamsoftware.tcs.fcm.service.IPushNotificationsService;
import com.dreamsoftware.tcs.utils.Unthrow;
import com.google.common.collect.Lists;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.scheduling.annotation.Async;

/**
 *
 * @author ssanchez
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PushNotificationsServiceImpl implements IPushNotificationsService {

    @Qualifier("fcmRestTemplate")
    private final RestTemplate restTemplate;
    private final FCMCustomProperties firebaseCustomProperties;

    private final static ParameterizedTypeReference<Map<String, String>> typeRef = new ParameterizedTypeReference<Map<String, String>>() {
    };

    /**
     *
     * @param notificationGroupName
     * @param deviceTokens
     * @return
     */
    @Async
    @Override
    public CompletableFuture<ResponseEntity<Map<String, String>>> createNotificationGroup(final String notificationGroupName, final List<String> deviceTokens) {
        Assert.notNull(notificationGroupName, "notificationGroupName can not be null");
        Assert.notNull(deviceTokens, "deviceTokens can not be null");
        Assert.notNull(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be null");
        Assert.hasLength(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be empty");

        log.debug("Notification Group Name  -> " + notificationGroupName + " device tokens -> " + deviceTokens.toString());

        return executeDeviceOperation(DevicesGroupOperationDTO
                .builder()
                .operation(DevicesGroupOperationType.CREATE)
                .groupName(notificationGroupName)
                .deviceTokens(deviceTokens)
                .build());
    }

    /**
     *
     * @param notificationGroupName
     * @param notificationGroupKey
     * @param deviceTokens
     * @return
     */
    @Async
    @Override
    public CompletableFuture<ResponseEntity<Map<String, String>>> addDevicesToGroup(final String notificationGroupName, final String notificationGroupKey, final List<String> deviceTokens) {
        Assert.notNull(notificationGroupName, "notificationGroupName can not be null");
        Assert.notNull(notificationGroupKey, "Notification Group Key can not be null");
        Assert.notEmpty(deviceTokens, "Device Tokens can not be empty");
        Assert.notNull(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be null");
        Assert.hasLength(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be empty");

        log.debug("addDevicesToGroup  -> " + notificationGroupName + " notificationGroupKey  -> " + notificationGroupKey + " device tokens -> " + deviceTokens.toString());

        return executeDeviceOperation(DevicesGroupOperationDTO
                .builder()
                .operation(DevicesGroupOperationType.ADD)
                .groupName(notificationGroupName)
                .groupKey(notificationGroupKey)
                .deviceTokens(deviceTokens)
                .build());
    }

    /**
     *
     * @param notificationGroupName
     * @param notificationGroupKey
     * @param deviceToken
     * @return
     */
    @Async
    @Override
    public CompletableFuture<ResponseEntity<Map<String, String>>> addDeviceToGroup(final String notificationGroupName, final String notificationGroupKey, final String deviceToken) {
        Assert.notNull(notificationGroupName, "notificationGroupName can not be null");
        Assert.notNull(notificationGroupKey, "Notification Group Key can not be null");
        Assert.notNull(deviceToken, "Device Token can not be empty");
        Assert.notNull(firebaseCustomProperties.getGroupPrefix(), "Group Prefix can not be null");
        Assert.notNull(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be null");
        Assert.hasLength(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be empty");

        log.debug(" addDeviceToGroup -> " + notificationGroupName + " notificationGroupKey -> " + notificationGroupKey + "deviceToken -> " + deviceToken);

        return executeDeviceOperation(DevicesGroupOperationDTO
                .builder()
                .operation(DevicesGroupOperationType.ADD)
                .groupName(notificationGroupName)
                .groupKey(notificationGroupKey)
                .deviceTokens(Lists.newArrayList(deviceToken))
                .build());
    }

    /**
     *
     * @param notificationGroupName
     * @param notificationGroupKey
     * @param deviceTokens
     * @return
     */
    @Async
    @Override
    public CompletableFuture<ResponseEntity<Map<String, String>>> removeDevicesFromGroup(final String notificationGroupName, final String notificationGroupKey, final List<String> deviceTokens) {
        Assert.notNull(notificationGroupName, "notificationGroupName can not be null");
        Assert.notNull(notificationGroupKey, "Notification Group Key can not be null");
        Assert.notEmpty(deviceTokens, "Device Tokens can not be empty");
        Assert.notNull(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be null");
        Assert.hasLength(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be empty");

        log.debug("removeDevicesFromGroup -> " + notificationGroupName + " notificationGroupKey -> " + notificationGroupKey + "deviceTokens -> " + deviceTokens.toString());

        return executeDeviceOperation(DevicesGroupOperationDTO
                .builder()
                .operation(DevicesGroupOperationType.REMOVE)
                .groupName(notificationGroupName)
                .groupKey(notificationGroupKey)
                .deviceTokens(deviceTokens)
                .build());
    }

    /**
     *
     * @param notificationGroupName
     * @param notificationGroupKey
     * @param deviceToken
     * @return
     */
    @Async
    @Override
    public CompletableFuture<ResponseEntity<Map<String, String>>> removeDeviceFromGroup(final String notificationGroupName, final String notificationGroupKey, final String deviceToken) {
        Assert.notNull(notificationGroupName, "notificationGroupName can not be null");
        Assert.notNull(notificationGroupKey, "Notification Group Key can not be null");
        Assert.notNull(deviceToken, "Device Token can not be empty");
        Assert.notNull(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be null");
        Assert.hasLength(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be empty");

        log.debug(" removeDeviceFromGroup -> " + notificationGroupName + " notificationGroupKey -> " + notificationGroupKey + "deviceToken -> " + deviceToken);

        return executeDeviceOperation(DevicesGroupOperationDTO
                .builder()
                .operation(DevicesGroupOperationType.REMOVE)
                .groupName(notificationGroupName)
                .groupKey(notificationGroupKey)
                .deviceTokens(Lists.newArrayList(deviceToken))
                .build());
    }

    /**
     *
     * @param fcmNotificationOperation
     * @return
     */
    @Async
    @Override
    public CompletableFuture<ResponseEntity<FirebaseResponseDTO>> send(final FCMNotificationOperationDTO fcmNotificationOperation) {
        Assert.notNull(fcmNotificationOperation, "FCM Notification operation can not be null");
        Assert.notNull(firebaseCustomProperties.getGroupPrefix(), "Group Prefix can not be null");
        Assert.notNull(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be null");
        Assert.hasLength(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be empty");

        log.debug(" send Firebase Notification " + fcmNotificationOperation.toString());

        return CompletableFuture.supplyAsync(() -> restTemplate.postForEntity(firebaseCustomProperties.getNotificationSendUrl(),
                fcmNotificationOperation, FirebaseResponseDTO.class));
    }

    /**
     *
     * @param notificationGroupName
     * @param notificationGroupKey
     * @param oldDeviceToken
     * @param newDeviceToken
     * @return
     */
    @Async
    @Override
    public CompletableFuture<Void> updateDeviceToken(final String notificationGroupName, final String notificationGroupKey, final String oldDeviceToken, final String newDeviceToken) {
        Assert.notNull(notificationGroupName, "notificationGroupName can not be null");
        Assert.notNull(notificationGroupKey, "Notification Group Key can not be null");
        Assert.notNull(oldDeviceToken, "Old Device Token can not be empty");
        Assert.notNull(newDeviceToken, "New Device Token can not be empty");
        Assert.notNull(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be null");
        Assert.hasLength(firebaseCustomProperties.getNotificationGroupsUrl(), "Notification Group Url can not be empty");

        log.debug(" updateDeviceToken  -> " + notificationGroupName + " notificationGroupKey ->  " + notificationGroupKey + " oldDeviceToken -> " + oldDeviceToken + "newDeviceToken -> " + newDeviceToken);

        return CompletableFuture.allOf(
                CompletableFuture.supplyAsync(() -> restTemplate.postForObject(firebaseCustomProperties.getNotificationGroupsUrl(),
                DevicesGroupOperationDTO
                        .builder()
                        .operation(DevicesGroupOperationType.REMOVE)
                        .groupName(notificationGroupName)
                        .groupKey(notificationGroupKey)
                        .deviceTokens(Lists.newArrayList(oldDeviceToken))
                        .build(), String.class)),
                CompletableFuture.supplyAsync(() -> restTemplate.postForObject(firebaseCustomProperties.getNotificationGroupsUrl(),
                DevicesGroupOperationDTO
                        .builder()
                        .operation(DevicesGroupOperationType.ADD)
                        .groupName(notificationGroupName)
                        .groupKey(notificationGroupKey)
                        .deviceTokens(Lists.newArrayList(newDeviceToken))
                        .build(), String.class)));
    }

    /**
     *
     * @param deviceOperation
     * @return
     */
    private CompletableFuture<ResponseEntity<Map<String, String>>> executeDeviceOperation(final DevicesGroupOperationDTO deviceOperation) {
        return CompletableFuture.supplyAsync(() -> Unthrow.wrap(() -> restTemplate.exchange(new URI(firebaseCustomProperties.getNotificationGroupsUrl()), HttpMethod.POST,
                new HttpEntity<>(deviceOperation), typeRef)));
    }

    @PostConstruct
    protected void init() {
        Assert.notNull(restTemplate, "Rest Template can not be null");
        Assert.notNull(firebaseCustomProperties, "Firebase Custom Properties can not be null");
        Assert.hasLength(firebaseCustomProperties.getAppServerKey(), "App Server Key can not be empty");
        log.debug("App Server Key -> " + firebaseCustomProperties.getAppServerKey());
    }

}
