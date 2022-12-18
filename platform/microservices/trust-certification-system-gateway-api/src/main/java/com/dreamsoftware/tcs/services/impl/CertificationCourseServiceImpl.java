package com.dreamsoftware.tcs.services.impl;

import com.dreamsoftware.tcs.config.properties.StreamChannelsProperties;
import com.dreamsoftware.tcs.mapper.*;
import com.dreamsoftware.tcs.persistence.bc.repository.ICertificationCourseBlockchainRepository;
import com.dreamsoftware.tcs.persistence.nosql.entity.*;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseEditionRepository;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseRepository;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.service.IQRCodeGenerator;
import com.dreamsoftware.tcs.services.IAccountsService;
import com.dreamsoftware.tcs.services.ICertificationCourseService;
import com.dreamsoftware.tcs.stream.events.course.*;
import com.dreamsoftware.tcs.web.core.FileInfoDTO;
import com.dreamsoftware.tcs.web.dto.request.SaveCertificationCourseDTO;
import com.dreamsoftware.tcs.web.dto.request.SaveCertificationCourseEditionDTO;
import com.dreamsoftware.tcs.web.dto.response.*;
import com.dreamsoftware.tcs.web.security.userdetails.ICommonUserDetailsAware;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * Certification Course Service
 *
 * @author ssanchez
 */
@Slf4j
@Service("certificationCourseService")
@RequiredArgsConstructor
public class CertificationCourseServiceImpl implements ICertificationCourseService {

    private final IAccountsService accountsService;
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
    private final IQRCodeGenerator qrCodeGenerator;

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
     *
     * @param studentWalletHash
     * @param courseId
     * @param editionId
     * @param securityToken
     * @throws Throwable
     */
    @Override
    public void checkIn(String studentWalletHash, String courseId, String editionId, String securityToken) throws Throwable {
        Assert.notNull(studentWalletHash, "studentWalletHash can not be null");
        Assert.notNull(courseId, "courseId can not be null");
        Assert.notNull(editionId, "editionId can not be null");
        Assert.notNull(securityToken, "securityToken can not be null");
        log.debug("checkIn course edition editionId -> " + editionId + " CALLED!");
        final CertificationCourseEditionEntity certificationCourseEditionEntity = certificationCourseEditionRepository.findById(new ObjectId(editionId))
                .orElseThrow(() -> new IllegalStateException("Course Edition can not be found"));
        final CertificationCourseAttendeeControlEntity attendeeControlEntity = certificationCourseEditionEntity.getAttendeeControl();
        final CertificationCourseEditionAttendeeEntity attendeeEntity = attendeeControlEntity.getAttendedUsers().stream().filter(item ->
                item.getStudent().getWalletHash().equals(studentWalletHash) &&
                        item.getSecurityToken().equals(securityToken)).findFirst().orElseThrow();
        attendeeEntity.setAttendedCount(attendeeEntity.getAttendedCount() + 1);
        certificationCourseEditionRepository.save(certificationCourseEditionEntity);
    }

    /**
     *
     * @param courseEditionId
     * @return
     */
    @Override
    public boolean currentUserHasBeenEnrolledTo(String courseEditionId) {
        Assert.notNull(courseEditionId, "courseEditionId can not be null");
        AtomicBoolean hasBeenEnrolled = new AtomicBoolean(false);
        final ICommonUserDetailsAware<String> principal = accountsService.getCurrentPrincipal();
        if(principal != null && ObjectId.isValid(courseEditionId)) {
            certificationCourseEditionRepository.findById(new ObjectId(courseEditionId)).ifPresent(certificationCourseEditionEntity -> {
                final CertificationCourseAttendeeControlEntity attendeeControlEntity = certificationCourseEditionEntity.getAttendeeControl();
                if(attendeeControlEntity != null) {
                    final List<String> attendeeWalletHash = attendeeControlEntity.getAttendedUsers().stream()
                            .map(CertificationCourseEditionAttendeeEntity::getStudent)
                            .map(UserEntity::getWalletHash)
                            .collect(Collectors.toList());
                    hasBeenEnrolled.set(attendeeWalletHash.contains(principal.getWalletHash()));
                }
            });
        }
        return hasBeenEnrolled.get();
    }

