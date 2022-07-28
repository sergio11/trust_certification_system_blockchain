package com.dreamsoftware.tcs.observer;

import com.dreamsoftware.tcs.persistence.bc.repository.ICertificationCourseBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationCourseEventEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.AbstractEventPayload;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEventPayloadEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.BlockchainEventLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author ssanchez
 */
@Component
public class CertificationCourseObserver extends SupportEventObserver<CertificationCourseEventEntity> {

    @Autowired
    public CertificationCourseObserver(BlockchainEventLogRepository blockchainEventLogRepository, ICertificationCourseBlockchainRepository blockchainEventRepository) {
        super(blockchainEventLogRepository, blockchainEventRepository);
    }

    /**
     *
     * @param event
     * @return
     */
    @Override
    AbstractEventPayload mapToPayload(CertificationCourseEventEntity event) {
        return CertificationCourseEventPayloadEntity.builder()
                .courseId(event.getCourseId())
                .type(event.getType().name())
                .build();
    }

}
