package com.dreamsoftware.tcs.persistence.bc.repository.impl;

import com.dreamsoftware.tcs.contracts.CertificationAuthorityContract;
import com.dreamsoftware.tcs.contracts.CertificationAuthorityContract.CertificationAuthorityRecord;
import com.dreamsoftware.tcs.exception.LoadWalletException;
import com.dreamsoftware.tcs.persistence.bc.repository.ICertificationAuthorityBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.core.SupportBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationAuthorityEntity;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationAuthorityEventEntity;
import com.dreamsoftware.tcs.persistence.bc.repository.mapper.CertificationAuthorityEntityMapper;
import com.dreamsoftware.tcs.persistence.bc.repository.mapper.CertificationAuthorityEventEntityMapper;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.google.common.collect.Lists;
import io.reactivex.Flowable;
import java.math.BigInteger;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.tx.FastRawTransactionManager;

/**
 *
 * @author ssanchez
 */
@Component
@RequiredArgsConstructor
public class CertificationAuthorityBlockchainRepositoryImpl extends SupportBlockchainRepository implements ICertificationAuthorityBlockchainRepository {

    private final Logger logger = LoggerFactory.getLogger(CertificationAuthorityBlockchainRepositoryImpl.class);

    private final CertificationAuthorityEntityMapper certificationAuthorityEntityMapper;
    private final CertificationAuthorityEventEntityMapper certificationAuthorityEventEntityMapper;

    /**
     * Register Certification Authority
     *
     * @param name
     * @param walletHash
     * @throws RepositoryException
     */
    @Override
    public void register(String name, String walletHash) throws RepositoryException {
        try {
            logger.debug("registerCertificationAuthority address: " + properties.getCertificationAuthorityContractAddress());
            final CertificationAuthorityContract caContract = loadCAContract(walletHash);
            caContract.addCertificationAuthority(name).send();
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
    public CertificationAuthorityEntity getDetail(String walletHash) throws RepositoryException {
        Assert.notNull(walletHash, "Wallet ca not be null");
        try {
            final CertificationAuthorityContract caContract = loadCAContract(walletHash);
            final CertificationAuthorityRecord caRecord = caContract.getCertificateAuthorityDetail().send();
            return certificationAuthorityEntityMapper.caRecordToCaEntity(caRecord);
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param rootWallet
     * @param caWallet
     * @return
     * @throws RepositoryException
     */
    @Override
    public CertificationAuthorityEntity enable(final String rootWallet, final String caWallet) throws RepositoryException {
        Assert.notNull(rootWallet, "Root Wallet can not be null");
        Assert.notNull(caWallet, "Ca Wallet can not be null");
        try {
            final CertificationAuthorityContract caContract = loadCAContract(rootWallet);
            caContract.enableCertificationAuthority(caWallet).send();
            final CertificationAuthorityRecord caRecord = caContract.getCertificateAuthorityDetail(caWallet).send();
            return certificationAuthorityEntityMapper.caRecordToCaEntity(caRecord);
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param rootWallet
     * @param caWallet
     * @return
     * @throws RepositoryException
     */
    @Override
    public CertificationAuthorityEntity disable(final String rootWallet, final String caWallet) throws RepositoryException {
        Assert.notNull(rootWallet, "Root Wallet can not be null");
        Assert.notNull(caWallet, "Ca Wallet can not be null");
        try {
            final CertificationAuthorityContract caContract = loadCAContract(rootWallet);
            caContract.disableCertificationAuthority(caWallet).send();
            final CertificationAuthorityRecord caRecord = caContract.getCertificateAuthorityDetail(caWallet).send();
            return certificationAuthorityEntityMapper.caRecordToCaEntity(caRecord);
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    @Override
    public Flowable<CertificationAuthorityEventEntity> getEvents() throws RepositoryException {
        try {
            final CertificationAuthorityContract caContract = CertificationAuthorityContract.load(properties.getCertificationAuthorityContractAddress(),
                    web3j, rootTxManager, properties.gas());
            return Flowable.merge(Lists.newArrayList(
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
     * Load CA Contract
     *
     * @param walletHash
     * @return
     * @throws LoadWalletException
     */
    private CertificationAuthorityContract loadCAContract(final String walletHash) throws LoadWalletException {
        final Credentials credentials = walletService.loadCredentials(walletHash);
        final FastRawTransactionManager txManager = new FastRawTransactionManager(web3j, credentials, properties.getChainId());
        return CertificationAuthorityContract.load(properties.getCertificationAuthorityContractAddress(),
                web3j, txManager, properties.gas());
    }

    @Override
    public CertificationAuthorityEntity update(String caWallet, CertificationAuthorityEntity caEntity) throws RepositoryException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
