package com.dreamsoftware.blockchaingateway.services;

import com.dreamsoftware.blockchaingateway.web.dto.request.RegisterCertificationAuthorityDTO;
import com.dreamsoftware.blockchaingateway.web.dto.response.CertificationAuthorityDetailDTO;

/**
 *
 * @author ssanchez
 */
public interface ICertificationAuthorityService {

    /**
     * Get Detail
     *
     * @param id
     * @return
     */
    CertificationAuthorityDetailDTO getDetail(final String id);

    /**
     * Register Certification Authority
     *
     * @param registerCertificationAuthorityDTO
     */
    void register(final RegisterCertificationAuthorityDTO registerCertificationAuthorityDTO);

}
