package com.dreamsoftware.blockchaingateway.persistence.bc.repository.impl;

import com.dreamsoftware.blockchaingateway.contracts.CertificationAuthorityContract;
import com.dreamsoftware.blockchaingateway.contracts.CertificationAuthorityContract.CertificationAuthorityRecord;
import com.dreamsoftware.blockchaingateway.exception.LoadWalletException;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.ICertificationAuthorityBlockchainRepository;
import com.dreamsoftware.blockchaingateway.persistence.bc.core.SupportBlockchainRepository;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.CertificationAuthorityEntity;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.mapper.CertificationAuthorityEntityMapper;
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
 *
 * @author ssanchez
 */
@Component
@RequiredArgsConstructor
public class CertificationAuthorityBlockchainRepositoryImpl extends SupportBlockchainRepository implements ICertificationAuthorityBlockchainRepository {

    private final Logger logger = LoggerFactory.getLogger(CertificationAuthorityBlockchainRepositoryImpl.class);

    private final CertificationAuthorityEntityMapper certificationAuthorityEntityMapper;

    /**
     * Register Certification Authority
     *
     * @param name
     * @param defaultCostOfIssuingCertificate
     * @param walletHash
     * @throws RepositoryException
     */
    @Override
    public void register(String name, Long defaultCostOfIssuingCertificate, String walletHash) throws RepositoryException {
        try {
            logger.debug("registerCertificationAuthority address: " + properties.getCertificationAuthorityContractAddress());
            final CertificationAuthorityContract caContract = loadCAContract(walletHash);
            caContract.addCertificationAuthority(name, BigInteger.valueOf(defaultCostOfIssuingCertificate)).send();
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

}
