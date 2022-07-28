package com.dreamsoftware.tcs.service.impl;

import com.dreamsoftware.tcs.model.events.OnNewUserRegistrationEvent;
import com.dreamsoftware.tcs.persistence.bc.repository.ICertificationAuthorityBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.IEtherFaucetBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.ITokenManagementBlockchainRepository;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserTypeEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.service.IUserService;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 *
 * @author ssanchez
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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

    /**
     * User Repository
     */
    private final UserRepository userRepository;

    /**
     *
     * @param event
     */
    @Override
    public void register(OnNewUserRegistrationEvent event) throws RepositoryException {
        Assert.notNull(event, "Event can not be null");
        logger.debug("register -> " + event.getWalletHash() + " CALLED!");
        // Add Seed Funds
        etherFaucetBlockchainRepository.addSeedFunds(event.getWalletHash());
        // Buy Tokens
        tokenManagementBlockchainRepository.addTokens(event.getWalletHash(), getDefaultTCSForUserType(event.getUserType()));
        if (event.getUserType() == UserTypeEnum.CA) {
            certificationAuthorityBlockchainRepository.register(event.getName(), DEFAULT_COST_OF_ISSUING_CERTIFICATE, event.getWalletHash());
        }
    }

    /**
     *
     * @param walletHash
     */
    @Override
    public void validate(String walletHash) {
        Assert.notNull(walletHash, "walletHash can not be null");
        logger.debug("validate -> " + walletHash + " CALLED!");
        userRepository.findOneByWalletHash(walletHash).ifPresent((userEntity) -> {
            userEntity.setActivationDate(new Date());
            userEntity.setState(UserStateEnum.VALIDATED);
            userRepository.save(userEntity);
        });
    }

    /**
     * Private Methods
     */
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
