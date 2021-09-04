/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dreamsoftware.blockchaingateway.services;

import com.dreamsoftware.blockchaingateway.web.dto.request.RegisterCertificationAuthorityDTO;

/**
 *
 * @author ssanchez
 */
public interface ICertificationAuthorityService {

    /**
     * Register Certification Authority
     *
     * @param registerCertificationAuthorityDTO
     */
    void register(final RegisterCertificationAuthorityDTO registerCertificationAuthorityDTO);

}
