package com.dreamsoftware.tcs.persistence.bc.repository.impl;

import com.dreamsoftware.tcs.contracts.CertificationAuthorityContract;
import com.dreamsoftware.tcs.contracts.CertificationAuthorityContract.CertificationAuthorityRecord;
import com.dreamsoftware.tcs.contracts.CertificationCourseContract;
import com.dreamsoftware.tcs.exception.LoadWalletException;
import com.dreamsoftware.tcs.persistence.bc.repository.ICertificationAuthorityBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.core.SupportBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationAuthorityEntity;
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
    public Iterable<CertificationAuthorityEntity> getAll() throws RepositoryException {
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
     * Register Certification Authority
     *
     * @param name
     * @param location
     * @param executiveDirector
     * @param walletHash
     * @throws RepositoryException
     */
    @Override
    public void register(final String name, final String location, final String executiveDirector, final String walletHash) throws RepositoryException {
        Assert.notNull(name, "name ca not be null");
        Assert.notNull(location, "location ca not be null");
        Assert.notNull(executiveDirector, "executiveDirector ca not be null");
        Assert.notNull(walletHash, "walletHash ca not be null");
        try {
            log.debug("registerCertificationAuthority address: " + properties.getCertificationAuthorityContractAddress());
            final CertificationAuthorityContract caContract = loadCAContract(walletHash);
            caContract.addCertificationAuthority(name, location, executiveDirector).send();
            caContract.getCertificateAuthorityDetail().send();
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     * Get Detail
     *
     * @param walletHash
     * @throws RepositoryException
     */
    @Override
    public CertificationAuthorityEntity getDetail(final String walletHash) throws RepositoryException {
        Assert.notNull(walletHash, "Wallet ca not be null");
        try {
            log.debug("Get Certification Authority Detail with walletHash: " + walletHash);
            final CertificationAuthorityContract caContract = loadCAContract(walletHash);
            return getCertificationAuthorityDetail(caContract);
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param caWallet
     * @return
     * @throws RepositoryException
     */
    @Override
    public CertificationAuthorityEntity enable(final String caWallet) throws RepositoryException {
        Assert.notNull(caWallet, "Ca Wallet can not be null");
        try {
            final CertificationAuthorityContract caContract = loadCAContractAsRoot();
            final Credentials caCredentials = walletService.loadCredentials(caWallet);
            caContract.enableCertificationAuthority(caCredentials.getAddress()).send();
            return getCertificationAuthorityDetail(caContract, caCredentials.getAddress());
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param caWallet
     * @return
     * @throws RepositoryException
     */
    @Override
    public CertificationAuthorityEntity disable(final String caWallet) throws RepositoryException {
        Assert.notNull(caWallet, "Ca Wallet can not be null");
        try {
            final CertificationAuthorityContract caContract = loadCAContractAsRoot();
            final Credentials caCredentials = walletService.loadCredentials(caWallet);
            caContract.disableCertificationAuthority(caCredentials.getAddress()).send();
            return getCertificationAuthorityDetail(caContract, caCredentials.getAddress());
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param caWallet
     * @param caEntity
     * @return
     * @throws RepositoryException
     */
    @Override
    public CertificationAuthorityEntity update(final String caWallet, final CertificationAuthorityEntity caEntity) throws RepositoryException {
        Assert.notNull(caWallet, "Ca Wallet can not be null");
        Assert.notNull(caEntity, "CA Entity can not be null");
        try {
            final Credentials caCredentials = walletService.loadCredentials(caWallet);
            final CertificationAuthorityContract caContract = loadCAContract(caCredentials);
            caContract.updateCertificationAuthority(caEntity.getName(), caEntity.getLocation(), caEntity.getExecutiveDirector(),
                    caEntity.getDefaultCostOfIssuingCertificate()
            ).send();
            return getCertificationAuthorityDetail(caContract, caCredentials.getAddress());
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
                    caContract.onNewCertificationAuthorityCreatedEventFlowable(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST).map(certificationAuthorityEventEntityMapper::mapEventToEntity)
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
     * @param certificationAuthorityContract
     * @return
     * @throws Exception
     */
    private CertificationAuthorityEntity getCertificationAuthorityDetail(final CertificationAuthorityContract certificationAuthorityContract) throws Exception {
        final CertificationAuthorityRecord caRecord = certificationAuthorityContract.getCertificateAuthorityDetail().send();
        return certificationAuthorityEntityMapper.caRecordToCaEntity(caRecord);
    }

    /**
     *
     * @param certificationAuthorityContract
     * @param caAddress
     * @return
     * @throws Exception
     */
    private CertificationAuthorityEntity getCertificationAuthorityDetail(final CertificationAuthorityContract certificationAuthorityContract, final String caAddress) throws Exception {
        final CertificationAuthorityRecord caRecord = certificationAuthorityContract.getCertificateAuthorityDetail(caAddress).send();
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
