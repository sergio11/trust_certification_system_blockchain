package com.dreamsoftware.blockchaingateway.persistence.bc.repository;

import com.dreamsoftware.blockchaingateway.persistence.exception.RepositoryException;

/**
 *
 * @author ssanchez
 */
public interface ICertificationAuthorityBlockchainRepository {

    /**
     * Register new Certification Authority
     *
     * @param name
     * @param defaultCostOfIssuingCertificate
     * @param walletHash
     * @throws
     * com.dreamsoftware.blockchaingateway.persistence.exception.RepositoryException
     */
    void register(final String name, final Long defaultCostOfIssuingCertificate, final String walletHash) throws RepositoryException;

}
