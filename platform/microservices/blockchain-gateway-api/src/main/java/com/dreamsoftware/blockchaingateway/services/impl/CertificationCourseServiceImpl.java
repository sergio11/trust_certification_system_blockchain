package com.dreamsoftware.blockchaingateway.services.impl;

import com.dreamsoftware.blockchaingateway.mapper.CertificationCourseDetailMapper;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.ICertificationCourseBlockchainRepository;
import com.dreamsoftware.blockchaingateway.persistence.bc.repository.entity.CertificationCourseEntity;
import com.dreamsoftware.blockchaingateway.services.IBlockchainProcessor;
import com.dreamsoftware.blockchaingateway.services.ICertificationCourseService;
import com.dreamsoftware.blockchaingateway.web.dto.request.SaveCertificationCourseDTO;
import com.dreamsoftware.blockchaingateway.web.dto.response.CertificationCourseDetailDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Certification Course Service
 *
 * @author ssanchez
 */
@Service
@RequiredArgsConstructor
public class CertificationCourseServiceImpl implements ICertificationCourseService {

    private final Logger logger = LoggerFactory.getLogger(CertificationCourseServiceImpl.class);

    private final IBlockchainProcessor blockchainProcessor;
    private final CertificationCourseDetailMapper certificationCourseDetailMapper;
    private final ICertificationCourseBlockchainRepository certificationCourseBlockchainRepository;

    /**
     * Enable Certification Course
     *
     * @param caWallet
     * @param courseId
     * @return
     * @throws Throwable
     */
    @Override
    public CertificationCourseDetailDTO enable(final String caWallet, final String courseId) throws Throwable {
        Assert.notNull(caWallet, "CA wallet can not be null");
        Assert.notNull(courseId, "Course ID can not be null");
        final CertificationCourseEntity certificationCourseEntity = certificationCourseBlockchainRepository.enable(caWallet, courseId);
        return certificationCourseDetailMapper.certificationCourseEntityToCertificationCourseDetail(certificationCourseEntity);
    }

    /**
     * Disable Certification Course
     *
     * @param caWallet
     * @param courseId
     * @return
     * @throws Throwable
     */
    @Override
    public CertificationCourseDetailDTO disable(final String caWallet, final String courseId) throws Throwable {
        Assert.notNull(caWallet, "CA wallet can not be null");
        Assert.notNull(courseId, "Course ID can not be null");
        final CertificationCourseEntity certificationCourseEntity = certificationCourseBlockchainRepository.disable(caWallet, courseId);
        return certificationCourseDetailMapper.certificationCourseEntityToCertificationCourseDetail(certificationCourseEntity);
    }

    /**
     * Save Certification Course
     *
     * @param model
     */
    @Override
    public void save(SaveCertificationCourseDTO model) {
        Assert.notNull(model, "model can not be null");
        blockchainProcessor.onRegisterCertificationCourse(model);
    }

    /**
     *
     * @param caWallet
     * @param courseId
     */
    @Override
    public CertificationCourseDetailDTO remove(String caWallet, String courseId) throws Throwable {
        Assert.notNull(caWallet, "CA wallet can not be null");
        Assert.notNull(courseId, "Course ID can not be null");
        final CertificationCourseEntity certificationCourseEntity = certificationCourseBlockchainRepository.remove(caWallet, courseId);
        return certificationCourseDetailMapper.certificationCourseEntityToCertificationCourseDetail(certificationCourseEntity);
    }

    /**
     *
     * @param caWallet
     * @param courseId
     * @return
     * @throws Throwable
     */
    @Override
    public Boolean canBeIssued(String caWallet, String courseId) throws Throwable {
        Assert.notNull(caWallet, "CA wallet can not be null");
        Assert.notNull(courseId, "Course ID can not be null");
        return certificationCourseBlockchainRepository.canBeIssued(caWallet, courseId);
    }

    /**
     *
     * @param caWallet
     * @param courseId
     * @return
     * @throws Throwable
     */
    @Override
    public Boolean canBeRenewed(String caWallet, String courseId) throws Throwable {
        Assert.notNull(caWallet, "CA wallet can not be null");
        Assert.notNull(courseId, "Course ID can not be null");
        return certificationCourseBlockchainRepository.canBeRenewed(caWallet, courseId);
    }

    /**
     * Get Detail
     *
     * @param caWallet
     * @param courseId
     * @return
     */
    @Override
    public CertificationCourseDetailDTO getDetail(String caWallet, String courseId) throws Throwable {
        Assert.notNull(caWallet, "CA wallet can not be null");
        Assert.notNull(courseId, "Course ID can not be null");
        final CertificationCourseEntity certificationCourseEntity = certificationCourseBlockchainRepository.get(caWallet, courseId);
        return certificationCourseDetailMapper.certificationCourseEntityToCertificationCourseDetail(certificationCourseEntity);
    }
}
