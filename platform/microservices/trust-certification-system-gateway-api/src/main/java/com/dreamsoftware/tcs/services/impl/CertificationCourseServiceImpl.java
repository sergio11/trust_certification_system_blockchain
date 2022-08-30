package com.dreamsoftware.tcs.services.impl;

import com.dreamsoftware.tcs.config.properties.StreamChannelsProperties;
import com.dreamsoftware.tcs.mapper.CertificationCourseDetailMapper;
import com.dreamsoftware.tcs.mapper.UpdateCertificationCourselMapper;
import com.dreamsoftware.tcs.stream.events.course.CourseCertificateRegistrationRequestEvent;
import com.dreamsoftware.tcs.persistence.bc.repository.ICertificationCourseBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationCourseModelEntity;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseRepository;
import com.dreamsoftware.tcs.services.ICertificationCourseService;
import com.dreamsoftware.tcs.stream.events.notifications.course.CourseDeletedNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.course.CourseDisabledNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.course.CourseEnabledNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.course.NewCourseRegistrationRequestedNotificationEvent;
import com.dreamsoftware.tcs.utils.Unthrow;
import com.dreamsoftware.tcs.web.dto.request.SaveCertificationCourseDTO;
import com.dreamsoftware.tcs.web.dto.response.CertificationCourseDetailDTO;
import com.dreamsoftware.tcs.web.dto.response.UpdateCertificationCourseDTO;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Certification Course Service
 *
 * @author ssanchez
 */
@Slf4j
@Service("certificationCourseService")
@RequiredArgsConstructor
public class CertificationCourseServiceImpl implements ICertificationCourseService {

