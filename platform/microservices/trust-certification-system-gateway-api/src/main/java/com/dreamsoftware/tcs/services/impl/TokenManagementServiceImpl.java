package com.dreamsoftware.tcs.services.impl;

import com.dreamsoftware.tcs.config.properties.StreamChannelsProperties;
import com.dreamsoftware.tcs.mapper.CreatedOrderMapper;
import com.dreamsoftware.tcs.stream.events.user.NewTokensOrderApprovedEvent;
import com.dreamsoftware.tcs.persistence.bc.repository.ITokenManagementBlockchainRepository;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.persistence.nosql.entity.CreatedOrderEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CreatedOrderStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.CreatedOrderRepository;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.services.ICryptoCompareService;
import com.dreamsoftware.tcs.services.IPaymentService;
import com.dreamsoftware.tcs.services.ITokenManagementService;
import com.dreamsoftware.tcs.web.dto.internal.CreateOrderRequestDTO;
import com.dreamsoftware.tcs.web.dto.internal.CreatedOrderDTO;
import com.dreamsoftware.tcs.web.dto.internal.CryptoComparePricesDTO;
import com.dreamsoftware.tcs.web.dto.request.PlaceTokensOrderRequestDTO;
import com.dreamsoftware.tcs.web.dto.response.OrderDetailDTO;
import com.dreamsoftware.tcs.web.dto.response.TokenPricesDTO;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 *
 * @author ssanchez
 */
@Service("tokenManagementService")
@RequiredArgsConstructor
@Slf4j
public class TokenManagementServiceImpl implements ITokenManagementService {

    private final Double ONE_WEI_IN_ETH = 0.000000000000000001;

    private final ITokenManagementBlockchainRepository tokenManagementBlockchainRepository;
    private final UserRepository userRepository;
    private final IPaymentService paymentService;
    private final ICryptoCompareService cryptoCompareService;
    private final CreatedOrderRepository createdOrderRepository;
    private final CreatedOrderMapper createdOrderMapper;
    private final StreamBridge streamBridge;
    private final StreamChannelsProperties streamChannelsProperties;

    /**
     *
     * @param walletHash
     * @return
     */
    @Override
    public TokenPricesDTO getMyTokens(final String walletHash) throws Exception {
        Assert.notNull(walletHash, "Wallet Hash can not be null");
        final Long tokenCount =  tokenManagementBlockchainRepository.getMyTokens(walletHash);
        return getTokenPricesByTokenCount(tokenCount);
    }

    /**
     *
     * @param tokenCount
     * @return
     */
    @Override
    public TokenPricesDTO getTokenPrices(final Long tokenCount) throws Exception {
        Assert.notNull(tokenCount, "Token Count can not be null");
        return getTokenPricesByTokenCount(tokenCount);
    }

    /**
     *
     * @param walletHash
     * @param clientId
     * @return
     */
    @Override
    public TokenPricesDTO getTokensByClient(final String walletHash, final ObjectId clientId) throws Exception {
        Assert.notNull(walletHash, "Wallet hash can not be null");
        Assert.notNull(clientId, "Client Id can not be null");
        final UserEntity userEntity = userRepository.findById(clientId).orElseThrow(() -> new IllegalStateException("User not found"));
        final Long tokenCount = tokenManagementBlockchainRepository.getTokensByClient(walletHash, userEntity.getWalletHash());
        return getTokenPricesByTokenCount(tokenCount);
    }

