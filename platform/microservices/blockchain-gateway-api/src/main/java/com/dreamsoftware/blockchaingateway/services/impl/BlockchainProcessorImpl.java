package com.dreamsoftware.blockchaingateway.services.impl;

import com.dreamsoftware.blockchaingateway.config.properties.StreamChannelsProperties;
import com.dreamsoftware.blockchaingateway.model.CourseCertificateRegistrationRequestEvent;
import com.dreamsoftware.blockchaingateway.model.OnNewUserRegistrationEvent;
import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.blockchaingateway.persistence.nosql.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import com.dreamsoftware.blockchaingateway.services.IBlockchainProcessor;
import com.dreamsoftware.blockchaingateway.web.dto.request.SaveCertificationCourseDTO;
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
    private final StreamChannelsProperties streamChannelsProperties;

    @Override
    public void onUserActivated(String id) {
        Assert.notNull(id, "Id can not be null");
        Assert.hasLength(id, "Id can not be empty");
        Assert.isTrue(ObjectId.isValid(id), "Id is invalid");

        try {
            final UserEntity userEntity = userRepository.findById(new ObjectId(id)).orElseThrow(() -> {
                throw new IllegalStateException("User not found");
            });
            publish(new OnNewUserRegistrationEvent(userEntity.getName(), userEntity.getWalletHash(), userEntity.getType()),
                    streamChannelsProperties.getNewUserRegistration());
        } catch (final Throwable ex) {
            logger.debug("onUserActivated FAILED! " + ex.getMessage());
        }

    }

    /**
     * On Register Certification Course
     *
     * @param certificationCourseDTO
     */
    @Override
    public void onRegisterCertificationCourse(final SaveCertificationCourseDTO certificationCourseDTO) {
        Assert.notNull(certificationCourseDTO, "Certification Course DTO can not be null");
        logger.debug("onRegisterCertificationCourse CALLED!");
        try {
            final CourseCertificateRegistrationRequestEvent event = new CourseCertificateRegistrationRequestEvent(
                    certificationCourseDTO.getName(), certificationCourseDTO.getCostOfIssuingCertificate(),
                    certificationCourseDTO.getDurationInHours(), certificationCourseDTO.getExpirationInDays(),
                    certificationCourseDTO.getCanBeRenewed(), certificationCourseDTO.getCostOfRenewingCertificate(),
                    certificationCourseDTO.getCaWallet());
            publish(event, streamChannelsProperties.getCertificationCourseRegistration());
        } catch (final Throwable ex) {
            logger.debug("onRegisterCertificationCourse FAILED! " + ex.getMessage());
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
    private <T> void publish(T event, final String channelName) {
        logger.debug("publish CALLED!");
        streamBridge.send(channelName, event);
    }

}
