package com.dreamsoftware.tcs.service.impl;

import com.dreamsoftware.tcs.persistence.bc.repository.IEtherFaucetBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.ITokenManagementBlockchainRepository;
import com.dreamsoftware.tcs.persistence.nosql.entity.CreatedOrderEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CreatedOrderStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.CreatedOrderRepository;
import com.dreamsoftware.tcs.service.IUserOrdersService;
import com.dreamsoftware.tcs.stream.events.user.NewTokensOrderApprovedEvent;
import com.dreamsoftware.tcs.stream.events.user.OnTokensOrderCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserOrdersServiceImpl implements IUserOrdersService {


    /**
     * Ether Faucet Blockchain Repository
     */
    private final IEtherFaucetBlockchainRepository etherFaucetBlockchainRepository;

    /**
     * Token Management Blockchain Repository
     */
    private final ITokenManagementBlockchainRepository tokenManagementBlockchainRepository;

    /**
     * Created order repository
     */
    private final CreatedOrderRepository createdOrderRepository;

    @Override
    public Long addTokensToWallet(NewTokensOrderApprovedEvent event) throws Exception {
        Assert.notNull(event, "event can not be null");
        log.debug("Add tokens to wallet, order id " + event.getOrderId());
        final CreatedOrderEntity createdOrder = createdOrderRepository.findById(event.getOrderId()).orElseThrow(() -> new IllegalStateException("Order not found"));
        final String targetWalletHash = createdOrder.getUser().getWalletHash();
        final Long amountWei = createdOrder.getAmountWEI();
        etherFaucetBlockchainRepository.sendFunds(targetWalletHash, amountWei);
        tokenManagementBlockchainRepository.addTokens(targetWalletHash, createdOrder.getTokens());
        return tokenManagementBlockchainRepository.getMyTokens(targetWalletHash);
    }

    @Override
    public CreatedOrderEntity completeOrder(OnTokensOrderCompletedEvent event) {
        Assert.notNull(event, "event can not be null");
        log.debug("Complete Order id: " + event.getOrderId());

        final CreatedOrderEntity orderEntity = createdOrderRepository.findById(event.getOrderId())
                .orElseThrow(() -> new IllegalStateException("Order not found"));
        orderEntity.setCompletedAt(new Date());
        orderEntity.setStatus(CreatedOrderStateEnum.COMPLETED);
        return createdOrderRepository.save(orderEntity);
    }
}
