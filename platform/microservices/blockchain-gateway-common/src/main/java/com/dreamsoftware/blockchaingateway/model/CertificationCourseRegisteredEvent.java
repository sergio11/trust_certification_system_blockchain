package com.dreamsoftware.blockchaingateway.model;

import com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.CertificationCourseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ssanchez
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificationCourseRegisteredEvent {

    /**
     * Certification Course
     */
    private CertificationCourseEntity certificationCourse;

    /**
     * Wallet Hash
     */
    private String walletHash;

}
