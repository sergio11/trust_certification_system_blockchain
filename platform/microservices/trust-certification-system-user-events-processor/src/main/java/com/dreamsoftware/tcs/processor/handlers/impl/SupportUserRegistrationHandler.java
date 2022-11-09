package com.dreamsoftware.tcs.processor.handlers.impl;

import com.dreamsoftware.tcs.persistence.bc.repository.IEtherFaucetBlockchainRepository;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.stream.events.AbstractEvent;
import com.dreamsoftware.tcs.stream.events.notifications.users.UserRegisteredNotificationEvent;
import com.dreamsoftware.tcs.utils.AbstractProcessAndReturnHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.math.BigInteger;

/**
 *
 * @param <T>
 */
@Slf4j
public abstract class SupportUserRegistrationHandler<T extends AbstractEvent> extends AbstractProcessAndReturnHandler<T, UserRegisteredNotificationEvent> {

    /**
     * Ether Faucet Blockchain Repository
     */
    @Autowired
    private IEtherFaucetBlockchainRepository etherFaucetBlockchainRepository;

    /**
     *
     * @param userWalletHash
     * @throws RepositoryException
     */
    protected void addSeedFunds(final String userWalletHash) throws RepositoryException {
        Assert.notNull(userWalletHash, "User Wallet Hash can not be null");
        final BigInteger initialAmount = etherFaucetBlockchainRepository.getInitialAmount();
        final BigInteger currentBalance = etherFaucetBlockchainRepository.getBalance();
        log.debug("initialAmount -> " + initialAmount + " Current Balance Contract -> " + currentBalance);
        if (currentBalance.compareTo(initialAmount) < 0) {
            log.debug("depositFunds ...");
            etherFaucetBlockchainRepository.depositFunds(initialAmount.multiply(BigInteger.TEN));
        }
        // Add ETH funds
        etherFaucetBlockchainRepository.addSeedFunds(userWalletHash);
    }
}
