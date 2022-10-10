package com.dreamsoftware.tcs.processor.handlers.impl;

import com.dreamsoftware.tcs.persistence.bc.repository.ICertificationAuthorityBlockchainRepository;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.processor.handlers.AbstractRegistrationHandler;
import com.dreamsoftware.tcs.stream.events.user.registration.OnNewCertificationAuthorityMemberRegistrationEvent;
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
public class NewCertificationAuthorityMemberRegistrationHandler extends AbstractRegistrationHandler<OnNewCertificationAuthorityMemberRegistrationEvent> {

    /**
     * Certification Authority Blockchain Repository
     */
    private final ICertificationAuthorityBlockchainRepository certificationAuthorityBlockchainRepository;

    @Override
    public String onHandle(final OnNewCertificationAuthorityMemberRegistrationEvent event) throws RepositoryException {
        Assert.notNull(event, "Event can not be null");
        // Add Seed funds
        addSeedFunds(event.getMemberWalletHash());
        // Add initial TCS ERC-20 funds
        certificationAuthorityBlockchainRepository.addCertificationAuthorityMember(event.getCaId(), event.getMemberWalletHash(), event.getAdminWalletHash());
        return event.getMemberWalletHash();
    }
}
