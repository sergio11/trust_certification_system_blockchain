package com.dreamsoftware.tcs.service.impl;

import com.dreamsoftware.tcs.model.events.NewTokensOrderApprovedEvent;
import com.dreamsoftware.tcs.model.events.OnNewUserRegistrationEvent;
import com.dreamsoftware.tcs.model.events.OnTokensOrderCompletedEvent;
import com.dreamsoftware.tcs.persistence.bc.repository.ICertificationAuthorityBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.IEtherFaucetBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.ITokenManagementBlockchainRepository;
import com.dreamsoftware.tcs.persistence.nosql.entity.CreatedOrderEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CreatedOrderStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserTypeEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.CreatedOrderRepository;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.service.INotificationService;
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
     * Created order repository
     */
    private final CreatedOrderRepository createdOrderRepository;

    /**
     * Notification Service
     */
    private final INotificationService notificationService;

    /**
     *
     * @param event
     */
    @Override
    public void register(OnNewUserRegistrationEvent event) throws Exception {
        Assert.notNull(event, "Event can not be null");
        logger.debug("register -> " + event.getWalletHash() + " CALLED!");
        // Add initial TCS ERC-20 funds
        tokenManagementBlockchainRepository.sendInitialTokenFundsTo(event.getWalletHash(), event.getUserType());
        if (event.getUserType() == UserTypeEnum.CA) {
            certificationAuthorityBlockchainRepository.register(event.getName(), event.getWalletHash());
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
            notificationService.onUserAccountValidated(userEntity);
        });
    }

    /**
     *
     * @param event
     */
    @Override
    public Long addTokensToWallet(final NewTokensOrderApprovedEvent event) throws Exception {
        Assert.notNull(event, "event can not be null");
        logger.debug("Add tokens to wallet, order id " + event.getOrderId());
        final CreatedOrderEntity createdOrder = createdOrderRepository.findById(event.getOrderId()).orElseThrow(() -> new IllegalStateException("Order not found"));
        final String targetWalletHash = createdOrder.getUser().getWalletHash();
        final Long amountWei = createdOrder.getAmountWEI();
        etherFaucetBlockchainRepository.sendFunds(targetWalletHash, amountWei);
        tokenManagementBlockchainRepository.addTokens(targetWalletHash, createdOrder.getTokens());
        return tokenManagementBlockchainRepository.getMyTokens(targetWalletHash);
    }

    /**
     *
     * @param event
     */
    @Override
    public void completeOrder(final OnTokensOrderCompletedEvent event) {
        Assert.notNull(event, "event can not be null");
        logger.debug("Complete Order id: " + event.getOrderId());
        createdOrderRepository.findById(event.getOrderId()).ifPresent((orderEntity) -> {
            orderEntity.setCompletedAt(new Date());
            orderEntity.setStatus(CreatedOrderStateEnum.COMPLETED);
            createdOrderRepository.save(orderEntity);
            notificationService.onUserOrderCompleted(orderEntity);
        });
    }
}
