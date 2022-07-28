package com.dreamsoftware.tcs.observer;

import com.dreamsoftware.tcs.persistence.bc.repository.ICertificationAuthorityBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationAuthorityEventEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.AbstractEventPayload;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationAuthorityEventPayloadEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.BlockchainEventLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author ssanchez
 */
@Component
public class CertificationAuthorityObserver extends SupportEventObserver<CertificationAuthorityEventEntity> {

    @Autowired
    public CertificationAuthorityObserver(BlockchainEventLogRepository blockchainEventLogRepository, ICertificationAuthorityBlockchainRepository blockchainEventRepository) {
        super(blockchainEventLogRepository, blockchainEventRepository);
    }

    /**
     *
     * @param event
     * @return
     */
    @Override
    AbstractEventPayload mapToPayload(CertificationAuthorityEventEntity event) {
        return CertificationAuthorityEventPayloadEntity
                .builder()
                .name(event.getName())
                .type(event.getType().name())
                .build();
    }
}
