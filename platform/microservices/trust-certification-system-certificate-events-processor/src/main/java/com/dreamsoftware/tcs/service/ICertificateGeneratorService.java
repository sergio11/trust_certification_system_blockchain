package com.dreamsoftware.tcs.service;

import com.dreamsoftware.tcs.model.CertificateGenerated;
import com.dreamsoftware.tcs.model.CertificationGenerationRequest;

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
    CertificateGenerated generate(final CertificationGenerationRequest request) throws Exception;
}
