package com.dreamsoftware.blockchaingateway.services.impl;

import com.dreamsoftware.blockchaingateway.services.ICertificationAuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dreamsoftware.blockchaingateway.web.controller.error.exception.GetCertificationAuthorityException;
import com.dreamsoftware.blockchaingateway.web.dto.response.CertificationAuthorityDetailDTO;
import javax.annotation.PostConstruct;
import org.springframework.util.Assert;
import com.dreamsoftware.blockchaingateway.persistence.nosql.repository.UserRepository;

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

    /**
     * Get Detail
     *
     * @param id
     * @return
     */
    @Override
    public CertificationAuthorityDetailDTO getDetail(String id) {
        Assert.notNull(id, "Id can not be null");
        try {
            //final UserEntity certificationAuthority = certificationAuthorityRepository.findById(new ObjectId(id));
            return null;
        } catch (final Exception ex) {
            logger.error("exception ocurred " + ex.getMessage() + " CALLED");
            throw new GetCertificationAuthorityException(ex.getMessage(), ex);
        }
    }

    @PostConstruct
    private void onPostConstruct() {
        Assert.notNull(certificationAuthorityRepository, "Certification Authority Repository can not be null");
    }
}