    /**
     *
     * @param courseEditionId
     * @return
     */
    @Override
    public boolean currentUserHasBeenReachAttendControlLimitTo(String courseEditionId) {
        Assert.notNull(courseEditionId, "courseEditionId can not be null");
        AtomicBoolean isValid = new AtomicBoolean(false);
        log.debug("currentUserHasBeenReachAttendControlLimitTo -> " + courseEditionId + " CALLED!");
        final ICommonUserDetailsAware<String> principal = accountsService.getCurrentPrincipal();
        if(principal != null && ObjectId.isValid(courseEditionId)) {
            log.debug("courseEditionId is valid");
            certificationCourseEditionRepository.findById(new ObjectId(courseEditionId)).ifPresent(certificationCourseEditionEntity -> {
                final CertificationCourseAttendeeControlEntity attendeeControlEntity = certificationCourseEditionEntity.getAttendeeControl();
                if(attendeeControlEntity != null) {
                    final List<CertificationCourseEditionAttendeeEntity> attendeeList = attendeeControlEntity.getAttendedUsers().stream()
                            .filter(item -> item.getStudent().getWalletHash().equals(principal.getWalletHash()))
                            .collect(Collectors.toList());
                    if(!attendeeList.isEmpty()) {
                        isValid.set(attendeeControlEntity.getMaxAttendanceCount() <= 0 || (attendeeList.get(0).getAttendedCount() / attendeeControlEntity.getMaxAttendanceCount() * 100) >= attendeeControlEntity.getMinPercentageAttendanceRequired());
                    }
                } else {
                    isValid.set(true);
                }
            });
        }
        return isValid.get();
    }

    /**
     *
     * @param courseEditionId
     * @return
     */
    @Override
    public boolean courseEditionAllowEnrollment(final String courseEditionId) {
        Assert.notNull(courseEditionId, "courseEditionId can not be null");
        AtomicBoolean allowEnrollment = new AtomicBoolean(false);
        if(ObjectId.isValid(courseEditionId)) {
            certificationCourseEditionRepository.findById(new ObjectId(courseEditionId)).ifPresent(courseEditionEntity -> {
                final CertificationCourseAttendeeControlEntity attendeeControlEntity = courseEditionEntity.getAttendeeControl();
                if(attendeeControlEntity != null && (courseEditionEntity.getStartAt() == null || courseEditionEntity.getStartAt().after(new Date()))) {
                    allowEnrollment.set(attendeeControlEntity.getMaxAttendanceCount() == 0 || attendeeControlEntity.getMaxAttendanceCount() > 0 &&
                            attendeeControlEntity.getAttendedUsers().size() < attendeeControlEntity.getMaxAttendanceCount());
                }
            });
        }
        return allowEnrollment.get();
    }

    /**
     *
     * @param courseEditionId
     * @return
     */
    @Override
    public boolean courseEditionAllowCheckIn(final String courseEditionId) {
        Assert.notNull(courseEditionId, "courseEditionId can not be null");
        AtomicBoolean allowCheckIn = new AtomicBoolean(false);
        if(ObjectId.isValid(courseEditionId)) {
            certificationCourseEditionRepository.findById(new ObjectId(courseEditionId)).ifPresent(courseEditionEntity -> {
                final CertificationCourseAttendeeControlEntity attendeeControlEntity = courseEditionEntity.getAttendeeControl();
                allowCheckIn.set(attendeeControlEntity != null && (courseEditionEntity.getStartAt() == null ||
                        courseEditionEntity.getEndAt() == null || courseEditionEntity.getStartAt().before(new Date()) && courseEditionEntity.getEndAt().after(new Date())));
            });
        }
        return allowCheckIn.get();
    }

    /**
     *
     * @param courseId
     * @param courseEdition
     * @param studentWalletHash
     * @param width
     * @param height
     * @return
     * @throws Exception
     */
    @Override
    public FileInfoDTO getEnrollmentQR(final String courseId, final String courseEdition, final String studentWalletHash, final Integer width, final Integer height) throws Exception {
        Assert.notNull(courseId, "courseId can not be null");
        Assert.notNull(courseEdition, "courseEdition can not be null");
        Assert.notNull(studentWalletHash, "studentWalletHash can not be null");
        Assert.notNull(width, "width can not be null");
        Assert.notNull(height, "height can not be null");
        log.debug("getEnrollmentQR CALLED!");
        final CertificationCourseEditionEntity certificationCourseEditionEntity = certificationCourseEditionRepository.findById(new ObjectId(courseEdition))
                .orElseThrow(() -> new IllegalStateException("Course Edition not found"));
        final String attendeeSecurityToken = certificationCourseEditionEntity.getAttendeeControl().getAttendedUsers().stream()
                .filter(attendeeEntity -> attendeeEntity.getStudent().getWalletHash().equals(studentWalletHash))
                .findFirst().map(CertificationCourseEditionAttendeeEntity::getSecurityToken)
                .orElseThrow(() -> new IllegalStateException("Attendee not found"));
        final byte[] qrCodeData = qrCodeGenerator.getQRCodeImage(attendeeSecurityToken, width, height);
        return FileInfoDTO
                .builder()
                .content(qrCodeData)
                .contentType(MediaType.IMAGE_PNG_VALUE)
                .size((long) qrCodeData.length)
                .build();
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
        final Iterable<CertificationCourseEntity> certificationCourseList = certificationCourseRepository.findAllByStatusNotOrderByCreatedAtDesc(CertificationCourseStateEnum.REMOVED);
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
