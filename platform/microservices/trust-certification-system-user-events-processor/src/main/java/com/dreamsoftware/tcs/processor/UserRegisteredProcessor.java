package com.dreamsoftware.tcs.processor;

import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.stream.events.OnUserRegisteredEvent;
import com.dreamsoftware.tcs.service.IUserService;
import com.dreamsoftware.tcs.stream.events.notifications.users.UserRegisteredNotificationEvent;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * User Registered Processor
 *
 * @author ssanchez
 */
@Component("userRegisteredProcessor")
@RequiredArgsConstructor
@Slf4j
public class UserRegisteredProcessor implements Function<OnUserRegisteredEvent, UserRegisteredNotificationEvent> {

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
