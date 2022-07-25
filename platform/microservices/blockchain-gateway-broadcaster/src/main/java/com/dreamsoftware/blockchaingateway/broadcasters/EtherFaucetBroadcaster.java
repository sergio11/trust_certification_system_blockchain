package com.dreamsoftware.blockchaingateway.broadcasters;

import com.dreamsoftware.blockchaingateway.persistence.bc.repository.IEtherFaucetBlockchainRepository;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.EtherFaucetEventEntity;
import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.AbstractEventPayload;
import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.EtherFaucetEventPayloadEntity;
import com.dreamsoftware.blockchaingateway.persistence.nosql.repository.BlockchainEventLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author ssanchez
 */
@Component
public class EtherFaucetBroadcaster extends SupportEventBroadcaster<EtherFaucetEventEntity> {

    @Autowired
    public EtherFaucetBroadcaster(BlockchainEventLogRepository blockchainEventLogRepository, IEtherFaucetBlockchainRepository blockchainEventRepository) {
        super(blockchainEventLogRepository, blockchainEventRepository);
    }

    /**
     *
     * @param event
     * @return
     */
    @Override
    AbstractEventPayload mapToPayload(EtherFaucetEventEntity event) {
        return EtherFaucetEventPayloadEntity
                .builder()
                .amount(event.getAmount())
                .type(event.getType().name())
                .build();
    }

}
