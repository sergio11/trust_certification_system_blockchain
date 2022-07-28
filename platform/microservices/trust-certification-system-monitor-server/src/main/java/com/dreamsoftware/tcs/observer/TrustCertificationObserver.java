package com.dreamsoftware.tcs.observer;

import com.dreamsoftware.tcs.persistence.bc.repository.ITrustCertificationBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.TrustCertificationEventEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.AbstractEventPayload;
import com.dreamsoftware.tcs.persistence.nosql.entity.TrustCertificationEventPayloadEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.BlockchainEventLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author ssanchez
 */
@Component
public class TrustCertificationObserver extends SupportEventObserver<TrustCertificationEventEntity> {

    @Autowired
    public TrustCertificationObserver(BlockchainEventLogRepository blockchainEventLogRepository, ITrustCertificationBlockchainRepository blockchainEventRepository) {
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
