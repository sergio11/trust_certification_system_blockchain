package com.dreamsoftware.blockchaingateway.services.impl;

import com.dreamsoftware.blockchaingateway.config.properties.TrustCertificationSystemProperties;
import com.dreamsoftware.blockchaingateway.mapper.CertificationAuthorityDetailMapper;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.ICertificationAuthorityBlockchainRepository;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.CertificationAuthorityEntity;
import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.blockchaingateway.services.ICertificationAuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dreamsoftware.blockchaingateway.web.dto.response.CertificationAuthorityDetailDTO;
import org.springframework.util.Assert;
import com.dreamsoftware.blockchaingateway.persistence.nosql.repository.UserRepository;
import org.bson.types.ObjectId;

/**
 *
 * @author ssanchez
 */
@Service
@RequiredArgsConstructor
public class CertificationAuthorityServiceImpl implements ICertificationAuthorityService {

    private final Logger logger = LoggerFactory.getLogger(CertificationAuthorityServiceImpl.class);

    /**
     * Certification Authority Repository
     */
    private final UserRepository certificationAuthorityRepository;
    private final ICertificationAuthorityBlockchainRepository caBlockchainRepository;
    private final CertificationAuthorityDetailMapper certificationAuthorityDetailMapper;
    private final TrustCertificationSystemProperties trustCertificationSystemProperties;

    /**
     * Get Detail
     *
     * @param id
     * @return
     */
    @Override
    public CertificationAuthorityDetailDTO getDetail(String id) throws Throwable {
        Assert.notNull(id, "Id can not be null");
        logger.debug("Get Certification Authority Detail, id: " + id);
        final UserEntity caUserEntity = certificationAuthorityRepository.findById(new ObjectId(id))
                .orElseThrow(() -> {
                    throw new IllegalStateException("User Not Found");
                });
        final CertificationAuthorityEntity caEntity = caBlockchainRepository.getDetail(caUserEntity.getWalletHash());
        return certificationAuthorityDetailMapper.caEntityToCaDetail(caEntity, caUserEntity);
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
        logger.debug("Enable Certification Authority, id: " + id);
        final UserEntity caUserEntity = certificationAuthorityRepository.findById(new ObjectId(id))
                .orElseThrow(() -> {
                    throw new IllegalStateException("User Not Found");
                });
        final CertificationAuthorityEntity caEntity = caBlockchainRepository.enable(trustCertificationSystemProperties.getRootPublicKey(),
                caUserEntity.getWalletHash());
        return certificationAuthorityDetailMapper.caEntityToCaDetail(caEntity, caUserEntity);
    }

    /**
     * Disable Certification Authority
     *
     * @param id
     * @return
     * @throws Throwable
     */
    @Override
    public CertificationAuthorityDetailDTO disable(String id) throws Throwable {
        Assert.notNull(id, "Id can not be null");
        logger.debug("Disable Certification Authority, id: " + id);
        final UserEntity caUserEntity = certificationAuthorityRepository.findById(new ObjectId(id))
                .orElseThrow(() -> {
                    throw new IllegalStateException("User Not Found");
                });
        final CertificationAuthorityEntity caEntity = caBlockchainRepository.disable(trustCertificationSystemProperties.getRootPublicKey(),
                caUserEntity.getWalletHash());
        return certificationAuthorityDetailMapper.caEntityToCaDetail(caEntity, caUserEntity);
    }
}
