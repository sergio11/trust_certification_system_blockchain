package com.dreamsoftware.blockchaingateway.persistence.bc.repository.impl;

import com.dreamsoftware.blockchaingateway.contracts.TrustCertificationContract;
import com.dreamsoftware.blockchaingateway.contracts.TrustCertificationContract.CertificateRecord;
import com.dreamsoftware.blockchaingateway.exception.LoadWalletException;
import com.dreamsoftware.blockchaingateway.persistence.bc.core.SupportBlockchainRepository;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.ITrustCertificationBlockchainRepository;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.CertificateIssuedEntity;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.TrustCertificationEventEntity;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.mapper.TrustCertificationEntityMapper;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.mapper.TrustCertificationEventEntityMapper;
import com.dreamsoftware.blockchaingateway.persistence.exception.RepositoryException;
import com.google.common.collect.Lists;
import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
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
public class TrustCertificationBlockchainRepositoryImpl extends SupportBlockchainRepository implements ITrustCertificationBlockchainRepository {

    private final Logger logger = LoggerFactory.getLogger(TrustCertificationBlockchainRepositoryImpl.class);

    private final TrustCertificationEntityMapper trustCertificationEntityMapper;
    private final TrustCertificationEventEntityMapper trustCertificationEventEntityMapper;

    /**
     *
     * @param issuerWallet
     * @param recipientAddress
     * @param certificateCourseId
     * @param qualification
     * @throws RepositoryException
     */
    @Override
    public void issueCertificate(String issuerWallet, String recipientAddress, String certificateCourseId, Long qualification) throws RepositoryException {
        Assert.notNull(issuerWallet, "Issuer Wallet can not be null");
        Assert.notNull(recipientAddress, "recipient Address can not be null");
        Assert.notNull(certificateCourseId, "certificate Course Id can not be null");
        Assert.notNull(qualification, "qualification can not be null");
        try {
            final TrustCertificationContract trustCertificationContract = loadTrustCertificationContract(issuerWallet);
            trustCertificationContract.issueCertificate(recipientAddress, certificateCourseId, BigInteger.valueOf(qualification)).send();
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param ownerWallet
     * @param certificationId
     * @return
     * @throws RepositoryException
     */
    @Override
    public CertificateIssuedEntity renewCertificate(String ownerWallet, String certificationId) throws RepositoryException {
        Assert.notNull(ownerWallet, "Owner Wallet can not be null");
        Assert.notNull(certificationId, "Certification Id can not be null");
        try {
            final TrustCertificationContract trustCertificationContract = loadTrustCertificationContract(ownerWallet);
            trustCertificationContract.renewCertificate(certificationId).send();
            final CertificateRecord certificateDetail = trustCertificationContract.getCertificateDetail(certificationId).send();
            return trustCertificationEntityMapper.certificateRecordToCertificateIssuedEntity(certificateDetail);
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     * @param ownerWallet
     * @param certificationId
     * @return
     * @throws RepositoryException
     */
    @Override
    public CertificateIssuedEntity enable(String ownerWallet, String certificationId) throws RepositoryException {
        Assert.notNull(ownerWallet, "Owner Wallet can not be null");
        Assert.notNull(certificationId, "Certification Id can not be null");
        try {
            final TrustCertificationContract trustCertificationContract = loadTrustCertificationContract(ownerWallet);
            trustCertificationContract.enableCertificate(certificationId).send();
            final CertificateRecord certificateRecord = trustCertificationContract.getCertificateDetail(certificationId).send();
            return trustCertificationEntityMapper.certificateRecordToCertificateIssuedEntity(certificateRecord);
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    @Override
    public CertificateIssuedEntity disable(String ownerWallet, String certificationId) throws RepositoryException {
        Assert.notNull(ownerWallet, "Owner Wallet can not be null");
        Assert.notNull(certificationId, "Certification Id can not be null");
        try {
            final TrustCertificationContract trustCertificationContract = loadTrustCertificationContract(ownerWallet);
            trustCertificationContract.disableCertificate(certificationId).send();
            final CertificateRecord certificateRecord = trustCertificationContract.getCertificateDetail(certificationId).send();
            return trustCertificationEntityMapper.certificateRecordToCertificateIssuedEntity(certificateRecord);
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    @Override
    public CertificateIssuedEntity updateCertificateVisibility(String ownerWallet, String certificationId, Boolean isVisible) throws RepositoryException {
        Assert.notNull(ownerWallet, "Owner Wallet can not be null");
        Assert.notNull(certificationId, "Certification Id can not be null");
        Assert.notNull(isVisible, "isVisible can not be null");
        try {
            final TrustCertificationContract trustCertificationContract = loadTrustCertificationContract(ownerWallet);
            trustCertificationContract.updateCertificateVisibility(certificationId, isVisible).send();
            final CertificateRecord certificateRecord = trustCertificationContract.getCertificateDetail(certificationId).send();
            return trustCertificationEntityMapper.certificateRecordToCertificateIssuedEntity(certificateRecord);
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    @Override
    public Boolean isCertificateValid(String wallet, String certificationId) throws RepositoryException {
        Assert.notNull(wallet, "Wallet can not be null");
        Assert.notNull(certificationId, "Certification Id can not be null");
        try {
            final TrustCertificationContract trustCertificationContract = loadTrustCertificationContract(wallet);
            return trustCertificationContract.isCertificateValid(certificationId).send();
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    @Override
    public CertificateIssuedEntity getCertificateDetail(String wallet, String certificationId) throws RepositoryException {
        Assert.notNull(wallet, "Wallet can not be null");
        Assert.notNull(certificationId, "Certification Id can not be null");
        try {
            final TrustCertificationContract trustCertificationContract = loadTrustCertificationContract(wallet);
            final CertificateRecord certificateRecord = trustCertificationContract.getCertificateDetail(certificationId).send();
            return trustCertificationEntityMapper.certificateRecordToCertificateIssuedEntity(certificateRecord);
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    @Override
    public List<CertificateIssuedEntity> getMyCertificatesAsRecipient(final String ownerWallet) throws RepositoryException {
        Assert.notNull(ownerWallet, "Owner Wallet can not be null");
        try {
            final TrustCertificationContract trustCertificationContract = loadTrustCertificationContract(ownerWallet);
            final List<String> certificateIds = trustCertificationContract.getMyCertificatesAsRecipient().send();
            final List<CertificateIssuedEntity> certificateIssuedEntities = new ArrayList<>();
            for (final String certificateId : certificateIds) {
                final CertificateRecord certificateRecord = trustCertificationContract.getCertificateDetail(certificateId).send();
                certificateIssuedEntities.add(trustCertificationEntityMapper.certificateRecordToCertificateIssuedEntity(certificateRecord));
            }
            return certificateIssuedEntities;
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    @Override
    public List<CertificateIssuedEntity> getMyCertificatesAsIssuer(final String ownerWallet) throws RepositoryException {
        Assert.notNull(ownerWallet, "Owner Wallet can not be null");
        try {
            final TrustCertificationContract trustCertificationContract = loadTrustCertificationContract(ownerWallet);
            final List<String> certificateIds = trustCertificationContract.getMyCertificatesAsIssuer().send();
            final List<CertificateIssuedEntity> certificateIssuedEntities = new ArrayList<>();
            for (final String certificateId : certificateIds) {
                final CertificateRecord certificateRecord = trustCertificationContract.getCertificateDetail(certificateId).send();
                certificateIssuedEntities.add(trustCertificationEntityMapper.certificateRecordToCertificateIssuedEntity(certificateRecord));
            }
            return certificateIssuedEntities;
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    @Override
    public Flowable<TrustCertificationEventEntity> getEvents() throws RepositoryException {
        try {
            final TrustCertificationContract trustCertificationContract = TrustCertificationContract.load(properties.getTrustCertificationContractAddress(),
                    web3j, rootTxManager, properties.gas());
            return Flowable.merge(Lists.newArrayList(
                    trustCertificationContract.onCertificateDeletedEventFlowable(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST).map(trustCertificationEventEntityMapper::mapEventToEntity),
                    trustCertificationContract.onCertificateDisabledEventFlowable(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST).map(trustCertificationEventEntityMapper::mapEventToEntity),
                    trustCertificationContract.onCertificateEnabledEventFlowable(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST).map(trustCertificationEventEntityMapper::mapEventToEntity),
                    trustCertificationContract.onCertificateRenewedEventFlowable(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST).map(trustCertificationEventEntityMapper::mapEventToEntity),
                    trustCertificationContract.onCertificateVisibilityUpdatedEventFlowable(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST).map(trustCertificationEventEntityMapper::mapEventToEntity),
                    trustCertificationContract.onNewCertificateGeneratedEventFlowable(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST).map(trustCertificationEventEntityMapper::mapEventToEntity)
            ));
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     * Private Methods
     */
    /**
     * Load Trust Certification Contract
     *
     * @param walletHash
     * @return
     * @throws LoadWalletException
     */
    private TrustCertificationContract loadTrustCertificationContract(final String walletHash) throws LoadWalletException {
        final Credentials credentials = walletService.loadCredentials(walletHash);
        final FastRawTransactionManager txManager = new FastRawTransactionManager(web3j, credentials, properties.getChainId());
        return TrustCertificationContract.load(properties.getCertificationCourseContractAddress(),
                web3j, txManager, properties.gas());
    }
}
