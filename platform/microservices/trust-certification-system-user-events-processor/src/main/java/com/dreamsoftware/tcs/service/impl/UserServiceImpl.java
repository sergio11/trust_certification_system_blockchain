package com.dreamsoftware.tcs.service.impl;

import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;

/**
 *
 * @author ssanchez
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    /**
     * User Repository
     */
    private final UserRepository userRepository;

    /**
     *
     * @param walletHash
     */
    @Override
    public UserEntity validate(final String walletHash) {
        Assert.notNull(walletHash, "walletHash can not be null");
        log.debug("validate -> " + walletHash + " CALLED!");
        final UserEntity userEntity = userRepository.findOneByWalletHash(walletHash)
                .orElseThrow(() -> new IllegalStateException("WalletHash not found"));
        userEntity.setActivationDate(new Date());
        userEntity.setState(UserStateEnum.VALIDATED);
        return userRepository.save(userEntity);
    }

    /**
     *
     * @return
     */
    @Override
    public long deleteUnactivatedAccounts() {
        return userRepository.deleteByState(UserStateEnum.PENDING_ACTIVATE);
    }
}
