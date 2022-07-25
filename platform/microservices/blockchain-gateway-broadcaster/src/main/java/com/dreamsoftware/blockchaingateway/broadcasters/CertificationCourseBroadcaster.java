package com.dreamsoftware.blockchaingateway.broadcasters;

import com.dreamsoftware.blockchaingateway.persistence.bc.repository.ICertificationCourseBlockchainRepository;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.CertificationCourseEventEntity;
import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.AbstractEventPayload;
import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.CertificationCourseEventPayloadEntity;
import com.dreamsoftware.blockchaingateway.persistence.nosql.repository.BlockchainEventLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author ssanchez
 */
@Component
public class CertificationCourseBroadcaster extends SupportEventBroadcaster<CertificationCourseEventEntity> {

    @Autowired
    public CertificationCourseBroadcaster(BlockchainEventLogRepository blockchainEventLogRepository, ICertificationCourseBlockchainRepository blockchainEventRepository) {
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
