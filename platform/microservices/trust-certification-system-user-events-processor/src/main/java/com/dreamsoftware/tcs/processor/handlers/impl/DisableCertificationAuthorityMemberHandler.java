package com.dreamsoftware.tcs.processor.handlers.impl;

import com.dreamsoftware.tcs.persistence.bc.repository.ICertificationAuthorityBlockchainRepository;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserAccountStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserTypeEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.processor.handlers.AbstractUserManagementHandler;
import com.dreamsoftware.tcs.stream.events.notifications.AbstractNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.users.UserRegisteredNotificationEvent;
import com.dreamsoftware.tcs.stream.events.user.DisableCertificationAuthorityMemberEvent;
import com.dreamsoftware.tcs.stream.events.user.NewCertificationAuthorityMemberEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
@Slf4j
public class DisableCertificationAuthorityMemberHandler extends AbstractUserManagementHandler<DisableCertificationAuthorityMemberEvent> {

    /**
     * Certification Authority Blockchain Repository
     */
    private final ICertificationAuthorityBlockchainRepository certificationAuthorityBlockchainRepository;

    /**
     * User Repository
     */
    private final UserRepository userRepository;

    @Override
    public AbstractNotificationEvent onHandle(final DisableCertificationAuthorityMemberEvent event) throws RepositoryException {
        Assert.notNull(event, "Event can not be null");
        final UserEntity caMember = userRepository.findById(new ObjectId(event.getMemberId()))
                .orElseThrow(() -> new IllegalStateException("CA member not found"));
        final UserEntity caAdmin = userRepository.findById(new ObjectId(event.getAdminId()))
                .orElseThrow(() -> new IllegalStateException("CA Admin not found"));
        certificationAuthorityBlockchainRepository.disableMember(event.getCaId(), caMember.getWalletHash(), caAdmin.getWalletHash());
        userRepository.updateAccountStateByWalletHash(caMember.getWalletHash(), UserAccountStateEnum.DISABLED);
        return null;
    }
}
