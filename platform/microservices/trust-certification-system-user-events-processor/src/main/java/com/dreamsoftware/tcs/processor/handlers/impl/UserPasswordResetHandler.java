package com.dreamsoftware.tcs.processor.handlers.impl;

import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.processor.handlers.AbstractUserManagementHandler;
import com.dreamsoftware.tcs.service.IPasswordResetTokenService;
import com.dreamsoftware.tcs.stream.events.notifications.users.UserPasswordResetNotificationEvent;
import com.dreamsoftware.tcs.stream.events.user.UserPasswordResetEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Optional;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
@Slf4j
public class UserPasswordResetHandler extends AbstractUserManagementHandler<UserPasswordResetEvent, UserPasswordResetNotificationEvent> {

    /**
     * Password Reset Token Service
     */
    private final IPasswordResetTokenService passwordResetTokenService;

    /**
     * User Repository
     */
    private final UserRepository userRepository;

    @Override
    public UserPasswordResetNotificationEvent onHandle(final UserPasswordResetEvent event) throws RepositoryException {
        Assert.notNull(event, "Event can not be null");
        final String resetToken = Optional.ofNullable(passwordResetTokenService.getPasswordResetTokenForUserWithEmail(event.getEmail()))
                .orElseGet(() -> passwordResetTokenService.createPasswordResetTokenForUserWithEmail(event.getEmail()));
        final UserEntity userEntity = userRepository.updateLastPasswordReset(event.getEmail(), resetToken);
        return new UserPasswordResetNotificationEvent(userEntity.getEmail(), userEntity.getFullName(), userEntity.getConfirmationToken());
    }
}