    private final CertificationCourseDetailMapper certificationCourseDetailMapper;
    private final UpdateCertificationCourselMapper updateCertificationCourselMapper;
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
        final CertificationCourseDetailDTO certificationCourseDetailDTO = certificationCourseDetailMapper.entityToDTO(certificationCourseEntity);
        streamBridge.send(streamChannelsProperties.getNotificationDeliveryRequest(), CourseEnabledNotificationEvent.builder()
                .id(certificationCourseDetailDTO.getId())
                .name(certificationCourseDetailDTO.getName())
                .build());
        return certificationCourseDetailDTO;
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
        final CertificationCourseDetailDTO certificationCourseDetailDTO = certificationCourseDetailMapper.entityToDTO(certificationCourseEntity);
        streamBridge.send(streamChannelsProperties.getNotificationDeliveryRequest(), CourseDisabledNotificationEvent.builder()
                .id(certificationCourseDetailDTO.getId())
                .name(certificationCourseDetailDTO.getName())
                .build());
        return certificationCourseDetailDTO;
    }

    /**
     * Save Certification Course
     *
     * @param model
     */
    @Override
    public void save(final SaveCertificationCourseDTO model) {
        Assert.notNull(model, "model can not be null");
        streamBridge.send(streamChannelsProperties.getCertificationCourseRegistration(), CourseCertificateRegistrationRequestEvent.builder()
                .name(model.getName())
                .costOfIssuingCertificate(model.getCostOfIssuingCertificate())
                .caWalletHash(model.getCaWalletHash())
                .durationInHours(model.getDurationInHours())
                .expirationInDays(model.getExpirationInDays())
                .canBeRenewed(model.getCanBeRenewed())
                .costOfRenewingCertificate(model.getCostOfRenewingCertificate())
                .build());
        streamBridge.send(streamChannelsProperties.getNotificationDeliveryRequest(), NewCourseRegistrationRequestedNotificationEvent.builder()
                .caWalletHash(model.getCaWalletHash())
                .courseName(model.getName())
                .build());
    }

    /**
     *
     * @param caWalletHash
     * @param courseId
     */
    @Override
    public CertificationCourseDetailDTO remove(final String caWalletHash, final String courseId) throws Throwable {
        Assert.notNull(caWalletHash, "CA wallet can not be null");
        Assert.notNull(courseId, "Course ID can not be null");
        log.debug("remove certification course " + courseId + " CALLED!");
        final CertificationCourseModelEntity certificationCourseEntity = certificationCourseBlockchainRepository.remove(caWalletHash, courseId);
        certificationCourseRepository.updateStatus(courseId, CertificationCourseStateEnum.REMOVED);
        final CertificationCourseDetailDTO certificationCourseDetailDTO = certificationCourseDetailMapper.entityToDTO(certificationCourseEntity);
        streamBridge.send(streamChannelsProperties.getNotificationDeliveryRequest(), CourseDeletedNotificationEvent.builder()
                .id(certificationCourseDetailDTO.getId())
                .name(certificationCourseDetailDTO.getName())
                .build());
        return certificationCourseDetailDTO;
    }

    /**
     *
     * @param caWalletHash
     * @param courseId
     * @return
     * @throws Throwable
     */
    @Override
    public Boolean canBeIssued(final String caWalletHash, final String courseId) throws Throwable {
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
    public Boolean canBeRenewed(final String caWalletHash, final String courseId) throws Throwable {
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
    public CertificationCourseDetailDTO getDetail(final String caWalletHash, final String courseId) throws Throwable {
        Assert.notNull(caWalletHash, "CA wallet can not be null");
        Assert.notNull(courseId, "Course ID can not be null");
        log.debug("get certification course " + courseId + " detail CALLED!");
        final CertificationCourseModelEntity certificationCourseEntity = certificationCourseBlockchainRepository.get(caWalletHash, courseId);
        return certificationCourseDetailMapper.entityToDTO(certificationCourseEntity);
    }

    /**
     *
     * @param courseId
     * @param userId
     * @return
     */
    @Override
    public Boolean isTheOwner(final String courseId, final ObjectId userId) {
        Assert.notNull(courseId, "Id can not be null");
        Assert.notNull(userId, "Id can not be null");
        return certificationCourseRepository.countByIdAndCaId(new ObjectId(courseId), userId) > 0;
    }

    /**
     *
     * @param courseId
     * @return
     */
    @Override
    public Optional<UpdateCertificationCourseDTO> editById(final String courseId) {
        Assert.notNull(courseId, "Id can not be null");
        try {
            return Optional.ofNullable(certificationCourseBlockchainRepository.get(courseId))
                    .map(updateCertificationCourselMapper::entityToDTO);
        } catch (final RepositoryException e) {
            log.debug("editById RepositoryException -> " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     *
     * @param courseId
     * @param model
     * @param caWalletHash
     * @return
     */
    @Override
    public Optional<CertificationCourseDetailDTO> update(final String courseId, final UpdateCertificationCourseDTO model, final String caWalletHash) {
        Assert.notNull(courseId, "Id can not be null");
        Assert.notNull(model, "Model can not be null");
        Assert.notNull(caWalletHash, "User Id can not be null");
        try {
            return Optional.ofNullable(certificationCourseBlockchainRepository.get(courseId))
                    .map(certificationCourseEntity -> updateCertificationCourselMapper.update(certificationCourseEntity, model))
                    .map(certificationCourseEntity -> Unthrow.wrap(() -> certificationCourseBlockchainRepository.update(caWalletHash, certificationCourseEntity)))
                    .map(certificationCourseDetailMapper::entityToDTO);
        } catch (final RepositoryException e) {
            log.debug("editById RepositoryException -> " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     *
     * @return @throws Throwable
     */
    @Override
    public Iterable<CertificationCourseDetailDTO> getAll() throws Throwable {
        final Iterable<CertificationCourseModelEntity> certificationCourseList = certificationCourseBlockchainRepository.getAll();
        return certificationCourseDetailMapper.certificationCourseModelEntityToDTO(certificationCourseList);
    }

    /**
     *
     * @param caWalletHash
     * @return
     * @throws Throwable
     */
    @Override
    public Iterable<CertificationCourseDetailDTO> getAllByCA(final String caWalletHash) throws Throwable {
        Assert.notNull(caWalletHash, "CA wallet hash");
        final Iterable<CertificationCourseModelEntity> certificationCourseList = certificationCourseBlockchainRepository.getAllByCa(caWalletHash);
        return certificationCourseDetailMapper.certificationCourseModelEntityToDTO(certificationCourseList);
    }
}
