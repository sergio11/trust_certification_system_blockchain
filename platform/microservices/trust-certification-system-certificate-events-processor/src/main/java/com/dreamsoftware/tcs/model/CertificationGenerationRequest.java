package com.dreamsoftware.tcs.model;

import com.dreamsoftware.tcs.persistence.nosql.entity.CertificateTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ssanchez
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificationGenerationRequest {

    private String caName;
    private String studentName;
    private String courseName;
    private Long qualification;
    private String executiveDirector;
    private CertificateTypeEnum type;
}
