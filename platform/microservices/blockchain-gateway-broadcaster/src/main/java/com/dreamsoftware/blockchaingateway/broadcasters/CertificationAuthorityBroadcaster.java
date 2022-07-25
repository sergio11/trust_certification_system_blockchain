package com.dreamsoftware.blockchaingateway.broadcasters;

import com.dreamsoftware.blockchaingateway.persistence.bc.repository.ICertificationAuthorityBlockchainRepository;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.CertificationAuthorityEventEntity;
import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.AbstractEventPayload;
import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.CertificationAuthorityEventPayloadEntity;
import com.dreamsoftware.blockchaingateway.persistence.nosql.repository.BlockchainEventLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author ssanchez
 */
@Component
public class CertificationAuthorityBroadcaster extends SupportEventBroadcaster<CertificationAuthorityEventEntity> {

    @Autowired
    public CertificationAuthorityBroadcaster(BlockchainEventLogRepository blockchainEventLogRepository, ICertificationAuthorityBlockchainRepository blockchainEventRepository) {
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
