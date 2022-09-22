package com.dreamsoftware.tcs.service;

import java.io.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Certificate Generator Service
 *
 * @author ssanchez
 */
public interface ICertificateGeneratorService {

    /**
     *
     * @param request
     * @return
     * @throws Exception
     */
    File generate(final CertificationGenerationRequest request) throws Exception;

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
}
