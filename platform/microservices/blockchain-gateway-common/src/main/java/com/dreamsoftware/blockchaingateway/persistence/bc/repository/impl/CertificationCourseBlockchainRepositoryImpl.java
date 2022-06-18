package com.dreamsoftware.blockchaingateway.persistence.bc.repository.impl;

import com.dreamsoftware.blockchaingateway.contracts.CertificationCourseContract;
import com.dreamsoftware.blockchaingateway.contracts.CertificationCourseContract.CertificationCourseRecord;
import com.dreamsoftware.blockchaingateway.exception.LoadWalletException;
import com.dreamsoftware.blockchaingateway.persistence.bc.core.SupportBlockchainRepository;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.ICertificationCourseBlockchainRepository;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.CertificationCourseEntity;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.mapper.CertificationCourseEntityMapper;
import com.dreamsoftware.blockchaingateway.persistence.exception.RepositoryException;
import java.math.BigInteger;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.web3j.crypto.Credentials;
import org.web3j.tx.FastRawTransactionManager;

/**
 * Certification Course Blockchain Repository
 *
 * @author ssanchez
 */
@Component
@RequiredArgsConstructor
public class CertificationCourseBlockchainRepositoryImpl extends SupportBlockchainRepository implements ICertificationCourseBlockchainRepository {

    private final Logger logger = LoggerFactory.getLogger(CertificationCourseBlockchainRepositoryImpl.class);

    private final CertificationCourseEntityMapper certificationCourseEntityMapper;

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
    public void register(String walletHash, String name, Long costOfIssuingCertificate, Long durationInHours, Long expirationInDays, Boolean canBeRenewed, Long costOfRenewingCertificate) throws RepositoryException {
        try {
            final CertificationCourseContract certificationCourseContract = loadCertificationCourseContract(walletHash);
            certificationCourseContract.addCertificationCourse(name, BigInteger.valueOf(costOfIssuingCertificate), BigInteger.valueOf(durationInHours),
                    BigInteger.valueOf(expirationInDays), canBeRenewed, BigInteger.valueOf(costOfRenewingCertificate)).send();
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
    public CertificationCourseEntity enable(final String caWallet, final String courseId) throws RepositoryException {
        Assert.notNull(caWallet, "Ca Wallet can not be null");
        Assert.notNull(courseId, "Course Id can not be null");
        try {
            final CertificationCourseContract certificationCourseContract = loadCertificationCourseContract(caWallet);
            certificationCourseContract.enableCertificationCourse(courseId).send();
            final CertificationCourseRecord certificationCourseDetail = certificationCourseContract.getCertificateCourseDetail(courseId).send();
            return certificationCourseEntityMapper.courseRecordToCertificationCourseEntity(certificationCourseDetail);
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
    public CertificationCourseEntity disable(final String caWallet, final String courseId) throws RepositoryException {
        Assert.notNull(caWallet, "Ca Wallet can not be null");
        Assert.notNull(courseId, "Course Id can not be null");
        try {
            final CertificationCourseContract certificationCourseContract = loadCertificationCourseContract(caWallet);
            certificationCourseContract.disableCertificationCourse(courseId).send();
            final CertificationCourseRecord certificationCourseRecord = certificationCourseContract.getCertificateCourseDetail(caWallet).send();
            return certificationCourseEntityMapper.courseRecordToCertificationCourseEntity(certificationCourseRecord);
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
    public CertificationCourseEntity get(String caWallet, String courseId) throws RepositoryException {
        Assert.notNull(caWallet, "Ca Wallet can not be null");
        Assert.notNull(courseId, "Course Id can not be null");
        try {
            final CertificationCourseContract certificationCourseContract = loadCertificationCourseContract(caWallet);
            final CertificationCourseRecord certificationCourseRecord = certificationCourseContract.getCertificateCourseDetail(courseId).send();
            return certificationCourseEntityMapper.courseRecordToCertificationCourseEntity(certificationCourseRecord);
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
    public CertificationCourseEntity remove(String caWallet, String courseId) throws RepositoryException {
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
     * Private Methods
     */
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
}
