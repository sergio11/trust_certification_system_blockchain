package com.dreamsoftware.tcs.services.impl;

import com.dreamsoftware.tcs.config.properties.EthereumProperties;
import com.dreamsoftware.tcs.mapper.CertificationAuthorityDetailMapper;
import com.dreamsoftware.tcs.mapper.UpdateCertificationAuthorityMapper;
import com.dreamsoftware.tcs.persistence.bc.repository.ICertificationAuthorityBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationAuthorityEntity;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.services.ICertificationAuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.dreamsoftware.tcs.web.dto.response.CertificationAuthorityDetailDTO;
import org.springframework.util.Assert;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.stream.events.notifications.ca.CADisabledNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.ca.CAEnabledNotificationEvent;
import com.dreamsoftware.tcs.config.properties.StreamChannelsProperties;
import com.dreamsoftware.tcs.utils.Unthrow;
import com.dreamsoftware.tcs.web.dto.response.UpdateCertificationAuthorityDTO;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.cloud.stream.function.StreamBridge;

/**
 *
 * @author ssanchez
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CertificationAuthorityServiceImpl implements ICertificationAuthorityService {

    private final UserRepository certificationAuthorityRepository;
    private final ICertificationAuthorityBlockchainRepository caBlockchainRepository;
    private final CertificationAuthorityDetailMapper certificationAuthorityDetailMapper;
    private final EthereumProperties trustCertificationSystemProperties;
    private final UpdateCertificationAuthorityMapper updateCertificationAuthorityMapper;
    private final StreamChannelsProperties streamChannelsProperties;
    private final StreamBridge streamBridge;

    /**
     * Get Detail
     *
     * @param id
     * @return
     */
    @Override
    public CertificationAuthorityDetailDTO getDetail(final String id) throws Throwable {
        Assert.notNull(id, "Id can not be null");
        log.debug("Get Certification Authority Detail, id: " + id);
        final UserEntity caUserEntity = certificationAuthorityRepository.findById(new ObjectId(id))
                .orElseThrow(() -> {
                    throw new IllegalStateException("User Not Found");
                });
        final CertificationAuthorityEntity caEntity = caBlockchainRepository.getDetail(caUserEntity.getWalletHash());
        return certificationAuthorityDetailMapper.entityToDTO(caEntity, caUserEntity);
    }

    /**
     * Enable Certification Authority
     *
     * @param id
     * @return
     * @throws Throwable
     */
    @Override
    public CertificationAuthorityDetailDTO enable(final String id) throws Throwable {
        Assert.notNull(id, "Id can not be null");
        log.debug("Enable Certification Authority, id: " + id);
        final UserEntity caUserEntity = certificationAuthorityRepository.findById(new ObjectId(id))
                .orElseThrow(() -> {
                    throw new IllegalStateException("User Not Found");
                });
        final CertificationAuthorityEntity caEntity = caBlockchainRepository.enable(trustCertificationSystemProperties.getRootPublicKey(),
                caUserEntity.getWalletHash());
        final CertificationAuthorityDetailDTO certificationAuthorityDetailDTO = certificationAuthorityDetailMapper.entityToDTO(caEntity, caUserEntity);
        streamBridge.send(streamChannelsProperties.getNotificationDeliveryRequest(), CAEnabledNotificationEvent.builder()
                .walletHash(certificationAuthorityDetailDTO.getWalletHash())
                .build());
        return certificationAuthorityDetailDTO;
    }

    /**
     * Disable Certification Authority
     *
     * @param id
     * @return
     * @throws Throwable
     */
    @Override
    public CertificationAuthorityDetailDTO disable(final String id) throws Throwable {
        Assert.notNull(id, "Id can not be null");
        log.debug("Disable Certification Authority, id: " + id);
        final UserEntity caUserEntity = certificationAuthorityRepository.findById(new ObjectId(id))
                .orElseThrow(() -> {
                    throw new IllegalStateException("User Not Found");
                });
        final CertificationAuthorityEntity caEntity = caBlockchainRepository.disable(trustCertificationSystemProperties.getRootPublicKey(),
                caUserEntity.getWalletHash());
        final CertificationAuthorityDetailDTO certificationAuthorityDetailDTO = certificationAuthorityDetailMapper.entityToDTO(caEntity, caUserEntity);
        streamBridge.send(streamChannelsProperties.getNotificationDeliveryRequest(), CADisabledNotificationEvent.builder()
                .walletHash(certificationAuthorityDetailDTO.getWalletHash())
                .build());
        return certificationAuthorityDetailDTO;
    }

    /**
     * Edit by wallet hash
     *
     * @param walletHash
     * @return
     */
    @Override
    public Optional<UpdateCertificationAuthorityDTO> editByWalletHash(final String walletHash) {
        Assert.notNull(walletHash, "walletHash can not be null");
        log.debug("editByWalletHash, walletHash: " + walletHash);
        try {
            return Optional.ofNullable(caBlockchainRepository.getDetail(walletHash))
                    .map(updateCertificationAuthorityMapper::entityToDTO);
        } catch (final RepositoryException e) {
            log.debug("editByWalletHash RepositoryException -> " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     *
     * @param walletHash
     * @param model
     * @return
     */
    @Override
    public Optional<CertificationAuthorityDetailDTO> update(final String walletHash, final UpdateCertificationAuthorityDTO model) {
        Assert.notNull(walletHash, "walletHash can not be null");
        Assert.notNull(model, "model can not be null");
        try {
            return Optional.ofNullable(caBlockchainRepository.getDetail(walletHash))
                    .map(certificationAuthorityEntity -> updateCertificationAuthorityMapper.update(certificationAuthorityEntity, model))
                    .map(certificationAuthorityEntity -> Unthrow.wrap(() -> caBlockchainRepository.update(walletHash, certificationAuthorityEntity)))
                    .map(certificationAuthorityEntity -> certificationAuthorityRepository.findOneByWalletHash(walletHash)
                    .map(caUserEntity -> certificationAuthorityDetailMapper.entityToDTO(certificationAuthorityEntity, caUserEntity))
                    .orElseThrow(IllegalStateException::new));
        } catch (final RepositoryException e) {
            log.debug("update RepositoryException -> " + e.getMessage());
            return Optional.empty();
        }
    }
}
