package com.dreamsoftware.tcs.processor.handlers.impl;

import com.dreamsoftware.tcs.persistence.bc.repository.ITokenManagementBlockchainRepository;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserTypeEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.stream.events.notifications.users.UserRegisteredNotificationEvent;
import com.dreamsoftware.tcs.stream.events.user.NewStudentEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
@Slf4j
public class NewStudentUserManagementHandler extends SupportUserRegistrationHandler<NewStudentEvent> {

    /**
     * Token Management Blockchain Repository
     */
    private final ITokenManagementBlockchainRepository tokenManagementBlockchainRepository;

    /**
     * User Repository
     */
    private final UserRepository userRepository;

    @Override
    public UserRegisteredNotificationEvent onHandle(final NewStudentEvent event) throws RepositoryException {
        Assert.notNull(event, "Event can not be null");
        // Add Seed funds
        addSeedFunds(event.getWalletHash());
        // Add initial TCS ERC-20 funds
        tokenManagementBlockchainRepository.configureInitialTokenFundsToStudent(event.getWalletHash());
        // Validate User
        userRepository.updateStatusAsValidatedByWalletHash(event.getWalletHash());
        return new UserRegisteredNotificationEvent(event.getWalletHash(), UserTypeEnum.STUDENT);
    }
}
