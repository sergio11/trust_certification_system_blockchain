package com.dreamsoftware.tcs.processor.handlers.impl;

import com.dreamsoftware.tcs.persistence.bc.repository.ICertificationAuthorityBlockchainRepository;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserAccountStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.stream.events.AbstractEvent;
import com.dreamsoftware.tcs.stream.events.notifications.ca.CertificationAuthorityDisabledNotificationEvent;
import com.dreamsoftware.tcs.stream.events.user.DisableCertificationAuthorityEvent;
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
public class DisableCertificationAuthorityHandler extends AbstractProcessAndReturnHandler<DisableCertificationAuthorityEvent> {

    /**
     * Certification Authority Blockchain Repository
     */
    private final ICertificationAuthorityBlockchainRepository certificationAuthorityBlockchainRepository;

    /**
     * User Repository
     */
    private final UserRepository userRepository;

    @Override
    public AbstractEvent onHandle(final DisableCertificationAuthorityEvent event) throws RepositoryException {
        Assert.notNull(event, "Event can not be null");
        certificationAuthorityBlockchainRepository.disable(event.getCaId());
        // Disable all users belong to this CA
        userRepository.updateAccountStateByCaId(event.getCaId(), UserAccountStateEnum.DISABLED);
        return new CertificationAuthorityDisabledNotificationEvent(event.getCaId());
    }
}
