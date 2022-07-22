package com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Authority Entity
 *
 * @author ssanchez
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class TrustCertificationEventEntity extends AbstractBlockchainEventEntity {

    /**
     * Event Type
     */
    private TrustCertificationEventTypeEnum type;

    /**
     * Certificate Id
     */
    private String certificateId;
}
