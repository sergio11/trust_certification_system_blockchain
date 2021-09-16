package com.dreamsoftware.blockchaingateway.services.impl;

import com.dreamsoftware.blockchaingateway.model.CertificationAuthorityInitialFundsRequestEvent;
import com.dreamsoftware.blockchaingateway.persistence.entity.CertificationAuthorityEntity;
import com.dreamsoftware.blockchaingateway.persistence.repository.CertificationAuthorityRepository;
import com.dreamsoftware.blockchaingateway.service.IEventPublisher;
import com.dreamsoftware.blockchaingateway.service.IWalletService;
import com.dreamsoftware.blockchaingateway.services.ICertificationAuthorityService;
import com.dreamsoftware.blockchaingateway.web.dto.request.RegisterCertificationAuthorityDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dreamsoftware.blockchaingateway.service.IPasswordHashingService;
import com.dreamsoftware.blockchaingateway.web.controller.error.exception.RegisterCertificationAuthorityException;

/**
 *
 * @author ssanchez
 */
@Service
@RequiredArgsConstructor
public class CertificationAuthorityServiceImpl implements ICertificationAuthorityService {

    private Logger logger = LoggerFactory.getLogger(CertificationAuthorityServiceImpl.class);

    /**
     * Wallet Service
     */
    private final IWalletService walletService;

    /**
     * Hash Service
     */
    private final IPasswordHashingService hashService;

    /**
     * Certification Authority Repository
     */
    private final CertificationAuthorityRepository certificationAuthorityRepository;

    /**
     * Event Publisher
     */
    private IEventPublisher<CertificationAuthorityInitialFundsRequestEvent> eventPublisher;

    /**
     * Register Certification Authority
     *
     * @param registerCertificationAuthorityDTO
     */
    @Override
    public void register(final RegisterCertificationAuthorityDTO registerCertificationAuthorityDTO) {
        try {
            // Generate Wallet
            final String walletHash = walletService.generateWallet();
            logger.debug("Wallet created with hash: " + walletHash);
            final String secretHash = hashService.hash(registerCertificationAuthorityDTO.getPassword());
            final CertificationAuthorityEntity certificationAuthority = new CertificationAuthorityEntity();
            certificationAuthority.setName(registerCertificationAuthorityDTO.getName());
            certificationAuthority.setPasswordHash(secretHash);
            certificationAuthority.setWalletHash(walletHash);
            final CertificationAuthorityEntity certificationAuthoritySaved = certificationAuthorityRepository.save(certificationAuthority);
            eventPublisher.publish(new CertificationAuthorityInitialFundsRequestEvent(certificationAuthoritySaved.getName(),
                    registerCertificationAuthorityDTO.getDefaultCostOfIssuingCertificate(), walletHash));

        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("exception ocurred " + ex.getMessage() + " CALLED");
            throw new RegisterCertificationAuthorityException(ex.getMessage(), ex);
        }
    }
}
