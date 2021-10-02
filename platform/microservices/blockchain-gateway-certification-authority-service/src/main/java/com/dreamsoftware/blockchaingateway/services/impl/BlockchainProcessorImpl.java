package com.dreamsoftware.blockchaingateway.services.impl;

import com.dreamsoftware.blockchaingateway.model.CertificationAuthorityInitialFundsRequestEvent;
import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.UserTypeEnum;
import com.dreamsoftware.blockchaingateway.persistence.nosql.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import com.dreamsoftware.blockchaingateway.services.IBlockchainProcessor;
import org.bson.types.ObjectId;
import org.springframework.util.Assert;

/**
 *
 * @author ssanchez
 */
@Service
@RequiredArgsConstructor
public class BlockchainProcessorImpl implements IBlockchainProcessor {

    private final Logger logger = LoggerFactory.getLogger(BlockchainProcessorImpl.class);

    private final UserRepository userRepository;
    private final StreamBridge streamBridge;

    @Override
    public void onUserActivated(String id) {
        Assert.notNull(id, "Id can not be null");
        Assert.hasLength(id, "Id can not be empty");
        Assert.isTrue(ObjectId.isValid(id), "Id is invalid");

        final UserEntity userEntity = userRepository.findById(new ObjectId(id)).orElseThrow(() -> {
            throw new IllegalStateException("User not found");
        });

        if (userEntity.getType().equals(UserTypeEnum.CA)) {
            publish(new CertificationAuthorityInitialFundsRequestEvent(userEntity.getName(), userEntity.getWalletHash()));
        }

    }

    /**
     * Private Methods
     */
    /**
     *
     * @param <T>
     * @param event
     */
    private <T> void publish(T event) {
        logger.debug("publish CALLED!");
        streamBridge.send("eventsChannel-out-0", event);
    }
}