    /**
     *
     * @param orderRequest
     * @return
     * @throws RepositoryException
     */
    @Override
    public OrderDetailDTO placeTokensOrder(final PlaceTokensOrderRequestDTO orderRequest) throws Exception {
        Assert.notNull(orderRequest.getWalletHash(), "Wallet Hash can not be null");
        Assert.notNull(orderRequest.getTokens(), "Token Count can not be null");
        log.debug("placeTokensOrder, walletHash: " + orderRequest.getWalletHash() + " , tokens: " + orderRequest.getTokens() + " CALLED!");
        final UserEntity userEntity = userRepository.findOneByWalletHash(orderRequest.getWalletHash()).orElseThrow(() -> new IllegalStateException("User not found"));
        final Long tokens = orderRequest.getTokens();
        final Long tokenPriceInWeis = tokenManagementBlockchainRepository.getTokenPriceInWeis(tokens);
        final Double tokenPriceInEth = tokenPriceInWeis * ONE_WEI_IN_ETH;
        log.debug("placeTokensOrder, tokenPriceInEth: " + tokenPriceInEth + " CALLED!");
        final CryptoComparePricesDTO prices = cryptoCompareService.getEthPrices();
        final Double tokenPriceInEUR = prices.getEUR() * tokenPriceInEth;
        final Double tokenPriceInUSD = prices.getUSD() * tokenPriceInEth;
        log.debug("tokenPriceInEUR -> " + tokenPriceInEUR);
        log.debug("tokenPriceInEth -> " + tokenPriceInEth);
        final Double orderAmountEUR = tokens * tokenPriceInEUR;
        final Double orderAmountUSD = tokens * tokenPriceInUSD;
        final Long orderAmountWEI = tokens * tokenPriceInWeis;
        final CreatedOrderDTO createdOrder = paymentService.createOrder(CreateOrderRequestDTO.builder()
                .payerEmail(userEntity.getEmail())
                .payerId(userEntity.getId().toString())
                .totalAmount(orderAmountEUR)
                .returnUrl(orderRequest.getConfirmOrderUri())
                .build());
        final CreatedOrderEntity createdOrderEntity = CreatedOrderEntity
                .builder()
                .externalOrderId(createdOrder.getOrderId())
                .createdAt(new Date())
                .tokenPriceEUR(tokenPriceInEUR)
                .tokenPriceUSD(tokenPriceInUSD)
                .amountEUR(orderAmountEUR)
                .amountUSD(orderAmountUSD)
                .amountWEI(orderAmountWEI)
                .securityToken(createdOrder.getSecurityToken())
                .tokens(tokens)
                .user(userEntity)
                .approvalLink(createdOrder.getApprovalLink().getPath())
                .status(CreatedOrderStateEnum.PENDING_APPROVAL)
                .build();
        final CreatedOrderEntity createdOrderEntitySaved = createdOrderRepository.save(createdOrderEntity);
        return createdOrderMapper.entityToDTO(createdOrderEntitySaved);
    }

    /**
     *
     * @param externalOrderId
     * @param securityToken
     * @return
     * @throws Exception
     */
    @Override
    public OrderDetailDTO confirmOrder(final String externalOrderId, final String securityToken) throws Exception {
        Assert.notNull(externalOrderId, "External Order id can not be null");
        Assert.notNull(securityToken, "Security Token can not be null");
        final CreatedOrderEntity createdOrderEntity = createdOrderRepository.findByExternalOrderIdAndSecurityToken(externalOrderId, securityToken).orElseThrow(() -> new IllegalStateException("Order not found"));
        paymentService.captureOrder(createdOrderEntity.getExternalOrderId());
        createdOrderEntity.setStatus(CreatedOrderStateEnum.APPROVED);
        createdOrderEntity.setApprovedAt(new Date());
        createdOrderEntity.setSecurityToken(null);
        streamBridge.send(streamChannelsProperties.getNewTokensOrderApproved(), new NewTokensOrderApprovedEvent(createdOrderEntity.getId()));
        final CreatedOrderEntity createdOrderEntitySaved = createdOrderRepository.save(createdOrderEntity);
        return createdOrderMapper.entityToDTO(createdOrderEntitySaved);
    }


    /**
     * Private Method
     */

    private TokenPricesDTO getTokenPricesByTokenCount(final Long tokenCount) throws RepositoryException {
        final Long tokenPriceInWeis = tokenManagementBlockchainRepository.getTokenPriceInWeis(tokenCount);
        final Double tokenPriceInEth = tokenPriceInWeis * ONE_WEI_IN_ETH;
        final CryptoComparePricesDTO prices = cryptoCompareService.getEthPrices();
        final Double tokenPriceInEUR = prices.getEUR() * tokenPriceInEth;
        final Double tokenPriceInUSD = prices.getUSD() * tokenPriceInEth;
        return TokenPricesDTO
                .builder()
                .tokenCount(tokenCount)
                .tokenPriceInEUR(tokenPriceInEUR)
                .tokenPriceInUSD(tokenPriceInUSD)
                .build();
    }

}
