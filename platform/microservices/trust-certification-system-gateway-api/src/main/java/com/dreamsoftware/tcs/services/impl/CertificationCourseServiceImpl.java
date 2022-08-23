package com.dreamsoftware.tcs.services.impl;

import com.dreamsoftware.tcs.config.properties.StreamChannelsProperties;
import com.dreamsoftware.tcs.mapper.CertificationCourseDetailMapper;
import com.dreamsoftware.tcs.model.events.CourseCertificateRegistrationRequestEvent;
import com.dreamsoftware.tcs.persistence.bc.repository.ICertificationCourseBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationCourseModelEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseRepository;
import com.dreamsoftware.tcs.services.ICertificationCourseService;
import com.dreamsoftware.tcs.web.dto.request.SaveCertificationCourseDTO;
import com.dreamsoftware.tcs.web.dto.response.CertificationCourseDetailDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Certification Course Service
 *
 * @author ssanchez
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CertificationCourseServiceImpl implements ICertificationCourseService {

    private final CertificationCourseDetailMapper certificationCourseDetailMapper;
    private final ICertificationCourseBlockchainRepository certificationCourseBlockchainRepository;
    private final CertificationCourseRepository certificationCourseRepository;
    private final StreamBridge streamBridge;
    private final StreamChannelsProperties streamChannelsProperties;

    /**
     * Enable Certification Course
     *
     * @param caWalletHash
     * @param courseId
     * @return
     * @throws Throwable
     */
    @Override
    public CertificationCourseDetailDTO enable(final String caWalletHash, final String courseId) throws Throwable {
        Assert.notNull(caWalletHash, "CA wallet can not be null");
        Assert.notNull(courseId, "Course ID can not be null");
        log.debug("Enable course -> " + courseId + " CALLED!");
        final CertificationCourseModelEntity certificationCourseEntity = certificationCourseBlockchainRepository.enable(caWalletHash, courseId);
        certificationCourseRepository.updateStatus(courseId, CertificationCourseStateEnum.ENABLED);
        return certificationCourseDetailMapper.entityToDTO(certificationCourseEntity);
    }

    /**
     * Disable Certification Course
     *
     * @param caWalletHash
     * @param courseId
     * @return
     * @throws Throwable
     */
    @Override
    public CertificationCourseDetailDTO disable(final String caWalletHash, final String courseId) throws Throwable {
        Assert.notNull(caWalletHash, "CA wallet can not be null");
        Assert.notNull(courseId, "Course ID can not be null");
        log.debug("Disable course -> " + courseId + " CALLED!");
        final CertificationCourseModelEntity certificationCourseEntity = certificationCourseBlockchainRepository.disable(caWalletHash, courseId);
        certificationCourseRepository.updateStatus(courseId, CertificationCourseStateEnum.DISABLED);
        return certificationCourseDetailMapper.entityToDTO(certificationCourseEntity);
    }

    /**
     * Save Certification Course
     *
     * @param model
     */
    @Override
    public void save(SaveCertificationCourseDTO model) {
        Assert.notNull(model, "model can not be null");
        final CourseCertificateRegistrationRequestEvent event = new CourseCertificateRegistrationRequestEvent(
                model.getName(), model.getCostOfIssuingCertificate(),
                model.getDurationInHours(), model.getExpirationInDays(),
                model.getCanBeRenewed(), model.getCostOfRenewingCertificate(),
                model.getCaWalletHash());
        streamBridge.send(streamChannelsProperties.getCertificationCourseRegistration(), event);
    }

    /**
     *
     * @param caWalletHash
     * @param courseId
     */
    @Override
    public CertificationCourseDetailDTO remove(String caWalletHash, String courseId) throws Throwable {
        Assert.notNull(caWalletHash, "CA wallet can not be null");
        Assert.notNull(courseId, "Course ID can not be null");
        log.debug("remove certification course " + courseId + " CALLED!");
        final CertificationCourseModelEntity certificationCourseEntity = certificationCourseBlockchainRepository.remove(caWalletHash, courseId);
        certificationCourseRepository.updateStatus(courseId, CertificationCourseStateEnum.REMOVED);
        return certificationCourseDetailMapper.entityToDTO(certificationCourseEntity);
    }

    /**
     *
     * @param caWalletHash
     * @param courseId
     * @return
     * @throws Throwable
     */
    @Override
    public Boolean canBeIssued(String caWalletHash, String courseId) throws Throwable {
        Assert.notNull(caWalletHash, "CA wallet can not be null");
        Assert.notNull(courseId, "Course ID can not be null");
        log.debug("check certification course " + courseId + " can be issued CALLED!");
        return certificationCourseBlockchainRepository.canBeIssued(caWalletHash, courseId);
    }

    /**
     *
     * @param caWalletHash
     * @param courseId
     * @return
     * @throws Throwable
     */
    @Override
    public Boolean canBeRenewed(String caWalletHash, String courseId) throws Throwable {
        Assert.notNull(caWalletHash, "CA wallet can not be null");
        Assert.notNull(courseId, "Course ID can not be null");
        log.debug("check certification course " + courseId + " can be renewed CALLED!");
        return certificationCourseBlockchainRepository.canBeRenewed(caWalletHash, courseId);
    }

    /**
     * Get Detail
     *
     * @param caWalletHash
     * @param courseId
     * @return
     */
    @Override
    public CertificationCourseDetailDTO getDetail(String caWalletHash, String courseId) throws Throwable {
        Assert.notNull(caWalletHash, "CA wallet can not be null");
        Assert.notNull(courseId, "Course ID can not be null");
        log.debug("get certification course " + courseId + " detail CALLED!");
        final CertificationCourseModelEntity certificationCourseEntity = certificationCourseBlockchainRepository.get(caWalletHash, courseId);
        return certificationCourseDetailMapper.entityToDTO(certificationCourseEntity);
    }
}
