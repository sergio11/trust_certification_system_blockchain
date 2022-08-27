package com.dreamsoftware.tcs.services.impl;

import com.dreamsoftware.tcs.config.properties.StreamChannelsProperties;
import com.dreamsoftware.tcs.mapper.CertificateIssuanceRequestMapper;
import com.dreamsoftware.tcs.mapper.CertificateIssuedMapper;
import com.dreamsoftware.tcs.stream.events.OnNewIssueCertificateRequestEvent;
import com.dreamsoftware.tcs.persistence.bc.repository.ITrustCertificationBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificateIssuedEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificateIssuanceRequestEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificateStatusEnum;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.services.ITrustCertificationService;
import com.dreamsoftware.tcs.web.dto.request.IssueCertificateRequestDTO;
import com.dreamsoftware.tcs.web.dto.response.CertificateIssuedDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificateIssuanceRequestRepository;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseRepository;
import com.dreamsoftware.tcs.service.IipfsGateway;
import com.dreamsoftware.tcs.stream.events.notifications.certificate.CertificateDisabledNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.certificate.CertificateEnabledNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.certificate.CertificateRenewedNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.certificate.CertificateRequestAcceptedNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.certificate.CertificateRequestRejectedNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.certificate.CertificateVisibilityChangedNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.certificate.IssueCertificateRequestedNotificationEvent;
import com.dreamsoftware.tcs.web.core.FileInfoDTO;
import com.dreamsoftware.tcs.web.dto.response.CertificateIssuanceRequestDTO;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

