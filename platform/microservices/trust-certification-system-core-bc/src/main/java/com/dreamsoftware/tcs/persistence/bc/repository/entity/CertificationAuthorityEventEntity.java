package com.dreamsoftware.tcs.persistence.bc.repository.entity;

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
public class CertificationAuthorityEventEntity extends AbstractBlockchainEventEntity {

    /**
     * Event Type
     */
    private CertificationAuthorityEventTypeEnum type;

    /**
     * CA Name
     */
    private String name;
}
