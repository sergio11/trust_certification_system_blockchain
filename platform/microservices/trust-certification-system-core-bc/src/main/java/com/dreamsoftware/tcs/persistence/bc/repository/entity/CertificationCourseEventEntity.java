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
public class CertificationCourseEventEntity extends AbstractBlockchainEventEntity {

    /**
     * Event Type
     */
    private CertificationCourseEventTypeEnum type;

    /**
     * Course Id
     */
    private String courseId;
}
