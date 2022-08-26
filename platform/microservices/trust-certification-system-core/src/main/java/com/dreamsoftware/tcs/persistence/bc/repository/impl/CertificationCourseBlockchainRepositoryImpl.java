package com.dreamsoftware.tcs.persistence.bc.repository.impl;

import com.dreamsoftware.tcs.contracts.CertificationCourseContract;
import com.dreamsoftware.tcs.contracts.CertificationCourseContract.CertificationCourseRecord;
import com.dreamsoftware.tcs.exception.LoadWalletException;
import com.dreamsoftware.tcs.persistence.bc.core.SupportBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.ICertificationCourseBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationCourseModelEntity;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationCourseEventEntity;
import com.dreamsoftware.tcs.persistence.bc.repository.mapper.CertificationCourseEntityMapper;
import com.dreamsoftware.tcs.persistence.bc.repository.mapper.CertificationCourseEventEntityMapper;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.google.common.collect.Lists;
import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.FastRawTransactionManager;

/**
 * Certification Course Blockchain Repository
 *
 * @author ssanchez
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CertificationCourseBlockchainRepositoryImpl extends SupportBlockchainRepository implements ICertificationCourseBlockchainRepository {

    private final CertificationCourseEntityMapper certificationCourseEntityMapper;
    private final CertificationCourseEventEntityMapper certificationCourseEventEntityMapper;

    /**
     *
     * @param walletHash
     * @param name
     * @param costOfIssuingCertificate
     * @param durationInHours
     * @param expirationInDays
     * @param canBeRenewed
     * @param costOfRenewingCertificate
     * @throws RepositoryException
     */
    @Override
    public CertificationCourseModelEntity register(String walletHash, String name, Long costOfIssuingCertificate, Long durationInHours, Long expirationInDays, Boolean canBeRenewed, Long costOfRenewingCertificate) throws RepositoryException {
        try {
            log.debug("register new certification course CALLED!");
            final CertificationCourseContract certificationCourseContract = loadCertificationCourseContract(walletHash);
            final TransactionReceipt transactionReceipt = certificationCourseContract.addCertificationCourse(name, BigInteger.valueOf(costOfIssuingCertificate), BigInteger.valueOf(durationInHours),
                    BigInteger.valueOf(expirationInDays), canBeRenewed, BigInteger.valueOf(costOfRenewingCertificate)).send();
            final List<CertificationCourseContract.OnNewCertificationCourseCreatedEventResponse> events = certificationCourseContract.getOnNewCertificationCourseCreatedEvents(transactionReceipt);
            final String courseId = events.get(0)._id;
            return getCertificationCourseDetail(certificationCourseContract, courseId);
        } catch (final Exception ex) {
            log.debug("ERROR: register new certification course ex -> " + ex.getMessage());
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param caWallet
     * @param courseId
     * @return
     * @throws RepositoryException
     */
    @Override
    public CertificationCourseModelEntity enable(final String caWallet, final String courseId) throws RepositoryException {
        Assert.notNull(caWallet, "Ca Wallet can not be null");
        Assert.notNull(courseId, "Course Id can not be null");
        try {
            final CertificationCourseContract certificationCourseContract = loadCertificationCourseContract(caWallet);
            certificationCourseContract.enableCertificationCourse(courseId).send();
            return getCertificationCourseDetail(certificationCourseContract, courseId);
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param caWallet
     * @param courseId
     * @return
     * @throws RepositoryException
     */
    @Override
    public CertificationCourseModelEntity disable(final String caWallet, final String courseId) throws RepositoryException {
        Assert.notNull(caWallet, "Ca Wallet can not be null");
        Assert.notNull(courseId, "Course Id can not be null");
        try {
            final CertificationCourseContract certificationCourseContract = loadCertificationCourseContract(caWallet);
            certificationCourseContract.disableCertificationCourse(courseId).send();
            return getCertificationCourseDetail(certificationCourseContract, courseId);
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     * Get Certification Course detail
     *
     * @param caWallet
     * @param courseId
     * @return
     * @throws RepositoryException
     */
    @Override
    public CertificationCourseModelEntity get(String caWallet, String courseId) throws RepositoryException {
        Assert.notNull(caWallet, "Ca Wallet can not be null");
        Assert.notNull(courseId, "Course Id can not be null");
        try {
            final CertificationCourseContract certificationCourseContract = loadCertificationCourseContract(caWallet);
            return getCertificationCourseDetail(certificationCourseContract, courseId);
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param courseId
     * @return
     * @throws RepositoryException
     */
    @Override
    public CertificationCourseModelEntity get(String courseId) throws RepositoryException {
        Assert.notNull(courseId, "Course Id can not be null");
        try {
            final CertificationCourseContract certificationCourseContract = loadCertificationCourseContract();
            return getCertificationCourseDetail(certificationCourseContract, courseId);
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     * Can be issued
     *
     * @param caWallet
     * @param courseId
     * @return
     * @throws RepositoryException
     */
    @Override
    public Boolean canBeIssued(String caWallet, String courseId) throws RepositoryException {
        Assert.notNull(caWallet, "Ca Wallet can not be null");
        Assert.notNull(courseId, "Course Id can not be null");
        try {
            final CertificationCourseContract certificationCourseContract = loadCertificationCourseContract(caWallet);
            return certificationCourseContract.canBeIssued(courseId).send();
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     * Can Be renewed
     *
     * @param caWallet
     * @param courseId
     * @return
     * @throws RepositoryException
     */
    @Override
    public Boolean canBeRenewed(String caWallet, String courseId) throws RepositoryException {
        Assert.notNull(caWallet, "Ca Wallet can not be null");
        Assert.notNull(courseId, "Course Id can not be null");
        try {
            final CertificationCourseContract certificationCourseContract = loadCertificationCourseContract(caWallet);
            return certificationCourseContract.canBeRenewed(courseId).send();
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param caWallet
     * @param courseId
     * @return
     * @throws RepositoryException
     */
    @Override
    public CertificationCourseModelEntity remove(String caWallet, String courseId) throws RepositoryException {
        Assert.notNull(caWallet, "Ca Wallet can not be null");
        Assert.notNull(courseId, "Course Id can not be null");
        try {
            final CertificationCourseContract certificationCourseContract = loadCertificationCourseContract(caWallet);
            final CertificationCourseRecord certificationCourseRecord = certificationCourseContract.getCertificateCourseDetail(courseId).send();
            certificationCourseContract.removeCertificationCourse(courseId).send();
            return certificationCourseEntityMapper.courseRecordToCertificationCourseEntity(certificationCourseRecord);
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     * Get Events
     *
     * @return
     * @throws RepositoryException
     */
    @Override
    public Flowable<CertificationCourseEventEntity> getEvents() throws RepositoryException {
        try {
            final CertificationCourseContract caContract = loadCertificationCourseContract();
            return Flowable.merge(Lists.newArrayList(
                    caContract.onCertificationCourseDisabledEventFlowable(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST).map(certificationCourseEventEntityMapper::mapEventToEntity),
                    caContract.onCertificationCourseEnabledEventFlowable(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST).map(certificationCourseEventEntityMapper::mapEventToEntity),
                    caContract.onCertificationCourseRemovedEventFlowable(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST).map(certificationCourseEventEntityMapper::mapEventToEntity),
                    caContract.onNewCertificationCourseCreatedEventFlowable(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST).map(certificationCourseEventEntityMapper::mapEventToEntity)
            ));
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     *
     * @param caWallet
     * @param model
     * @return
     * @throws RepositoryException
     */
    @Override
    public CertificationCourseModelEntity update(final String caWallet, final CertificationCourseModelEntity model) throws RepositoryException {
        Assert.notNull(caWallet, "Ca Wallet can not be null");
        Assert.notNull(model, "Course Id can not be null");
        try {
            final CertificationCourseContract certificationCourseContract = loadCertificationCourseContract(caWallet);
            certificationCourseContract.updateCertificationCourse(model.getId(), model.getName(), BigInteger.valueOf(model.getCostOfIssuingCertificate()),
                    BigInteger.valueOf(model.getDurationInHours()), BigInteger.valueOf(model.getExpirationInDays()), model.getCanBeRenewed(), BigInteger.valueOf(model.getCostOfRenewingCertificate())).send();
            return getCertificationCourseDetail(certificationCourseContract, model.getId());
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     * Private Methods
     */
    /**
     *
     * @param certificationCourseContract
     * @param courseId
     * @return
     * @throws Exception
     */
    private CertificationCourseModelEntity getCertificationCourseDetail(CertificationCourseContract certificationCourseContract, String courseId) throws Exception {
        final CertificationCourseRecord certificationCourseDetail = certificationCourseContract.getCertificateCourseDetail(courseId).send();
        return certificationCourseEntityMapper.courseRecordToCertificationCourseEntity(certificationCourseDetail);
    }

    /**
     * Load Certification Course Contract
     *
     * @param walletHash
     * @return
     * @throws LoadWalletException
     */
    private CertificationCourseContract loadCertificationCourseContract(final String walletHash) throws LoadWalletException {
        final Credentials credentials = walletService.loadCredentials(walletHash);
        final FastRawTransactionManager txManager = new FastRawTransactionManager(web3j, credentials, properties.getChainId());
        return CertificationCourseContract.load(properties.getCertificationCourseContractAddress(),
                web3j, txManager, properties.gas());
    }

    /**
     *
     * @return
     */
    private CertificationCourseContract loadCertificationCourseContract() {
        return CertificationCourseContract.load(properties.getCertificationCourseContractAddress(),
                web3j, rootTxManager, properties.gas());
    }
}
