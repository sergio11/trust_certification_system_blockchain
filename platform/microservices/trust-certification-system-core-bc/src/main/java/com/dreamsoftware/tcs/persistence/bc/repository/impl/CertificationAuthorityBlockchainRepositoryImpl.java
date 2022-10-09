package com.dreamsoftware.tcs.persistence.bc.repository.impl;

import com.dreamsoftware.tcs.contracts.CertificationAuthorityContract;
import com.dreamsoftware.tcs.contracts.CertificationAuthorityContract.CertificationAuthorityRecord;
import com.dreamsoftware.tcs.exception.LoadWalletException;
import com.dreamsoftware.tcs.persistence.bc.repository.ICertificationAuthorityBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.core.SupportBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationAuthorityBcEntity;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationAuthorityEventEntity;
import com.dreamsoftware.tcs.persistence.bc.repository.mapper.CertificationAuthorityEntityMapper;
import com.dreamsoftware.tcs.persistence.bc.repository.mapper.CertificationAuthorityEventEntityMapper;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import io.reactivex.Flowable;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.tx.FastRawTransactionManager;

/**
 *
 * @author ssanchez
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CertificationAuthorityBlockchainRepositoryImpl extends SupportBlockchainRepository implements ICertificationAuthorityBlockchainRepository {

    private final CertificationAuthorityEntityMapper certificationAuthorityEntityMapper;
    private final CertificationAuthorityEventEntityMapper certificationAuthorityEventEntityMapper;

    /**
     *
     * @return @throws RepositoryException
     */
    @Override
    public Iterable<CertificationAuthorityBcEntity> getAll() throws RepositoryException {
        log.debug("get All certification authorities ");
        try {
            final CertificationAuthorityContract caContract = loadCAContractAsRoot();
            final List<CertificationAuthorityRecord> authorities = caContract.getAllCertificationAuthorities().send();
            return certificationAuthorityEntityMapper.caRecordToCaEntity(authorities);
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     * Get Detail
     *
     * @param caId
     * @throws RepositoryException
     */
    @Override
    public CertificationAuthorityBcEntity getDetail(final String caId) throws RepositoryException {
        Assert.notNull(caId, "caId ca not be null");
        try {
            log.debug("Get Certification Authority Detail: " + caId);
            return getCertificationAuthorityDetail(caId);
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     * Add Certification Authority
     *
     * @param id
     * @param walletHash
     * @throws RepositoryException
     */
    @Override
    public void addCertificationAuthority(final String id, final String walletHash) throws RepositoryException {
        Assert.notNull(id, "CA id can not be null");
        Assert.notNull(walletHash, "walletHash ca not be null");
        try {
            log.debug("addCertificationAuthority address: " + properties.getCertificationAuthorityContractAddress());
            final CertificationAuthorityContract caContract = loadCAContract(walletHash);
            caContract.addCertificationAuthority(id).send();
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param id
     * @throws RepositoryException
     */
    @Override
    public void removeCertificationAuthority(final String id) throws RepositoryException {
        Assert.notNull(id, "CA id can not be null");
        try {

        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     * Add Certification Authority Member
     *
     * @param caId
     * @param memberWalletHash
     * @param adminWalletHash
     * @throws RepositoryException
     */
    @Override
    public void addCertificationAuthorityMember(final String caId, final String memberWalletHash, final String adminWalletHash) throws RepositoryException {
        Assert.notNull(caId, "CA id can not be null");
        Assert.notNull(memberWalletHash, "Member Wallet Hash can not be null");
        Assert.notNull(adminWalletHash, "Admin Wallet Hash can not be null");
        try {
            log.debug("addCertificationAuthorityMember caId: " + caId + " , memberWalletHash: " + memberWalletHash);
            final CertificationAuthorityContract caContract = loadCAContract(adminWalletHash);
            caContract.addMember(caId, memberWalletHash).send();
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param caId
     * @param memberWalletHash
     * @param adminWalletHash
     * @throws RepositoryException
     */
    @Override
    public void removeCertificationAuthorityMember(final String caId, final String memberWalletHash, final String adminWalletHash) throws RepositoryException {
        Assert.notNull(caId, "CA id can not be null");
        Assert.notNull(memberWalletHash, "Member Wallet Hash can not be null");
        Assert.notNull(adminWalletHash, "Admin Wallet Hash can not be null");
        try {
            log.debug("removeCertificationAuthorityMember caId: " + caId + " , memberWalletHash: " + memberWalletHash);
            final CertificationAuthorityContract caContract = loadCAContract(adminWalletHash);
            caContract.removeMember(caId, memberWalletHash).send();
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param caId
     * @return
     * @throws RepositoryException
     */
    @Override
    public CertificationAuthorityBcEntity enable(final String caId) throws RepositoryException {
        Assert.notNull(caId, "Ca Id can not be null");
        try {
            final CertificationAuthorityContract caContract = loadCAContractAsRoot();
            caContract.enableCertificationAuthority(caId).send();
            return getCertificationAuthorityDetail(caContract, caId);
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param caId
     * @return
     * @throws RepositoryException
     */
    @Override
    public CertificationAuthorityBcEntity disable(final String caId) throws RepositoryException {
        Assert.notNull(caId, "Ca Id can not be null");
        try {
            final CertificationAuthorityContract caContract = loadCAContractAsRoot();
            caContract.disableCertificationAuthority(caId).send();
            return getCertificationAuthorityDetail(caContract, caId);
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param caId
     * @param memberWalletHash
     * @param adminWalletHash
     * @throws RepositoryException
     */
    @Override
    public void enableMember(final String caId, final String memberWalletHash, final String adminWalletHash) throws RepositoryException {
        Assert.notNull(caId, "Ca Id can not be null");
        Assert.notNull(memberWalletHash, "memberWalletHash can not be null");
        Assert.notNull(adminWalletHash, "adminWalletHash can not be null");
        try {
            log.debug("enableMember caId: " + caId + " , memberWalletHash: " + memberWalletHash);
            final CertificationAuthorityContract caContract = loadCAContract(adminWalletHash);
            caContract.enableMember(caId, memberWalletHash).send();
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param caId
     * @param memberWalletHash
     * @param adminWalletHash
     * @throws RepositoryException
     */
    @Override
    public void disableMember(final String caId, final String memberWalletHash, final String adminWalletHash) throws RepositoryException {
        Assert.notNull(caId, "Ca Id can not be null");
        Assert.notNull(memberWalletHash, "memberWalletHash can not be null");
        Assert.notNull(adminWalletHash, "adminWalletHash can not be null");
        try {
            log.debug("disableMember caId: " + caId + " , memberWalletHash: " + memberWalletHash);
            final CertificationAuthorityContract caContract = loadCAContract(adminWalletHash);
            caContract.disableMember(caId, memberWalletHash).send();
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @return @throws RepositoryException
     */
    @Override
    public Flowable<CertificationAuthorityEventEntity> getEvents() throws RepositoryException {
        try {
            final CertificationAuthorityContract caContract = CertificationAuthorityContract.load(properties.getCertificationAuthorityContractAddress(),
                    web3j, rootTxManager, properties.gas());
            return Flowable.merge(List.of(
                    caContract.onCertificationAuthorityDisabledEventFlowable(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST).map(certificationAuthorityEventEntityMapper::mapEventToEntity),
                    caContract.onCertificationAuthorityEnabledEventFlowable(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST).map(certificationAuthorityEventEntityMapper::mapEventToEntity),
                    caContract.onCertificationAuthorityRemovedEventFlowable(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST).map(certificationAuthorityEventEntityMapper::mapEventToEntity),
                    caContract.onNewCertificationAuthorityCreatedEventFlowable(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST).map(certificationAuthorityEventEntityMapper::mapEventToEntity),
                    caContract.onNewCertificationAuthorityMemberAddedEventFlowable(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST).map(certificationAuthorityEventEntityMapper::mapEventToEntity),
                    caContract.onCertificationAuthorityMemberRemovedEventFlowable(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST).map(certificationAuthorityEventEntityMapper::mapEventToEntity),
                    caContract.onCertificationAuthorityMemberEnabledEventFlowable(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST).map(certificationAuthorityEventEntityMapper::mapEventToEntity),
                    caContract.onCertificationAuthorityMemberDisabledEventFlowable(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST).map(certificationAuthorityEventEntityMapper::mapEventToEntity)
            ));
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     * Private Methods
     */
    /**
     *
     * @param caId
     * @return
     * @throws Exception
     */
    private CertificationAuthorityBcEntity getCertificationAuthorityDetail(final String caId) throws Exception {
        final CertificationAuthorityContract caContract = loadCAContractAsRoot();
        final CertificationAuthorityRecord caRecord = caContract.getCertificateAuthorityDetail(caId).send();
        return certificationAuthorityEntityMapper.caRecordToCaEntity(caRecord);
    }

    /**
     *
     * @param caContract
     * @param caId
     * @return
     * @throws Exception
     */
    private CertificationAuthorityBcEntity getCertificationAuthorityDetail(final CertificationAuthorityContract caContract, final String caId) throws Exception {
        final CertificationAuthorityRecord caRecord = caContract.getCertificateAuthorityDetail(caId).send();
        return certificationAuthorityEntityMapper.caRecordToCaEntity(caRecord);
    }

    /**
     * Load CA Contract
     *
     * @param walletHash
     * @return
     * @throws LoadWalletException
     */
    private CertificationAuthorityContract loadCAContract(final String walletHash) throws LoadWalletException {
        final Credentials credentials = walletService.loadCredentials(walletHash);
        log.debug("loadCAContract, credentials.getAddress -> " + credentials.getAddress());
        return loadCAContract(credentials);
    }

    /**
     *
     * @param credentials
     * @return
     * @throws LoadWalletException
     */
    private CertificationAuthorityContract loadCAContract(final Credentials credentials) throws LoadWalletException {
        final FastRawTransactionManager txManager = new FastRawTransactionManager(web3j, credentials, properties.getChainId());
        return CertificationAuthorityContract.load(properties.getCertificationAuthorityContractAddress(),
                web3j, txManager, properties.gas());
    }

    /**
     *
     * @return
     */
    private CertificationAuthorityContract loadCAContractAsRoot() {
        return CertificationAuthorityContract.load(properties.getCertificationAuthorityContractAddress(),
                web3j, rootTxManager, properties.gas());
    }

}
