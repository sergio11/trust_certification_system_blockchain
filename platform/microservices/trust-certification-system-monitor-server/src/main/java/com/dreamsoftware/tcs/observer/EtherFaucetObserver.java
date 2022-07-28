package com.dreamsoftware.tcs.observer;

import com.dreamsoftware.tcs.persistence.bc.repository.IEtherFaucetBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.EtherFaucetEventEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.AbstractEventPayload;
import com.dreamsoftware.tcs.persistence.nosql.entity.EtherFaucetEventPayloadEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.BlockchainEventLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author ssanchez
 */
@Component
public class EtherFaucetObserver extends SupportEventObserver<EtherFaucetEventEntity> {

    @Autowired
    public EtherFaucetObserver(BlockchainEventLogRepository blockchainEventLogRepository, IEtherFaucetBlockchainRepository blockchainEventRepository) {
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
