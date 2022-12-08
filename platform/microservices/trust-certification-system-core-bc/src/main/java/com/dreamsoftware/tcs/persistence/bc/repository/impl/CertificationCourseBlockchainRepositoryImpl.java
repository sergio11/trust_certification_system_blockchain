package com.dreamsoftware.tcs.persistence.bc.repository.impl;

import com.dreamsoftware.tcs.contracts.CertificationCourseContract;
import com.dreamsoftware.tcs.contracts.CertificationCourseContract.CertificationCourseRecord;
import com.dreamsoftware.tcs.exception.LoadWalletException;
import com.dreamsoftware.tcs.persistence.bc.core.SupportBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.ICertificationCourseBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationCourseBcEntity;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationCourseEventEntity;
import com.dreamsoftware.tcs.persistence.bc.repository.mapper.CertificationCourseEntityMapper;
import com.dreamsoftware.tcs.persistence.bc.repository.mapper.CertificationCourseEventEntityMapper;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
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
     * @param id
     * @param costOfIssuingCertificate
     * @param durationInHours
     * @param expirationInDays
     * @param canBeRenewed
     * @param costOfRenewingCertificate
     * @throws RepositoryException
     */
    @Override
    public CertificationCourseBcEntity addCertificationCourse(final String walletHash, final String id, final Long costOfIssuingCertificate, final Long durationInHours, final Long expirationInDays, final Boolean canBeRenewed, final Long costOfRenewingCertificate) throws RepositoryException {
        Assert.notNull(walletHash, "walletHash can not be null");
        Assert.notNull(id, "id can not be null");
        Assert.notNull(costOfIssuingCertificate, "costOfIssuingCertificate can not be null");
        Assert.notNull(durationInHours, "durationInHours can not be null");
        Assert.notNull(expirationInDays, "expirationInDays can not be null");
        Assert.notNull(canBeRenewed, "canBeRenewed can not be null");
        Assert.notNull(costOfRenewingCertificate, "costOfRenewingCertificate can not be null");
        try {
            log.debug("register new certification course CALLED!");
            final CertificationCourseContract certificationCourseContract = loadCertificationCourseContract(walletHash);
            final TransactionReceipt transactionReceipt = certificationCourseContract.addCertificationCourse(id, BigInteger.valueOf(costOfIssuingCertificate), BigInteger.valueOf(durationInHours),
                    BigInteger.valueOf(expirationInDays), canBeRenewed, BigInteger.valueOf(costOfRenewingCertificate)).send();
            log.debug("Certification Course registered with id: " + id + " transactionReceipt " + transactionReceipt.getBlockHash() + " CALLED!");
            return getCertificationCourseDetail(certificationCourseContract, id);
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
    public CertificationCourseBcEntity enable(final String caWallet, final String courseId) throws RepositoryException {
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
    public CertificationCourseBcEntity disable(final String caWallet, final String courseId) throws RepositoryException {
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
    public CertificationCourseBcEntity get(final String caWallet, final String courseId) throws RepositoryException {
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
     * @return @throws RepositoryException
     */
    @Override
    public Iterable<CertificationCourseBcEntity> getAll() throws RepositoryException {
        try {
            final CertificationCourseContract certificationCourseContract = loadCertificationCourseContract();
            final List<CertificationCourseRecord> caCourses = certificationCourseContract.getAllCertificationCourses().send();
            return certificationCourseEntityMapper.courseRecordToCertificationCourseEntity(caCourses);
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
    public Iterable<CertificationCourseBcEntity> getAllByCa(final String caWallet) throws RepositoryException {
        Assert.notNull(caWallet, "Ca Wallet can not be null");
        try {
            final CertificationCourseContract certificationCourseContract = loadCertificationCourseContract(caWallet);
            final List<CertificationCourseRecord> caCourses = certificationCourseContract.getMyCertificationCourses().send();
            return certificationCourseEntityMapper.courseRecordToCertificationCourseEntity(caCourses);
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
    public CertificationCourseBcEntity get(final String courseId) throws RepositoryException {
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
     * @param courseId
     * @return
     * @throws RepositoryException
     */
    @Override
    public Boolean canBeIssued(final String courseId) throws RepositoryException {
        Assert.notNull(courseId, "Course Id can not be null");
        try {
            final CertificationCourseContract certificationCourseContract = loadCertificationCourseContract();
            return certificationCourseContract.canBeIssued(courseId).send();
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

    /**
     * Can be renewed
     *
     * @param courseId
     * @return
     * @throws RepositoryException
     */
    @Override
    public Boolean canBeRenewed(final String courseId) throws RepositoryException {
        Assert.notNull(courseId, "Course Id can not be null");
        try {
            final CertificationCourseContract certificationCourseContract = loadCertificationCourseContract();
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
    public CertificationCourseBcEntity remove(String caWallet, String courseId) throws RepositoryException {
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
            return Flowable.merge(List.of(
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
     * @param walletHash
     * @param id
     * @param costOfIssuingCertificate
     * @param durationInHours
     * @param expirationInDays
     * @param canBeRenewed
     * @param costOfRenewingCertificate
     * @return
     * @throws RepositoryException
     */
    @Override
    public CertificationCourseBcEntity update(final String walletHash, final String id, final Long costOfIssuingCertificate, final Long durationInHours, final Long expirationInDays, final Boolean canBeRenewed, final Long costOfRenewingCertificate) throws RepositoryException {
        Assert.notNull(walletHash, "Ca Wallet can not be null");
        Assert.notNull(id, "Course Id can not be null");
        try {
            final CertificationCourseContract certificationCourseContract = loadCertificationCourseContract(walletHash);
            certificationCourseContract.updateCertificationCourse(id, BigInteger.valueOf(costOfIssuingCertificate),
                    BigInteger.valueOf(durationInHours), BigInteger.valueOf(expirationInDays), canBeRenewed, BigInteger.valueOf(costOfRenewingCertificate)).send();
            return getCertificationCourseDetail(certificationCourseContract, id);
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
    private CertificationCourseBcEntity getCertificationCourseDetail(final CertificationCourseContract certificationCourseContract, final String courseId) throws Exception {
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
