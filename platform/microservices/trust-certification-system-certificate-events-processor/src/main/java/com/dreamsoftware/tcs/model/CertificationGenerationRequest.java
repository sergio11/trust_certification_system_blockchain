package com.dreamsoftware.tcs.model;

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
    private String caWalletHash;
    private String studentName;
    private String studentWalletHash;
    private String courseName;
    private String courseId;
    private Long qualification;
}
