package com.dreamsoftware.blockchaingateway.broadcasters;

import com.dreamsoftware.blockchaingateway.persistence.bc.repository.ITrustCertificationBlockchainRepository;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.TrustCertificationEventEntity;
import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.AbstractEventPayload;
import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.TrustCertificationEventPayloadEntity;
import com.dreamsoftware.blockchaingateway.persistence.nosql.repository.BlockchainEventLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author ssanchez
 */
@Component
public class TrustCertificationBroadcaster extends SupportEventBroadcaster<TrustCertificationEventEntity> {

    @Autowired
    public TrustCertificationBroadcaster(BlockchainEventLogRepository blockchainEventLogRepository, ITrustCertificationBlockchainRepository blockchainEventRepository) {
        super(blockchainEventLogRepository, blockchainEventRepository);
    }

    /**
     *
     * @param event
     * @return
     */
    @Override
    AbstractEventPayload mapToPayload(TrustCertificationEventEntity event) {
        return TrustCertificationEventPayloadEntity
                .builder()
                .certificateId(event.getCertificateId())
                .type(event.getType().name())
                .build();
    }

}
