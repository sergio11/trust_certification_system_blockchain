package com.dreamsoftware.tcs.stream.events.course;

import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationCourseModelEntity;
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
    private CertificationCourseModelEntity certificationCourse;

    /**
     * CA Wallet Hash
     */
    private String caWalletHash;

}
