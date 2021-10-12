package com.dreamsoftware.blockchaingateway.services.impl;

import com.dreamsoftware.blockchaingateway.mapper.CertificationAuthorityDetailMapper;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.ICertificationAuthorityBlockchainRepository;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.CertificationAuthorityEntity;
import com.dreamsoftware.blockchaingateway.persistence.exception.RepositoryException;
import com.dreamsoftware.blockchaingateway.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.blockchaingateway.services.ICertificationAuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dreamsoftware.blockchaingateway.web.controller.ca.error.exception.GetCertificationAuthorityException;
import com.dreamsoftware.blockchaingateway.web.dto.response.CertificationAuthorityDetailDTO;
import javax.annotation.PostConstruct;
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

    /**
     * Get Detail
     *
     * @param id
     * @return
     */
    @Override
    public CertificationAuthorityDetailDTO getDetail(String id) throws Throwable {
        Assert.notNull(id, "Id can not be null");
        try {
            final UserEntity caUserEntity = certificationAuthorityRepository.findById(new ObjectId(id))
                    .orElseThrow(() -> {
                        throw new IllegalStateException("User Not Found");
                    });
            final CertificationAuthorityEntity caEntity = caBlockchainRepository.getDetail(caUserEntity.getWalletHash());
            return certificationAuthorityDetailMapper.caEntityToCaDetail(caEntity, caUserEntity);
        } catch (final RepositoryException ex) {
            logger.error("exception ocurred " + ex.getMessage() + " CALLED");
            throw new GetCertificationAuthorityException(ex.getMessage(), ex);
        }
    }

    @PostConstruct
    private void onPostConstruct() {
        Assert.notNull(certificationAuthorityRepository, "Certification Authority Repository can not be null");
    }
}
