package com.dreamsoftware.blockchaingateway.services.impl;

import com.dreamsoftware.blockchaingateway.model.CertificationAuthorityInitialFundsRequestEvent;
import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.blockchaingateway.service.IEventPublisher;
import com.dreamsoftware.blockchaingateway.service.IWalletService;
import com.dreamsoftware.blockchaingateway.services.ICertificationAuthorityService;
import com.dreamsoftware.blockchaingateway.web.dto.request.SignUpUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dreamsoftware.blockchaingateway.service.IPasswordHashingService;
import com.dreamsoftware.blockchaingateway.web.controller.error.exception.GetCertificationAuthorityException;
import com.dreamsoftware.blockchaingateway.web.controller.error.exception.RegisterCertificationAuthorityException;
import com.dreamsoftware.blockchaingateway.web.dto.response.CertificationAuthorityDetailDTO;
import javax.annotation.PostConstruct;
import org.springframework.util.Assert;
import com.dreamsoftware.blockchaingateway.persistence.nosql.repository.UserRepository;

/**
 *
 * @author ssanchez
 */
@Service
@RequiredArgsConstructor
public class CertificationAuthorityServiceImpl implements ICertificationAuthorityService {

    private final Logger logger = LoggerFactory.getLogger(CertificationAuthorityServiceImpl.class);

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
    private final UserRepository certificationAuthorityRepository;

    /**
     * Event Publisher
     */
    private final IEventPublisher eventPublisher;

    /**
     * Get Detail
     *
     * @param id
     * @return
     */
    @Override
    public CertificationAuthorityDetailDTO getDetail(String id) {
        Assert.notNull(id, "Id can not be null");
        try {
            //final UserEntity certificationAuthority = certificationAuthorityRepository.findById(new ObjectId(id));
            return null;
        } catch (final Exception ex) {
            logger.error("exception ocurred " + ex.getMessage() + " CALLED");
            throw new GetCertificationAuthorityException(ex.getMessage(), ex);
        }
    }

    /**
     * Register Certification Authority
     *
     * @param registerCertificationAuthorityDTO
     */
    @Override
    public void register(final SignUpUserDTO registerCertificationAuthorityDTO) {
        try {
            // Generate Wallet
            final String walletHash = walletService.generateWallet();
            logger.debug("Wallet created with hash: " + walletHash);
            final String secretHash = hashService.hash(registerCertificationAuthorityDTO.getPasswordClear());
            final UserEntity certificationAuthority = new UserEntity();
            certificationAuthority.setName(registerCertificationAuthorityDTO.getName());
            certificationAuthority.setPassword(secretHash);
            certificationAuthority.setWalletHash(walletHash);
            final UserEntity certificationAuthoritySaved = certificationAuthorityRepository.save(certificationAuthority);
            eventPublisher.publish(new CertificationAuthorityInitialFundsRequestEvent(certificationAuthoritySaved.getName(), walletHash));
        } catch (final Exception ex) {
            ex.printStackTrace();
            logger.error("exception ocurred " + ex.getMessage() + " CALLED");
            throw new RegisterCertificationAuthorityException(ex.getMessage(), ex);
        }
    }

    @PostConstruct
    private void onPostConstruct() {
        Assert.notNull(walletService, "Wallet Service can not be null");
        Assert.notNull(hashService, "Hash Service can not be null");
        Assert.notNull(certificationAuthorityRepository, "Certification Authority Repository can not be null");
        Assert.notNull(eventPublisher, "Event Publisher can not be null");
    }

}
