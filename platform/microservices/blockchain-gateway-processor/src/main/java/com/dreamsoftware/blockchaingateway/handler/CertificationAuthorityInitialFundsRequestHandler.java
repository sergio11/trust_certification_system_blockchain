package com.dreamsoftware.blockchaingateway.handler;

import com.dreamsoftware.blockchaingateway.model.CertificationAuthorityInitialFundsRequestEvent;
import com.dreamsoftware.blockchaingateway.model.CertificationAuthorityRegistrationRequestEvent;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.IEtherFaucetBlockchainRepository;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.ITokenManagementBlockchainRepository;
import com.dreamsoftware.blockchaingateway.persistence.exception.RepositoryException;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * Certification Authority Initial Funds Request Handler
 *
 * @author ssanchez
 */
@Service
@RequiredArgsConstructor
public class CertificationAuthorityInitialFundsRequestHandler {

    private final Logger logger = LoggerFactory.getLogger(CertificationAuthorityInitialFundsRequestHandler.class);

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

    /**
     * Certification Authority Initial Funds Request Channel
     *
     * @return
     */
    @Bean
    public Function<CertificationAuthorityInitialFundsRequestEvent, CertificationAuthorityRegistrationRequestEvent> certificationAuthorityInitialFunds() {
        return (CertificationAuthorityInitialFundsRequestEvent event) -> onCertificationAuthorityInitialFundsRequested(event);
    }

    /**
     * Private Methods
     */
    /**
     * On Certification Authority Initial Funds Requested
     *
     * @param event
     */
    private CertificationAuthorityRegistrationRequestEvent onCertificationAuthorityInitialFundsRequested(final CertificationAuthorityInitialFundsRequestEvent event) {
        logger.debug("onCertificationAuthorityRegistered CALLED!");
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
