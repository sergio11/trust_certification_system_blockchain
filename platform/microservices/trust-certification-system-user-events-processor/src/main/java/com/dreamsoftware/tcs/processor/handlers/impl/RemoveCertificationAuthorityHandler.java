package com.dreamsoftware.tcs.processor.handlers.impl;

import com.dreamsoftware.tcs.persistence.bc.repository.ICertificationAuthorityBlockchainRepository;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserAccountStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.processor.handlers.AbstractUserManagementHandler;
import com.dreamsoftware.tcs.stream.events.notifications.AbstractNotificationEvent;
import com.dreamsoftware.tcs.stream.events.user.RemoveCertificationAuthorityEvent;
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
public class RemoveCertificationAuthorityHandler extends AbstractUserManagementHandler<RemoveCertificationAuthorityEvent> {

    /**
     * Certification Authority Blockchain Repository
     */
    private final ICertificationAuthorityBlockchainRepository certificationAuthorityBlockchainRepository;

    /**
     * User Repository
     */
    private final UserRepository userRepository;

    @Override
    public AbstractNotificationEvent onHandle(final RemoveCertificationAuthorityEvent event) throws RepositoryException {
        Assert.notNull(event, "Event can not be null");
        certificationAuthorityBlockchainRepository.removeCertificationAuthority(event.getCaId());
        userRepository.updateAccountStateByCaId(event.getCaId(), UserAccountStateEnum.REMOVED);
        return null;
    }
}
