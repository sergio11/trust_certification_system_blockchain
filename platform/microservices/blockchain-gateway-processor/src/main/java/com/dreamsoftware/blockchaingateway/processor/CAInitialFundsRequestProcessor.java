package com.dreamsoftware.blockchaingateway.processor;

import com.dreamsoftware.blockchaingateway.model.CertificationAuthorityInitialFundsRequestEvent;
import com.dreamsoftware.blockchaingateway.model.CertificationAuthorityRegistrationRequestEvent;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.IEtherFaucetBlockchainRepository;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.ITokenManagementBlockchainRepository;
import com.dreamsoftware.blockchaingateway.persistence.exception.RepositoryException;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * CA Initial Funds Request Processor
 *
 * @author ssanchez
 */
@Component("caInitialFundsRequestProcessor")
@RequiredArgsConstructor
public class CAInitialFundsRequestProcessor implements Function<CertificationAuthorityInitialFundsRequestEvent, CertificationAuthorityRegistrationRequestEvent> {

    private final Logger logger = LoggerFactory.getLogger(CAInitialFundsRequestProcessor.class);

    /**
     * Constants
     */
    private final static Long DEFAULT_CERTIFICATION_AUTHORITY_TOKENS = 20L;

    /**
     * Ether Faucet Blockchain Repository
     */
    private final IEtherFaucetBlockchainRepository etherFaucetBlockchainRepository;

    /**
     * Token Management Blockchain Repository
     */
    private final ITokenManagementBlockchainRepository tokenManagementBlockchainRepository;

    @Override
    public CertificationAuthorityRegistrationRequestEvent apply(CertificationAuthorityInitialFundsRequestEvent event) {
        logger.debug("CAInitialFundsRequestProcessor CALLED!");
        CertificationAuthorityRegistrationRequestEvent registrationRequest = null;
        try {
            // Add Seed Funds
            etherFaucetBlockchainRepository.addSeedFunds(event.getWalletHash());
            // Buy Tokens
            tokenManagementBlockchainRepository.addTokens(event.getWalletHash(), DEFAULT_CERTIFICATION_AUTHORITY_TOKENS);

            registrationRequest = new CertificationAuthorityRegistrationRequestEvent(
                    event.getName(), event.getDefaultCostOfIssuingCertificate(), event.getWalletHash());
        } catch (final RepositoryException ex) {
            logger.debug("Ex Message -> " + ex.getMessage());
        }
        return registrationRequest;
    }
}
