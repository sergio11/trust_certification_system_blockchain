package com.dreamsoftware.blockchaingateway.services;

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
     * @throws java.lang.Throwable
     */
    CertificationAuthorityDetailDTO getDetail(final String id) throws Throwable;

    /**
     * Enable Certification Authority
     *
     * @param id
     * @return
     * @throws Throwable
     */
    CertificationAuthorityDetailDTO enable(final String id) throws Throwable;

    /**
     * Disable Certification Authority
     *
     * @param id
     * @return
     * @throws Throwable
     */
    CertificationAuthorityDetailDTO disable(final String id) throws Throwable;

}
