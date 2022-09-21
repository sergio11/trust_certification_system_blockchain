package com.dreamsoftware.tcs.persistence.bc.repository;

import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationAuthorityEntity;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationAuthorityEventEntity;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;

/**
 *
 * @author ssanchez
 */
public interface ICertificationAuthorityBlockchainRepository extends IBlockchainEventRepository<CertificationAuthorityEventEntity> {

    /**
     * Get Detail
     *
     * @return
     * @throws RepositoryException
     */
    Iterable<CertificationAuthorityEntity> getAll() throws RepositoryException;

    /**
     * Register new Certification Authority
     *
     * @param name
     * @param walletHash
     * @throws com.dreamsoftware.tcs.persistence.exception.RepositoryException
     */
    void register(final String name, final String walletHash) throws RepositoryException;

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
     * @param caWallet
     * @return
     * @throws RepositoryException
     */
    CertificationAuthorityEntity enable(final String caWallet) throws RepositoryException;

    /**
     * Disable Certification Authority
     *
     * @param caWallet
     * @return
     * @throws RepositoryException
     */
    CertificationAuthorityEntity disable(final String caWallet) throws RepositoryException;

    /**
     *
     * @param caWallet
     * @param caEntity
     * @return
     * @throws RepositoryException
     */
    CertificationAuthorityEntity update(final String caWallet, final CertificationAuthorityEntity caEntity) throws RepositoryException;

}
