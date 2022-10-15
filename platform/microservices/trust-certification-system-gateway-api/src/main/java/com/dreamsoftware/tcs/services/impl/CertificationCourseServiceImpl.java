package com.dreamsoftware.tcs.services.impl;

import com.dreamsoftware.tcs.config.properties.StreamChannelsProperties;
import com.dreamsoftware.tcs.mapper.CertificationCourseDetailMapper;
import com.dreamsoftware.tcs.mapper.SimpleCertificationCourseDetailMapper;
import com.dreamsoftware.tcs.mapper.UpdateCertificationCourseMapper;
import com.dreamsoftware.tcs.persistence.bc.repository.ICertificationCourseBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationCourseBcEntity;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseRepository;
import com.dreamsoftware.tcs.services.ICertificationCourseService;
import com.dreamsoftware.tcs.stream.events.course.CourseCertificateRegistrationRequestEvent;
import com.dreamsoftware.tcs.stream.events.course.DisableCertificationCourseEvent;
import com.dreamsoftware.tcs.stream.events.course.EnableCertificationCourseEvent;
import com.dreamsoftware.tcs.stream.events.course.RemoveCertificationCourseEvent;
import com.dreamsoftware.tcs.stream.events.notifications.course.NewCourseRegistrationRequestedNotificationEvent;
import com.dreamsoftware.tcs.utils.Unthrow;
import com.dreamsoftware.tcs.web.dto.request.SaveCertificationCourseDTO;
import com.dreamsoftware.tcs.web.dto.response.CertificationCourseDetailDTO;
import com.dreamsoftware.tcs.web.dto.response.SimpleCertificationCourseDetailDTO;
import com.dreamsoftware.tcs.web.dto.response.UpdateCertificationCourseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.bson.types.ObjectId;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Certification Course Service
 *
 * @author ssanchez
 */
@Slf4j
@Service("certificationCourseService")
@RequiredArgsConstructor
public class CertificationCourseServiceImpl implements ICertificationCourseService {

    private final SimpleCertificationCourseDetailMapper simpleCertificationCourseDetailMapper;
    private final CertificationCourseDetailMapper certificationCourseDetailMapper;
    private final UpdateCertificationCourseMapper updateCertificationCourselMapper;
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
    public void enable(final String caWalletHash, final String courseId) throws Throwable {
        Assert.notNull(caWalletHash, "CA wallet can not be null");
        Assert.notNull(courseId, "Course ID can not be null");
        log.debug("Enable course -> " + courseId + " CALLED!");
        streamBridge.send(streamChannelsProperties.getCourseManagement(), EnableCertificationCourseEvent.builder()
                .caWalletHash(caWalletHash)
                .courseId(courseId)
                .build());
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
    public void disable(final String caWalletHash, final String courseId) throws Throwable {
        Assert.notNull(caWalletHash, "CA wallet can not be null");
        Assert.notNull(courseId, "Course ID can not be null");
        log.debug("Disable course -> " + courseId + " CALLED!");
        streamBridge.send(streamChannelsProperties.getCourseManagement(), DisableCertificationCourseEvent.builder()
                .caWalletHash(caWalletHash)
                .courseId(courseId)
                .build());
    }

    /**
     * Save Certification Course
     *
     * @param model
     */
    @Override
    public void save(final SaveCertificationCourseDTO model) {
        Assert.notNull(model, "model can not be null");
        streamBridge.send(streamChannelsProperties.getCourseManagement(), CourseCertificateRegistrationRequestEvent.builder()
                .id(model.getName())
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
     * @param caWalletHash
     * @param courseId
     */
    @Override
    public void remove(final String caWalletHash, final String courseId) throws Throwable {
        Assert.notNull(caWalletHash, "CA wallet can not be null");
        Assert.notNull(courseId, "Course ID can not be null");
        log.debug("remove certification course " + courseId + " CALLED!");
        streamBridge.send(streamChannelsProperties.getCourseManagement(), RemoveCertificationCourseEvent.builder()
                .caWalletHash(caWalletHash)
                .courseId(courseId)
                .build());
    }

    /**
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
        final CertificationCourseEntity certificationCourseEntity = certificationCourseRepository.findById(new ObjectId(courseId))
                .orElseThrow(() -> new IllegalStateException("Course not found"));
        final CertificationCourseBcEntity certificationCourseBcEntity = certificationCourseBlockchainRepository.get(caWalletHash, courseId);
        return certificationCourseDetailMapper.entityToDTO(Pair.of(certificationCourseEntity, certificationCourseBcEntity));
    }

    /**
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
     * @param courseId
     * @param model
     * @param caWalletHash
     * @return
     */
    @Override
    public Optional<SimpleCertificationCourseDetailDTO> update(final String courseId, final UpdateCertificationCourseDTO model, final String caWalletHash) {
        Assert.notNull(courseId, "Id can not be null");
        Assert.notNull(model, "Model can not be null");
        Assert.notNull(caWalletHash, "User Id can not be null");
        try {
            return Optional.ofNullable(certificationCourseBlockchainRepository.get(courseId))
                    .map(certificationCourseEntity -> updateCertificationCourselMapper.update(certificationCourseEntity, model))
                    .map(certificationCourseEntity -> Unthrow.wrap(() -> certificationCourseBlockchainRepository.update(caWalletHash, certificationCourseEntity)))
                    .map(certificationCourseEntity  -> Pair.of(certificationCourseRepository.findById(new ObjectId(certificationCourseEntity.getId()))
                            .orElseThrow(() -> new IllegalStateException("Course not found")), certificationCourseEntity))
                    .map(simpleCertificationCourseDetailMapper::entityToDTO);
        } catch (final RepositoryException e) {
            log.debug("editById RepositoryException -> " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * @return @throws Throwable
     */
    @Override
    public Iterable<SimpleCertificationCourseDetailDTO> getAll() throws Throwable {
        final Iterable<Pair<CertificationCourseEntity, CertificationCourseBcEntity>> certificationCoursesByCA = StreamSupport.stream(certificationCourseBlockchainRepository.getAll().spliterator(), true).map(certificationCourseBcEntity ->
                Pair.of(certificationCourseRepository.findById(new ObjectId(certificationCourseBcEntity.getId()))
                        .orElseThrow(() -> new IllegalStateException("Course not found")), certificationCourseBcEntity)).collect(Collectors.toList());
        return simpleCertificationCourseDetailMapper.entityListToDTOList(certificationCoursesByCA);
    }

    /**
     * @param caWalletHash
     * @return
     * @throws Throwable
     */
    @Override
    public Iterable<SimpleCertificationCourseDetailDTO> getAllByCA(final String caWalletHash) throws Throwable {
        Assert.notNull(caWalletHash, "CA wallet hash");
        final Iterable<Pair<CertificationCourseEntity, CertificationCourseBcEntity>> certificationCoursesByCA = StreamSupport.stream(certificationCourseBlockchainRepository.getAllByCa(caWalletHash).spliterator(), true).map(certificationCourseBcEntity ->
                Pair.of(certificationCourseRepository.findById(new ObjectId(certificationCourseBcEntity.getId()))
                        .orElseThrow(() -> new IllegalStateException("Course not found")), certificationCourseBcEntity)).collect(Collectors.toList());
        return simpleCertificationCourseDetailMapper.entityListToDTOList(certificationCoursesByCA);
    }
}
