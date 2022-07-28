package com.dreamsoftware.tcs.processor;

import com.dreamsoftware.tcs.model.events.OnUserRegisteredEvent;
import com.dreamsoftware.tcs.model.events.OnNewUserRegistrationEvent;
import com.dreamsoftware.tcs.persistence.bc.repository.ICertificationAuthorityBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.IEtherFaucetBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.ITokenManagementBlockchainRepository;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserTypeEnum;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * New User Registration Processor
 *
 * @author ssanchez
 */
@Component("newUserRegistrationProcessor")
@RequiredArgsConstructor
public class NewUserRegistrationProcessor implements Function<OnNewUserRegistrationEvent, OnUserRegisteredEvent> {

    private final Logger logger = LoggerFactory.getLogger(NewUserRegistrationProcessor.class);

    /**
     * Certification Authority Default cost of issuing certificate
     */
    private final static Long DEFAULT_COST_OF_ISSUING_CERTIFICATE = 8L;
    private final static Long DEFAULT_CERTIFICATION_AUTHORITY_TOKENS = 20L;
    private final static Long DEFAULT_ADMIN_TOKENS = 30L;
    private final static Long DEFAULT_STUDENTS_TOKENS = 10L;

    /**
     * Certification Authority Blockchain Repository
     */
    private final ICertificationAuthorityBlockchainRepository certificationAuthorityBlockchainRepository;

    /**
     * Ether Faucet Blockchain Repository
     */
    private final IEtherFaucetBlockchainRepository etherFaucetBlockchainRepository;

    /**
     * Token Management Blockchain Repository
     */
    private final ITokenManagementBlockchainRepository tokenManagementBlockchainRepository;

    @Override
    public OnUserRegisteredEvent apply(OnNewUserRegistrationEvent event) {
        logger.debug("NewUserRegistrationProcessor CALLED!");
        OnUserRegisteredEvent registeredEvent = null;
        try {
            // Add Seed Funds
            etherFaucetBlockchainRepository.addSeedFunds(event.getWalletHash());
            // Buy Tokens
            tokenManagementBlockchainRepository.addTokens(event.getWalletHash(), getDefaultTCSForUserType(event.getUserType()));
            if (event.getUserType() == UserTypeEnum.CA) {
                certificationAuthorityBlockchainRepository.register(event.getName(), DEFAULT_COST_OF_ISSUING_CERTIFICATE, event.getWalletHash());
            }
            registeredEvent = new OnUserRegisteredEvent(event.getWalletHash());
        } catch (final RepositoryException ex) {
            logger.debug("Ex Message -> " + ex.getMessage());
        }
        return registeredEvent;
    }

    /**
     * Get Default TCS for user type
     *
     * @param userType
     * @return
     */
    private Long getDefaultTCSForUserType(UserTypeEnum userType) {
        long defaultTokens;
        switch (userType) {
            case ADMIN:
                defaultTokens = DEFAULT_ADMIN_TOKENS;
                break;
            case CA:
                defaultTokens = DEFAULT_CERTIFICATION_AUTHORITY_TOKENS;
                break;
            default:
                defaultTokens = DEFAULT_STUDENTS_TOKENS;
                break;
        }
        return defaultTokens;
    }
}
