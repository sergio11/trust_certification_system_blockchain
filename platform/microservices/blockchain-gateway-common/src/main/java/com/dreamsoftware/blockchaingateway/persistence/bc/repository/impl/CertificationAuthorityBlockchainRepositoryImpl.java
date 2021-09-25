package com.dreamsoftware.blockchaingateway.persistence.bc.repository.impl;

import com.dreamsoftware.blockchaingateway.contracts.CertificationAuthorityContract;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.ICertificationAuthorityBlockchainRepository;
import com.dreamsoftware.blockchaingateway.persistence.bc.core.SupportBlockchainRepository;
import com.dreamsoftware.blockchaingateway.persistence.exception.RepositoryException;
import java.math.BigInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.tx.FastRawTransactionManager;

/**
 *
 * @author ssanchez
 */
@Component
public class CertificationAuthorityBlockchainRepositoryImpl extends SupportBlockchainRepository implements ICertificationAuthorityBlockchainRepository {

    private final Logger logger = LoggerFactory.getLogger(CertificationAuthorityBlockchainRepositoryImpl.class);

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
            final Credentials credentials = walletService.loadCredentials(walletHash);
            final FastRawTransactionManager txManager = new FastRawTransactionManager(web3j, credentials, properties.getChainId());
            final CertificationAuthorityContract certificationAuthorityContract = CertificationAuthorityContract.load(properties.getCertificationAuthorityContractAddress(),
                    web3j, txManager, properties.gas());
            certificationAuthorityContract.addCertificationAuthority(name, BigInteger.valueOf(defaultCostOfIssuingCertificate)).send();
        } catch (final Exception ex) {
            throw new RepositoryException(ex.getMessage(), ex);
        }
    }

}
