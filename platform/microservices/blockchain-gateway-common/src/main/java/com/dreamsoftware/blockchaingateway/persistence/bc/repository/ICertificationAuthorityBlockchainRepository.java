package com.dreamsoftware.blockchaingateway.persistence.bc.repository;

import com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.CertificationAuthorityEntity;
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

    /**
     * Get Detail
     *
     * @param walletHash
     * @return
     * @throws RepositoryException
     */
    CertificationAuthorityEntity getDetail(final String walletHash) throws RepositoryException;

    /**
     * Enable Certification Authority
     *
     * @param rootWallet
     * @param caWallet
     * @return
     * @throws RepositoryException
     */
    CertificationAuthorityEntity enable(final String rootWallet, final String caWallet) throws RepositoryException;

    /**
     * Disable Certification Authority
     *
     * @param rootWallet
     * @param caWallet
     * @return
     * @throws RepositoryException
     */
    CertificationAuthorityEntity disable(final String rootWallet, final String caWallet) throws RepositoryException;
}
