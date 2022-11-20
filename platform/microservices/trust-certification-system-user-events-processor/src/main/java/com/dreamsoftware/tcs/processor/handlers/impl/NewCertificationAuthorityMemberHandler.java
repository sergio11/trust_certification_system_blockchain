package com.dreamsoftware.tcs.processor.handlers.impl;

import com.dreamsoftware.tcs.persistence.bc.repository.ICertificationAuthorityBlockchainRepository;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserTypeEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.stream.events.AbstractEvent;
import com.dreamsoftware.tcs.stream.events.notifications.users.UserRegisteredNotificationEvent;
import com.dreamsoftware.tcs.stream.events.user.NewCertificationAuthorityMemberEvent;
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
public class NewCertificationAuthorityMemberHandler extends SupportUserRegistrationHandler<NewCertificationAuthorityMemberEvent> {

    /**
     * Certification Authority Blockchain Repository
     */
    private final ICertificationAuthorityBlockchainRepository certificationAuthorityBlockchainRepository;

    /**
     * User Repository
     */
    private final UserRepository userRepository;

    @Override
    public AbstractEvent onHandle(final NewCertificationAuthorityMemberEvent event) throws RepositoryException {
        Assert.notNull(event, "Event can not be null");
        // Add Seed funds
        addSeedFunds(event.getMemberWalletHash());
        // Add initial TCS ERC-20 funds
        certificationAuthorityBlockchainRepository.addCertificationAuthorityMember(event.getCaId(), event.getMemberWalletHash(), event.getAdminWalletHash());
        // Validate User
        userRepository.updateStatusAsValidatedByWalletHash(event.getMemberWalletHash());
        return new UserRegisteredNotificationEvent(event.getMemberWalletHash(), UserTypeEnum.CA_MEMBER);
    }
}
