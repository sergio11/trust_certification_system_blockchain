package com.dreamsoftware.tcs.persistence.bc.repository.impl;

import com.dreamsoftware.tcs.contracts.TrustCertificationContract;
import com.dreamsoftware.tcs.contracts.TrustCertificationContract.CertificateRecord;
import com.dreamsoftware.tcs.contracts.TrustCertificationContract.IssueCertificateRequest;
import com.dreamsoftware.tcs.exception.LoadWalletException;
import com.dreamsoftware.tcs.persistence.bc.core.SupportBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.ITrustCertificationBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificateIssuedEntity;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.TrustCertificationEventEntity;
import com.dreamsoftware.tcs.persistence.bc.repository.mapper.TrustCertificationEntityMapper;
import com.dreamsoftware.tcs.persistence.bc.repository.mapper.TrustCertificationEventEntityMapper;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Component
@RequiredArgsConstructor
public class TrustCertificationBlockchainRepositoryImpl extends SupportBlockchainRepository implements ITrustCertificationBlockchainRepository {

    private final TrustCertificationEntityMapper trustCertificationEntityMapper;
    private final TrustCertificationEventEntityMapper trustCertificationEventEntityMapper;

    /**
     *
     * @param id
     * @param issuerWalletHash
     * @param studentWalletHash
     * @param certificateCourseId
     * @param qualification
     * @param fileCid
     * @param fileHash
     * @param imageCid
     * @param imageHash
     * @return
     * @throws RepositoryException
     */
    @Override
    public CertificateIssuedEntity issueCertificate(final UUID id, final String issuerWalletHash, final String studentWalletHash, final String certificateCourseId,
            final Long qualification, final String fileCid, final String fileHash, final String imageCid, final String imageHash) throws RepositoryException {
        Assert.notNull(id, "ID can not be null");
        Assert.notNull(issuerWalletHash, "Issuer Wallet can not be null");
        Assert.notNull(studentWalletHash, "Student Wallet Hash can not be null");
        Assert.notNull(certificateCourseId, "certificate Course Id can not be null");
        Assert.notNull(fileCid, "fileCid can not be null");
        Assert.notNull(fileHash, "fileHash can not be null");
        Assert.notNull(imageCid, "imageCid can not be null");
        Assert.notNull(imageHash, "imageHash can not be null");
        log.debug("issueCertificate CALLED!");
        try {
            final TrustCertificationContract trustCertificationContract = loadTrustCertificationContract(issuerWalletHash);
            final Credentials studentCredentials = walletService.loadCredentials(studentWalletHash);
            final TransactionReceipt transactionReceipt = trustCertificationContract.issueCertificate(new IssueCertificateRequest(id.toString(), studentCredentials.getAddress(),
                    certificateCourseId, BigInteger.valueOf(qualification), fileCid, fileHash, imageCid, imageHash)).send();
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
    public CertificateIssuedEntity renewCertificate(final String ownerWalletHash, final String certificationId) throws RepositoryException {
        Assert.notNull(ownerWalletHash, "Owner Wallet can not be null");
        Assert.notNull(certificationId, "Certification Id can not be null");
        log.debug("renewCertificate CALLED!");
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
    public CertificateIssuedEntity enable(final String ownerWalletHash, String certificationId) throws RepositoryException {
        Assert.notNull(ownerWalletHash, "Owner Wallet can not be null");
        Assert.notNull(certificationId, "Certification Id can not be null");
        log.debug("enableCertificate CALLED!");
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
    public CertificateIssuedEntity disable(final String ownerWalletHash, final String certificationId) throws RepositoryException {
        Assert.notNull(ownerWalletHash, "Owner Wallet can not be null");
        Assert.notNull(certificationId, "Certification Id can not be null");
        log.debug("disableCertificate CALLED!");
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
    public CertificateIssuedEntity updateCertificateVisibility(final String ownerWalletHash, final String certificationId, final Boolean isVisible) throws RepositoryException {
        Assert.notNull(ownerWalletHash, "Owner Wallet can not be null");
        Assert.notNull(certificationId, "Certification Id can not be null");
        Assert.notNull(isVisible, "isVisible can not be null");
        log.debug("updateCertificateVisibility CALLED!");
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
     * @param certificationId
     * @return
     * @throws RepositoryException
     */
    @Override
    public Boolean isCertificateValid(final String certificationId) throws RepositoryException {
        Assert.notNull(certificationId, "Certification Id can not be null");
        log.debug("isCertificateValid CALLED!");
        try {
            final TrustCertificationContract trustCertificationContract = loadTrustCertificationContract();
            return trustCertificationContract.isCertificateValid(certificationId).send();
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    @Override
    public Boolean isCertificateDisabled(String certificationId) throws RepositoryException {
        Assert.notNull(certificationId, "Certification Id can not be null");
        log.debug("isCertificateDisabled " + certificationId + "CALLED!");
        try {
            final TrustCertificationContract trustCertificationContract = loadTrustCertificationContract();
            final CertificateRecord certificateRecord = trustCertificationContract.getCertificateDetail(certificationId).send();
            return !certificateRecord.isEnabled;
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    @Override
    public Boolean isCertificateEnabled(String certificationId) throws RepositoryException {
        Assert.notNull(certificationId, "Certification Id can not be null");
        log.debug("isCertificateEnabled " + certificationId + "CALLED!");
        try {
            final TrustCertificationContract trustCertificationContract = loadTrustCertificationContract();
            final CertificateRecord certificateRecord = trustCertificationContract.getCertificateDetail(certificationId).send();
            return certificateRecord.isEnabled;
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    @Override
    public Boolean isCertificateVisible(String certificationId) throws RepositoryException {
        Assert.notNull(certificationId, "Certification Id can not be null");
        log.debug("isCertificateVisible " + certificationId + "CALLED!");
        try {
            final TrustCertificationContract trustCertificationContract = loadTrustCertificationContract();
            final CertificateRecord certificateRecord = trustCertificationContract.getCertificateDetail(certificationId).send();
            return certificateRecord.isVisible;
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    @Override
    public Boolean isCertificateInvisible(String certificationId) throws RepositoryException {
        Assert.notNull(certificationId, "Certification Id can not be null");
        log.debug("isCertificateInvisible " + certificationId + "CALLED!");
        try {
            final TrustCertificationContract trustCertificationContract = loadTrustCertificationContract();
            final CertificateRecord certificateRecord = trustCertificationContract.getCertificateDetail(certificationId).send();
            return !certificateRecord.isVisible;
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param certificationId
     * @return
     * @throws RepositoryException
     */
    @Override
    public CertificateIssuedEntity getCertificateDetail(final String certificationId) throws RepositoryException {
        Assert.notNull(certificationId, "Certification Id can not be null");
        log.debug("getCertificateDetail CALLED!");
        try {
            final TrustCertificationContract trustCertificationContract = loadTrustCertificationContract();
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
    public List<CertificateIssuedEntity> getMyCertificatesAsRecipient(final String ownerWalletHash) throws RepositoryException {
        Assert.notNull(ownerWalletHash, "Owner Wallet can not be null");
        log.debug("getMyCertificatesAsRecipient CALLED!");
        try {
            final TrustCertificationContract trustCertificationContract = loadTrustCertificationContract(ownerWalletHash);
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

    /**
     *
     * @param ownerWalletHash
     * @return
     * @throws RepositoryException
     */
    @Override
    public List<CertificateIssuedEntity> getMyCertificatesAsIssuer(final String ownerWalletHash) throws RepositoryException {
        Assert.notNull(ownerWalletHash, "Owner Wallet can not be null");
        log.debug("getMyCertificatesAsIssuer CALLED!");
        try {
            final TrustCertificationContract trustCertificationContract = loadTrustCertificationContract(ownerWalletHash);
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

    /**
     *
     * @return @throws RepositoryException
     */
    @Override
    public Flowable<TrustCertificationEventEntity> getEvents() throws RepositoryException {
        try {
            final TrustCertificationContract trustCertificationContract = TrustCertificationContract.load(properties.getTrustCertificationContractAddress(),
                    web3j, rootTxManager, properties.gas());
            return Flowable.merge(List.of(
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
        return TrustCertificationContract.load(properties.getTrustCertificationContractAddress(),
                web3j, txManager, properties.gas());
    }

    /**
     *
     * @return
     */
    private TrustCertificationContract loadTrustCertificationContract() {
        return TrustCertificationContract.load(properties.getTrustCertificationContractAddress(),
                web3j, rootTxManager, properties.gas());
    }

}