/**
 *
 * @author ssanchez
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TrustCertificationServiceImpl implements ITrustCertificationService {

    private final CertificateIssuedMapper certificateIssuedMapper;
    private final UserRepository userRepository;
    private final CertificateIssuanceRequestRepository certificateIssuanceRequestRepository;
    private final ITrustCertificationBlockchainRepository trustCertificationRepository;
    private final CertificationCourseRepository certificationCourseRepository;
    private final StreamBridge streamBridge;
    private final StreamChannelsProperties streamChannelsProperties;
    private final CertificateIssuanceRequestMapper certificateIssuanceRequestMapper;
    private final IipfsGateway ipfsService;

    /**
     *
     * @param ownerWallet
     * @param certificationId
     * @return
     * @throws Throwable
     */
    @Override
    public CertificateIssuedDTO enable(String ownerWallet, String certificationId) throws Throwable {
        Assert.notNull(ownerWallet, "ownerWallet can not be null");
        Assert.notNull(certificationId, "certificationId can not be null");
        final CertificateIssuedEntity certificate = trustCertificationRepository.enable(ownerWallet, certificationId);
        final CertificateIssuedDTO certificateIssuedDTO = certificateIssuedMapper.entityToDTO(certificate);
        streamBridge.send(streamChannelsProperties.getNotificationDeliveryRequest(), CertificateEnabledNotificationEvent.builder()
                .id(certificateIssuedDTO.getId())
                .build());
        return certificateIssuedDTO;
    }

    /**
     *
     * @param ownerWallet
     * @param certificationId
     * @return
     * @throws Throwable
     */
    @Override
    public CertificateIssuedDTO disable(String ownerWallet, String certificationId) throws Throwable {
        Assert.notNull(ownerWallet, "ownerWallet can not be null");
        Assert.notNull(certificationId, "certificationId can not be null");
        final CertificateIssuedEntity certificate = trustCertificationRepository.disable(ownerWallet, certificationId);
        final CertificateIssuedDTO certificateIssuedDTO = certificateIssuedMapper.entityToDTO(certificate);
        streamBridge.send(streamChannelsProperties.getNotificationDeliveryRequest(), CertificateDisabledNotificationEvent.builder()
                .id(certificateIssuedDTO.getId())
                .build());
        return certificateIssuedDTO;
    }

    /**
     *
     * @param ownerWallet
     * @param certificationId
     * @param isVisible
     * @return
     * @throws Throwable
     */
    @Override
    public CertificateIssuedDTO updateVisibility(String ownerWallet, String certificationId, Boolean isVisible) throws Throwable {
        Assert.notNull(ownerWallet, "ownerWallet can not be null");
        Assert.notNull(certificationId, "certificationId can not be null");
        final CertificateIssuedEntity certificate = trustCertificationRepository.updateCertificateVisibility(ownerWallet, certificationId, isVisible);
        final CertificateIssuedDTO certificateIssuedDTO = certificateIssuedMapper.entityToDTO(certificate);
        streamBridge.send(streamChannelsProperties.getNotificationDeliveryRequest(), CertificateVisibilityChangedNotificationEvent.builder()
                .id(certificateIssuedDTO.getId())
                .isVisible(certificateIssuedDTO.getIsVisible())
                .build());
        return certificateIssuedDTO;
    }

    /**
     *
     * @param ownerWallet
     * @param certificationId
     * @return
     * @throws Throwable
     */
    @Override
    public CertificateIssuedDTO getDetail(String ownerWallet, String certificationId) throws Throwable {
        Assert.notNull(ownerWallet, "ownerWallet can not be null");
        Assert.notNull(certificationId, "certificationId can not be null");
        final CertificateIssuedEntity certificate = trustCertificationRepository.getCertificateDetail(ownerWallet, certificationId);
        return certificateIssuedMapper.entityToDTO(certificate);
    }

    /**
     *
     * @param ownerWallet
     * @param certificationId
     * @return
     * @throws Throwable
     */
    @Override
    public Boolean isCertificateValid(String ownerWallet, String certificationId) throws Throwable {
        Assert.notNull(ownerWallet, "ownerWallet can not be null");
        Assert.notNull(certificationId, "certificationId can not be null");
        return trustCertificationRepository.isCertificateValid(ownerWallet, certificationId);
    }

    /**
     *
     * @param ownerWallet
     * @return
     * @throws Throwable
     */
    @Override
    public Iterable<CertificateIssuedDTO> getMyCertificatesAsRecipient(String ownerWallet) throws Throwable {
        Assert.notNull(ownerWallet, "ownerWallet can not be null");
        final List<CertificateIssuedEntity> myCertificates = trustCertificationRepository.getMyCertificatesAsRecipient(ownerWallet);
        return certificateIssuedMapper.entityToDTO(myCertificates);
    }

    /**
     *
     * @param ownerWallet
     * @return
     * @throws Throwable
     */
    @Override
    public Iterable<CertificateIssuedDTO> getMyCertificatesAsIssuer(String ownerWallet) throws Throwable {
        Assert.notNull(ownerWallet, "ownerWallet can not be null");
        final List<CertificateIssuedEntity> myCertificates = trustCertificationRepository.getMyCertificatesAsIssuer(ownerWallet);
        return certificateIssuedMapper.entityToDTO(myCertificates);
    }

    /**
     *
     * @param issueCertificate
     * @throws Throwable
     */
    @Override
    public CertificateIssuanceRequestDTO issueCertificateRequest(IssueCertificateRequestDTO issueCertificate) throws Throwable {
        Assert.notNull(issueCertificate.getStudentWalletHash(), "Student Wallet Hash can not be null");
        Assert.notNull(issueCertificate.getCertificateCourseId(), "certificateCourseId can not be null");
        Assert.notNull(issueCertificate.getQualification(), "qualification can not be null");
        final CertificationCourseEntity certificationCourseEntity = certificationCourseRepository.findById(new ObjectId(issueCertificate.getCertificateCourseId()))
                .orElseThrow(() -> new IllegalStateException("Course not found"));
        final UserEntity caEntity = certificationCourseEntity.getCa();
        final UserEntity studentEntity = userRepository.findOneByWalletHash(issueCertificate.getStudentWalletHash())
                .orElseThrow(() -> new IllegalStateException("Student not found"));
        final CertificateIssuanceRequestEntity certificateRequest = CertificateIssuanceRequestEntity
                .builder()
                .ca(caEntity)
                .course(certificationCourseEntity)
                .createdAt(new Date())
                .status(CertificateStatusEnum.PENDING_REVIEW)
                .qualification(issueCertificate.getQualification())
                .student(studentEntity)
                .build();
        final CertificateIssuanceRequestEntity certificateRequestSaved = certificateIssuanceRequestRepository.save(certificateRequest);
        final CertificateIssuanceRequestDTO certificateIssuanceRequestDTO = certificateIssuanceRequestMapper.entityToDTO(certificateRequestSaved);
        streamBridge.send(streamChannelsProperties.getNotificationDeliveryRequest(), IssueCertificateRequestedNotificationEvent.builder()
                .id(certificateIssuanceRequestDTO.getId())
                .build());
        return certificateIssuanceRequestDTO;
    }

    /**
     *
     * @param id
     */
    @Override
    public CertificateIssuanceRequestDTO acceptCertificateRequest(ObjectId id) {
        Assert.notNull(id, "Id can not be null");
        final CertificateIssuanceRequestEntity certificate = certificateIssuanceRequestRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Certificate not found"));
        final OnNewIssueCertificateRequestEvent event = OnNewIssueCertificateRequestEvent
                .builder()
                .caWalletHash(certificate.getCa().getWalletHash())
                .courseId(certificate.getCourse().getCourseId())
                .qualification(certificate.getQualification())
                .studentWalletHash(certificate.getStudent().getWalletHash())
                .build();
        streamBridge.send(streamChannelsProperties.getNewCertificationRequest(), event);
        final CertificateIssuanceRequestEntity certificateRequestUpdated = certificateIssuanceRequestRepository.updateStatus(id, CertificateStatusEnum.REVIEWED);
        final CertificateIssuanceRequestDTO certificateIssuanceRequestDTO = certificateIssuanceRequestMapper.entityToDTO(certificateRequestUpdated);
        streamBridge.send(streamChannelsProperties.getNotificationDeliveryRequest(), CertificateRequestAcceptedNotificationEvent.builder()
                .id(certificateIssuanceRequestDTO.getId())
                .build());
        return certificateIssuanceRequestDTO;
    }

    /**
     *
     * @param id
     */
    @Override
    public CertificateIssuanceRequestDTO rejectCertificateRequest(ObjectId id) {
        Assert.notNull(id, "Id can not be null");
        final CertificateIssuanceRequestEntity certificateRequestUpdated = certificateIssuanceRequestRepository.updateStatus(id, CertificateStatusEnum.REJECTED);
        final CertificateIssuanceRequestDTO certificateIssuanceRequestDTO = certificateIssuanceRequestMapper.entityToDTO(certificateRequestUpdated);
        streamBridge.send(streamChannelsProperties.getNotificationDeliveryRequest(), CertificateRequestRejectedNotificationEvent.builder()
                .id(certificateIssuanceRequestDTO.getId())
                .build());
        return certificateIssuanceRequestDTO;
    }

    /**
     *
     * @param ownerWallet
     * @param certificationId
     * @return
     * @throws Throwable
     */
    @Override
    public CertificateIssuedDTO renewCertificate(final String ownerWallet, final String certificationId) throws Throwable {
        Assert.notNull(ownerWallet, "ownerWallet can not be null");
        Assert.notNull(certificationId, "certificationId can not be null");
        final CertificateIssuedEntity certificate = trustCertificationRepository.renewCertificate(ownerWallet, certificationId);
        final CertificateIssuedDTO certificateIssuedDTO = certificateIssuedMapper.entityToDTO(certificate);
        streamBridge.send(streamChannelsProperties.getNotificationDeliveryRequest(), CertificateRenewedNotificationEvent.builder()
                .id(certificateIssuedDTO.getId())
                .build());
        return certificateIssuedDTO;
    }

    /**
     *
     * @param studentWalletHash
     * @return
     */
    @Override
    public Iterable<CertificateIssuanceRequestDTO> getCertificatesIssuanceRequestsFromStudent(final String studentWalletHash) {
        Assert.notNull(studentWalletHash, "Student Wallet hash can not be null");
        Iterable<CertificateIssuanceRequestEntity> certificateRequests = certificateIssuanceRequestRepository.findByStudentWalletHashOrderByUpdatedAtDesc(studentWalletHash);
        return certificateIssuanceRequestMapper.entityToDTO(certificateRequests);
    }

    /**
     *
     * @param caWalletHash
     * @return
     */
    @Override
    public Iterable<CertificateIssuanceRequestDTO> getCertificatesIssuanceRequestsFromCa(final String caWalletHash) {
        Assert.notNull(caWalletHash, "CA Wallet hash can not be null");
        Iterable<CertificateIssuanceRequestEntity> certificateRequests = certificateIssuanceRequestRepository.findByCaWalletHashOrderByUpdatedAtDesc(caWalletHash);
        return certificateIssuanceRequestMapper.entityToDTO(certificateRequests);
    }

    /**
     *
     * @param ownerWallet
     * @param certificateId
     * @return
     * @throws Exception
     */
    @Override
    public FileInfoDTO getCertificateFile(final String ownerWallet, final String certificateId) throws Exception {
        Assert.notNull(certificateId, "Certificate Id can not be null");
        final CertificateIssuedEntity certificate = trustCertificationRepository.getCertificateDetail(ownerWallet, certificateId);
        final byte[] fileContents = ipfsService.get(certificate.getCid());
        return FileInfoDTO
                .builder()
                .content(fileContents)
                .contentType(MediaType.APPLICATION_PDF_VALUE)
                .size((long) fileContents.length)
                .build();
    }
}
