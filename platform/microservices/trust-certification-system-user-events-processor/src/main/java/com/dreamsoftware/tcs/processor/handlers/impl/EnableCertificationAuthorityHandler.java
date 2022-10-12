package com.dreamsoftware.tcs.processor.handlers.impl;

import com.dreamsoftware.tcs.persistence.bc.repository.ICertificationAuthorityBlockchainRepository;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserAccountStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.processor.handlers.AbstractUserManagementHandler;
import com.dreamsoftware.tcs.stream.events.notifications.users.CertificationAuthorityEnabledNotificationEvent;
import com.dreamsoftware.tcs.stream.events.user.EnableCertificationAuthorityEvent;
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
public class EnableCertificationAuthorityHandler extends AbstractUserManagementHandler<EnableCertificationAuthorityEvent, CertificationAuthorityEnabledNotificationEvent> {

    /**
     * Certification Authority Blockchain Repository
     */
    private final ICertificationAuthorityBlockchainRepository certificationAuthorityBlockchainRepository;

    /**
     * User Repository
     */
    private final UserRepository userRepository;

    @Override
    public CertificationAuthorityEnabledNotificationEvent onHandle(final EnableCertificationAuthorityEvent event) throws RepositoryException {
        Assert.notNull(event, "Event can not be null");
        certificationAuthorityBlockchainRepository.enable(event.getCaId());
        // Enable all users belong to this CA
        userRepository.updateAccountStateByCaId(event.getCaId(), UserAccountStateEnum.ENABLED);
        return new CertificationAuthorityEnabledNotificationEvent(event.getCaId());
    }
}
