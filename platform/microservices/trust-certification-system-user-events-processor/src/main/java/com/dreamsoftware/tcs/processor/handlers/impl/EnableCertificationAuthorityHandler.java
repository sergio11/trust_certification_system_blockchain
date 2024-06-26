package com.dreamsoftware.tcs.processor.handlers.impl;

import com.dreamsoftware.tcs.persistence.bc.repository.ICertificationAuthorityBlockchainRepository;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserAccountStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.stream.events.AbstractEvent;
import com.dreamsoftware.tcs.stream.events.notifications.ca.CertificationAuthorityEnabledNotificationEvent;
import com.dreamsoftware.tcs.stream.events.user.EnableCertificationAuthorityEvent;
import com.dreamsoftware.tcs.utils.AbstractProcessAndReturnHandler;
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
public class EnableCertificationAuthorityHandler extends AbstractProcessAndReturnHandler<EnableCertificationAuthorityEvent> {

    /**
     * Certification Authority Blockchain Repository
     */
    private final ICertificationAuthorityBlockchainRepository certificationAuthorityBlockchainRepository;

    /**
     * User Repository
     */
    private final UserRepository userRepository;

    @Override
    public AbstractEvent onHandle(final EnableCertificationAuthorityEvent event) throws RepositoryException {
        Assert.notNull(event, "Event can not be null");
        certificationAuthorityBlockchainRepository.enable(event.getCaId());
        // Enable all users belong to this CA
        userRepository.updateAccountStateByCaId(event.getCaId(), UserAccountStateEnum.ENABLED);
        return new CertificationAuthorityEnabledNotificationEvent(event.getCaId());
    }
}
