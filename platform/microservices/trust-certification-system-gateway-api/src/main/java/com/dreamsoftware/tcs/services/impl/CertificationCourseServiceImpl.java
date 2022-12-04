package com.dreamsoftware.tcs.services.impl;

import com.dreamsoftware.tcs.config.properties.StreamChannelsProperties;
import com.dreamsoftware.tcs.mapper.*;
import com.dreamsoftware.tcs.persistence.bc.repository.ICertificationCourseBlockchainRepository;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEditionEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseEditionRepository;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseRepository;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.services.ICertificationCourseService;
import com.dreamsoftware.tcs.stream.events.course.*;
import com.dreamsoftware.tcs.web.dto.request.SaveCertificationCourseDTO;
import com.dreamsoftware.tcs.web.dto.request.SaveCertificationCourseEditionDTO;
import com.dreamsoftware.tcs.web.dto.response.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

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
    private final CertificationCourseEditionDetailMapper certificationCourseEditionDetailMapper;
    private final UpdateCertificationCourseMapper updateCertificationCourseMapper;
    private final UpdateCertificationCourseEditionMapper updateCertificationCourseEditionMapper;
    private final SaveCertificationCourseMapper saveCertificationCourseMapper;
    private final SaveCertificationCourseEditionMapper saveCertificationCourseEditionMapper;
    private final ICertificationCourseBlockchainRepository certificationCourseBlockchainRepository;
    private final CertificationCourseRepository certificationCourseRepository;
    private final CertificationCourseEditionRepository certificationCourseEditionRepository;
    private final UserRepository userRepository;
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
     * Enable Certification Course Edition
     *
     * @param caWalletHash
     * @param courseId
     * @param editionId
     * @throws Throwable
     */
    @Override
    public void enable(String caWalletHash, String courseId, String editionId) throws Throwable {
        Assert.notNull(caWalletHash, "CA wallet can not be null");
        Assert.notNull(courseId, "Course ID can not be null");
        Assert.notNull(editionId, "Edition ID can not be null");
        log.debug("Enable course edition -> " + editionId + " CALLED!");
        streamBridge.send(streamChannelsProperties.getCourseManagement(), EnableCertificationCourseEditionEvent.builder()
                .caWalletHash(caWalletHash)
                .courseId(courseId)
                .editionId(editionId)
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
     * Disable Certification Course Edition
     *
     * @param caWalletHash
     * @param courseId
     * @param editionId
     * @throws Throwable
     */
    @Override
    public void disable(String caWalletHash, String courseId, String editionId) throws Throwable {
        Assert.notNull(caWalletHash, "CA wallet can not be null");
        Assert.notNull(courseId, "Course ID can not be null");
        Assert.notNull(editionId, "Edition Id can not be null");
        log.debug("Disable course edition -> " + courseId + " CALLED!");
        streamBridge.send(streamChannelsProperties.getCourseManagement(), DisableCertificationCourseEditionEvent.builder()
                .caWalletHash(caWalletHash)
                .courseId(courseId)
                .editionId(editionId)
                .build());
    }

    /**
     *
     * @param studentWalletHash
     * @param courseId
     * @param editionId
     * @throws Throwable
     */
    @Override
    public void enroll(String studentWalletHash, String courseId, String editionId) throws Throwable {
        Assert.notNull(studentWalletHash, "Student wallet can not be null");
        Assert.notNull(courseId, "Course ID can not be null");
        Assert.notNull(editionId, "Edition Id can not be null");
        log.debug("course edition enrollment -> " + courseId + " CALLED!");
        streamBridge.send(streamChannelsProperties.getCourseManagement(), CourseEditionEnrollmentRequestEvent.builder()
                .studentWalletHash(studentWalletHash)
                .courseId(courseId)
                .editionId(editionId)
                .build());
    }

    /**
     * Save Certification Course
     *
     * @param model
     */
    @Override
    public SimpleCertificationCourseDetailDTO save(final SaveCertificationCourseDTO model) {
        Assert.notNull(model, "model can not be null");
        final CertificationCourseEntity certificationCourseEntity = saveCertificationCourseMapper.dtoToEntity(model);
        final CertificationCourseEntity certificationCourseEntitySaved = certificationCourseRepository.save(certificationCourseEntity);
        return simpleCertificationCourseDetailMapper.entityToDTO(certificationCourseEntitySaved);
    }

    /**
     * @param model
     * @return
     */
    @Override
    public CertificationCourseEditionDetailDTO save(SaveCertificationCourseEditionDTO model) {
        Assert.notNull(model, "model can not be null");
        final CertificationCourseEditionEntity certificationCourseEditionEntity = saveCertificationCourseEditionMapper.dtoToEntity(model);
        final CertificationCourseEditionEntity certificationCourseEditionEntitySaved = certificationCourseEditionRepository.save(certificationCourseEditionEntity);
        streamBridge.send(streamChannelsProperties.getCourseManagement(), NewCertificationCourseEditionRegistrationRequestEvent.builder()
                .courseId(certificationCourseEditionEntitySaved.getCourse().getId().toString())
                .courseEditionId(certificationCourseEditionEntitySaved.getId().toString())
                .caWalletHash(model.getCaWalletHash())
                .build());
        return certificationCourseEditionDetailMapper.entityToDTO(certificationCourseEditionEntity);
    }

    /**
     * @param caWalletHash
     * @param courseId
     * @throws Throwable
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
     * @param editionId
     * @throws Throwable
     */
    @Override
    public void remove(final String caWalletHash, final String courseId, final String editionId) throws Throwable {
        Assert.notNull(caWalletHash, "CA wallet can not be null");
        Assert.notNull(courseId, "Course Id can not be null");
        Assert.notNull(editionId, "Edition id can not be null");
        log.debug("remove certification course edition " + editionId + " CALLED!");
        streamBridge.send(streamChannelsProperties.getCourseManagement(), RemoveCertificationCourseEditionEvent.builder()
                .caWalletHash(caWalletHash)
                .courseId(courseId)
                .editionId(editionId)
                .build());
    }

    /**
     * @param courseId
     * @return
     * @throws Throwable
     */
    @Override
    public Boolean canBeIssued(final String courseId) throws Throwable {
        Assert.notNull(courseId, "Course ID can not be null");
        log.debug("check certification course " + courseId + " can be issued CALLED!");
        return certificationCourseBlockchainRepository.canBeIssued(courseId);
    }

    /**
     * @param courseId
     * @return
     * @throws Throwable
     */
    @Override
    public Boolean canBeRenewed(final String courseId) throws Throwable {
        Assert.notNull(courseId, "Course ID can not be null");
        log.debug("check certification course " + courseId + " can be renewed CALLED!");
        return certificationCourseBlockchainRepository.canBeRenewed(courseId);
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
        return certificationCourseDetailMapper.entityToDTO(certificationCourseEntity);
    }

    /**
     * @param courseId
     * @return
     */
    @Override
    public Optional<UpdateCertificationCourseDTO> editCertificationCourseById(final String courseId) {
        Assert.notNull(courseId, "Id can not be null");
        return certificationCourseRepository.findById(new ObjectId(courseId))
                .map(updateCertificationCourseMapper::entityToDTO);
    }

    /**
     * @param editionId
     * @return
     */
    @Override
    public Optional<UpdateCertificationCourseEditionDTO> editCertificationCourseEditionById(final String editionId) {
        return certificationCourseEditionRepository.findById(new ObjectId(editionId))
                .map(updateCertificationCourseEditionMapper::entityToDTO);
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
        return certificationCourseRepository.findById(new ObjectId(courseId))
                .map(certificationCourseEntity -> updateCertificationCourseMapper.update(certificationCourseEntity, model))
                .map(certificationCourseRepository::save)
                .map(simpleCertificationCourseDetailMapper::entityToDTO);
    }

    @Override
    public Optional<CertificationCourseEditionDetailDTO> update(String courseId, String editionId, UpdateCertificationCourseEditionDTO model, String caWalletHash) {
        Assert.notNull(courseId, "Course Id can not be null");
        Assert.notNull(editionId, "Edition Id can not be null");
        Assert.notNull(model, "model can not be null");
        Assert.notNull(caWalletHash, "CA Wallet can not be null");
        final Optional<CertificationCourseEditionDetailDTO> certificationCourseEditionDetailDTO = certificationCourseEditionRepository.findById(new ObjectId(editionId))
                .map(certificationCourseEditionEntity -> updateCertificationCourseEditionMapper.update(certificationCourseEditionEntity, model))
                .map(certificationCourseEditionRepository::save)
                .map(certificationCourseEditionDetailMapper::entityToDTO);
        certificationCourseEditionDetailDTO.ifPresent(edition -> {
            streamBridge.send(streamChannelsProperties.getCourseManagement(), UpdateCertificationCourseEditionRequestEvent.builder()
                    .courseEditionId(editionId)
                    .courseId(courseId)
                    .caWalletHash(caWalletHash)
                    .build());
        });
        return certificationCourseEditionDetailDTO;
    }

    /**
     * @return @throws Throwable
     */
    @Override
    public Iterable<SimpleCertificationCourseDetailDTO> getAll() throws Throwable {
        final Iterable<CertificationCourseEntity> certificationCourseList = certificationCourseRepository.findAll();
        return simpleCertificationCourseDetailMapper.entityListToDTOList(certificationCourseList);
    }

    /**
     * @param term
     * @return
     * @throws Throwable
     */
    @Override
    public Iterable<SimpleCertificationCourseDetailDTO> searchCourses(final String term) throws Throwable {
        Assert.notNull(term, "query term can not be null");
        Assert.isTrue(!term.isBlank(), "query term can not be empty");
        final Iterable<CertificationCourseEntity> certificationCourseList = certificationCourseRepository.searchByTerm(term);
        return simpleCertificationCourseDetailMapper.entityListToDTOList(certificationCourseList);
    }

    /**
     * @param caWalletHash
     * @return
     * @throws Throwable
     */
    @Override
    public Iterable<CertificationCourseDetailDTO> getAllByCA(final String caWalletHash) throws Throwable {
        Assert.notNull(caWalletHash, "CA wallet hash");
        final UserEntity caMemberEntity = userRepository.findOneByWalletHash(caWalletHash)
                .orElseThrow(() -> new IllegalStateException("CA not found"));
        final Iterable<CertificationCourseEntity> certificationCoursesByCA = certificationCourseRepository.findAllByCa(caMemberEntity.getCa());
        return certificationCourseDetailMapper.entityListToDTOList(certificationCoursesByCA);
    }

    /**
     * @param courseId
     * @param userId
     * @return
     */
    @Override
    public Boolean isTheCourseOwner(String courseId, ObjectId userId) {
        Assert.notNull(courseId, "Id can not be null");
        Assert.notNull(userId, "Id can not be null");
        AtomicBoolean isTheCourseOwner = new AtomicBoolean(false);
        userRepository.findById(userId).ifPresent(userEntity -> {
            certificationCourseRepository.findById(new ObjectId(courseId)).ifPresent(certificationCourseEntity -> {
                if (userEntity.getCa() != null) {
                    isTheCourseOwner.set(userEntity.getCa().getId().equals(certificationCourseEntity.getCa().getId()));
                }
            });
        });
        return isTheCourseOwner.get();
    }
}
