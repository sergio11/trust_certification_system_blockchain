package com.dreamsoftware.tcs.processor;

import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.service.IUserService;
import com.dreamsoftware.tcs.stream.events.notifications.users.UserRegisteredNotificationEvent;
import com.dreamsoftware.tcs.stream.events.user.OnUserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * User Management Processor
 *
 * @author ssanchez
 */
@Component("userManagementProcessor")
@RequiredArgsConstructor
@Slf4j
public class UserManagementProcessor implements Function<OnUserRegisteredEvent, UserRegisteredNotificationEvent> {

    /**
     * User Service
     */
    private final IUserService userService;

    /**
     *
     * @param event
     * @return
     */
    @Override
    public UserRegisteredNotificationEvent apply(final OnUserRegisteredEvent event) {
        log.debug("UserRegisteredProcessor CALLED!");
        final UserEntity userEntity = userService.validate(event.getWalletHash());
        return new UserRegisteredNotificationEvent(userEntity.getWalletHash());
    }
}
