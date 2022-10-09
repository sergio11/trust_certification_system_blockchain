package com.dreamsoftware.tcs.persistence.bc.repository;

import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationAuthorityBcEntity;
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
    Iterable<CertificationAuthorityBcEntity> getAll() throws RepositoryException;

    /**
     * Get Detail
     *
     * @param caId
     * @return
     * @throws RepositoryException
     */
    CertificationAuthorityBcEntity getDetail(final String caId) throws RepositoryException;

    /**
     * Register new Certification Authority
     *
     * @param id
     * @param walletHash
     * @throws com.dreamsoftware.tcs.persistence.exception.RepositoryException
     */
    void addCertificationAuthority(final String id, final String walletHash) throws RepositoryException;

    /**
     * Remove Certification Authority
     *
     * @param id
     * @throws RepositoryException
     */
    void removeCertificationAuthority(final String id) throws RepositoryException;

    /**
     * Add Certification Authority Member
     *
     * @param caId
     * @param memberWalletHash
     * @param adminWalletHash
     * @throws RepositoryException
     */
    void addCertificationAuthorityMember(final String caId, final String memberWalletHash, final String adminWalletHash) throws RepositoryException;

    /**
     * Remove Certification Authority Member
     *
     * @param caId
     * @param memberWalletHash
     * @param adminWalletHash
     * @throws RepositoryException
     */
    void removeCertificationAuthorityMember(final String caId, final String memberWalletHash, final String adminWalletHash) throws RepositoryException;

    /**
     * Enable Certification Authority
     *
     * @param caId
     * @return
     * @throws RepositoryException
     */
    CertificationAuthorityBcEntity enable(final String caId) throws RepositoryException;

    /**
     * Disable Certification Authority
     *
     * @param caId
     * @return
     * @throws RepositoryException
     */
    CertificationAuthorityBcEntity disable(final String caId) throws RepositoryException;

    /**
     * Enable Certification Authority Member
     *
     * @param caId
     * @param memberWalletHash
     * @param adminWalletHash
     * @throws RepositoryException
     */
    void enableMember(final String caId, final String memberWalletHash, final String adminWalletHash) throws RepositoryException;

    /**
     * Disable Certification Authority Member
     *
     * @param caId
     * @param memberWalletHash
     * @param adminWalletHash
     * @throws RepositoryException
     */
    void disableMember(final String caId, final String memberWalletHash, final String adminWalletHash) throws RepositoryException;

}
