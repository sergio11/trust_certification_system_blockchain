package com.dreamsoftware.tcs.persistence.bc.repository.impl;

import com.dreamsoftware.tcs.contracts.TrustCertificationContract;
import com.dreamsoftware.tcs.contracts.TrustCertificationContract.CertificateRecord;
import com.dreamsoftware.tcs.exception.LoadWalletException;
import com.dreamsoftware.tcs.persistence.bc.core.SupportBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.ITrustCertificationBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificateIssuedBcEntity;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.TrustCertificationEventEntity;
import com.dreamsoftware.tcs.persistence.bc.repository.mapper.TrustCertificationEntityMapper;
import com.dreamsoftware.tcs.persistence.bc.repository.mapper.TrustCertificationEventEntityMapper;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
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
import org.web3j.protocol.core.methods.response.TransactionReceipt;
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
     * @param issuerWalletHash
     * @param studentWalletHash
     * @param certificateCourseId
     * @param qualification
     * @throws RepositoryException
     */
    @Override
    public CertificateIssuedBcEntity issueCertificate(String issuerWalletHash, String studentWalletHash, String certificateCourseId, Long qualification, final String cid) throws RepositoryException {
        Assert.notNull(issuerWalletHash, "Issuer Wallet can not be null");
        Assert.notNull(studentWalletHash, "Student Wallet Hash can not be null");
        Assert.notNull(certificateCourseId, "certificate Course Id can not be null");
        Assert.notNull(qualification, "qualification can not be null");
        logger.debug("issueCertificate CALLED!");
        try {
            final TrustCertificationContract trustCertificationContract = loadTrustCertificationContract(issuerWalletHash);
            final Credentials studentCredentials = walletService.loadCredentials(studentWalletHash);
            final TransactionReceipt transactionReceipt = trustCertificationContract.issueCertificate(studentCredentials.getAddress(), certificateCourseId, BigInteger.valueOf(qualification), cid).send();
            final List<TrustCertificationContract.OnNewCertificateGeneratedEventResponse> events = trustCertificationContract.getOnNewCertificateGeneratedEvents(transactionReceipt);
            final String certificationId = events.get(0)._id;
            final CertificateRecord certificateRecord = trustCertificationContract.getCertificateDetail(certificationId).send();
            return trustCertificationEntityMapper.certificateRecordToCertificateIssuedEntity(certificateRecord);
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param ownerWalletHash
     * @param certificationId
     * @return
     * @throws RepositoryException
     */
    @Override
    public CertificateIssuedBcEntity renewCertificate(String ownerWalletHash, String certificationId) throws RepositoryException {
        Assert.notNull(ownerWalletHash, "Owner Wallet can not be null");
        Assert.notNull(certificationId, "Certification Id can not be null");
        logger.debug("renewCertificate CALLED!");
        try {
            final TrustCertificationContract trustCertificationContract = loadTrustCertificationContract(ownerWalletHash);
            trustCertificationContract.renewCertificate(certificationId).send();
            final CertificateRecord certificateDetail = trustCertificationContract.getCertificateDetail(certificationId).send();
            return trustCertificationEntityMapper.certificateRecordToCertificateIssuedEntity(certificateDetail);
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     * @param ownerWalletHash
     * @param certificationId
     * @return
     * @throws RepositoryException
     */
    @Override
    public CertificateIssuedBcEntity enable(String ownerWalletHash, String certificationId) throws RepositoryException {
        Assert.notNull(ownerWalletHash, "Owner Wallet can not be null");
        Assert.notNull(certificationId, "Certification Id can not be null");
        logger.debug("enableCertificate CALLED!");
        try {
            final TrustCertificationContract trustCertificationContract = loadTrustCertificationContract(ownerWalletHash);
            trustCertificationContract.enableCertificate(certificationId).send();
            final CertificateRecord certificateRecord = trustCertificationContract.getCertificateDetail(certificationId).send();
            return trustCertificationEntityMapper.certificateRecordToCertificateIssuedEntity(certificateRecord);
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param ownerWalletHash
     * @param certificationId
     * @return
     * @throws RepositoryException
     */
    @Override
    public CertificateIssuedBcEntity disable(String ownerWalletHash, String certificationId) throws RepositoryException {
        Assert.notNull(ownerWalletHash, "Owner Wallet can not be null");
        Assert.notNull(certificationId, "Certification Id can not be null");
        logger.debug("disableCertificate CALLED!");
        try {
            final TrustCertificationContract trustCertificationContract = loadTrustCertificationContract(ownerWalletHash);
            trustCertificationContract.disableCertificate(certificationId).send();
            final CertificateRecord certificateRecord = trustCertificationContract.getCertificateDetail(certificationId).send();
            return trustCertificationEntityMapper.certificateRecordToCertificateIssuedEntity(certificateRecord);
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param ownerWalletHash
     * @param certificationId
     * @param isVisible
     * @return
     * @throws RepositoryException
     */
    @Override
    public CertificateIssuedBcEntity updateCertificateVisibility(String ownerWalletHash, String certificationId, Boolean isVisible) throws RepositoryException {
        Assert.notNull(ownerWalletHash, "Owner Wallet can not be null");
        Assert.notNull(certificationId, "Certification Id can not be null");
        Assert.notNull(isVisible, "isVisible can not be null");
        logger.debug("updateCertificateVisibility CALLED!");
        try {
            final TrustCertificationContract trustCertificationContract = loadTrustCertificationContract(ownerWalletHash);
            trustCertificationContract.updateCertificateVisibility(certificationId, isVisible).send();
            final CertificateRecord certificateRecord = trustCertificationContract.getCertificateDetail(certificationId).send();
            return trustCertificationEntityMapper.certificateRecordToCertificateIssuedEntity(certificateRecord);
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param walletHash
     * @param certificationId
     * @return
     * @throws RepositoryException
     */
    @Override
    public Boolean isCertificateValid(String walletHash, String certificationId) throws RepositoryException {
        Assert.notNull(walletHash, "Wallet can not be null");
        Assert.notNull(certificationId, "Certification Id can not be null");
        logger.debug("isCertificateValid CALLED!");
        try {
            final TrustCertificationContract trustCertificationContract = loadTrustCertificationContract(walletHash);
            return trustCertificationContract.isCertificateValid(certificationId).send();
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param walletHash
     * @param certificationId
     * @return
     * @throws RepositoryException
     */
    @Override
    public CertificateIssuedBcEntity getCertificateDetail(String walletHash, String certificationId) throws RepositoryException {
        Assert.notNull(walletHash, "Wallet can not be null");
        Assert.notNull(certificationId, "Certification Id can not be null");
        logger.debug("getCertificateDetail CALLED!");
        try {
            final TrustCertificationContract trustCertificationContract = loadTrustCertificationContract(walletHash);
            final CertificateRecord certificateRecord = trustCertificationContract.getCertificateDetail(certificationId).send();
            return trustCertificationEntityMapper.certificateRecordToCertificateIssuedEntity(certificateRecord);
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param ownerWalletHash
     * @return
     * @throws RepositoryException
     */
    @Override
    public List<CertificateIssuedBcEntity> getMyCertificatesAsRecipient(final String ownerWalletHash) throws RepositoryException {
        Assert.notNull(ownerWalletHash, "Owner Wallet can not be null");
        logger.debug("getMyCertificatesAsRecipient CALLED!");
        try {
            final TrustCertificationContract trustCertificationContract = loadTrustCertificationContract(ownerWalletHash);
            final List<String> certificateIds = trustCertificationContract.getMyCertificatesAsRecipient().send();
            final List<CertificateIssuedBcEntity> certificateIssuedEntities = new ArrayList<>();
            for (final String certificateId : certificateIds) {
                final CertificateRecord certificateRecord = trustCertificationContract.getCertificateDetail(certificateId).send();
                certificateIssuedEntities.add(trustCertificationEntityMapper.certificateRecordToCertificateIssuedEntity(certificateRecord));
            }
            return certificateIssuedEntities;
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param ownerWalletHash
     * @return
     * @throws RepositoryException
     */
    @Override
    public List<CertificateIssuedBcEntity> getMyCertificatesAsIssuer(final String ownerWalletHash) throws RepositoryException {
        Assert.notNull(ownerWalletHash, "Owner Wallet can not be null");
        logger.debug("getMyCertificatesAsIssuer CALLED!");
        try {
            final TrustCertificationContract trustCertificationContract = loadTrustCertificationContract(ownerWalletHash);
            final List<String> certificateIds = trustCertificationContract.getMyCertificatesAsIssuer().send();
            final List<CertificateIssuedBcEntity> certificateIssuedEntities = new ArrayList<>();
            for (final String certificateId : certificateIds) {
                final CertificateRecord certificateRecord = trustCertificationContract.getCertificateDetail(certificateId).send();
                certificateIssuedEntities.add(trustCertificationEntityMapper.certificateRecordToCertificateIssuedEntity(certificateRecord));
            }
            return certificateIssuedEntities;
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @return @throws RepositoryException
     */
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
