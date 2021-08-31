/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dreamsoftware.blockchaingateway.services;

/**
 *
 * @author ssanchez
 */
public interface ICertificationAuthorityService {

    /**
     * Add Certification Authority
     *
     * @param name
     * @param secret
     * @param defaultCostOfIssuingCertificate
     */
    void addCertificationAuthority(final String name, final String secret, final Long defaultCostOfIssuingCertificate);

}
