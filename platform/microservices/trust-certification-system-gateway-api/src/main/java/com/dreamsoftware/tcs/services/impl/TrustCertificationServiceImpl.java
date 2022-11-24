package com.dreamsoftware.tcs.services.impl;

import com.dreamsoftware.tcs.config.properties.StreamChannelsProperties;
import com.dreamsoftware.tcs.mapper.CertificateIssuanceRequestMapper;
import com.dreamsoftware.tcs.mapper.CertificationCourseEditionDetailMapper;
import com.dreamsoftware.tcs.mapper.SimpleCertificateIssuedMapper;
import com.dreamsoftware.tcs.mapper.SimpleUserMapper;
import com.dreamsoftware.tcs.persistence.bc.repository.ITrustCertificationBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificateIssuedEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.*;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificateIssuanceRequestRepository;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseEditionRepository;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.service.ICryptService;
import com.dreamsoftware.tcs.service.IQRCodeGenerator;
import com.dreamsoftware.tcs.service.IipfsGateway;
import com.dreamsoftware.tcs.services.ITrustCertificationService;
import com.dreamsoftware.tcs.stream.events.certificate.DisableCertificateRequestEvent;
import com.dreamsoftware.tcs.stream.events.certificate.EnableCertificateRequestEvent;
import com.dreamsoftware.tcs.stream.events.certificate.OnNewCertificateRequestAcceptedEvent;
import com.dreamsoftware.tcs.stream.events.certificate.UpdateCertificateVisibilityRequestEvent;
import com.dreamsoftware.tcs.stream.events.notifications.certificate.CertificateRenewedNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.certificate.CertificateRequestAcceptedNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.certificate.CertificateRequestRejectedNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.certificate.IssueCertificateRequestedNotificationEvent;
import com.dreamsoftware.tcs.web.controller.certification.error.exception.CertificateInvalidException;
import com.dreamsoftware.tcs.web.core.FileInfoDTO;
import com.dreamsoftware.tcs.web.dto.request.IssueCertificateRequestDTO;
import com.dreamsoftware.tcs.web.dto.response.CertificateIssuanceRequestDTO;
import com.dreamsoftware.tcs.web.dto.response.CertificateIssuedDetailDTO;
import com.dreamsoftware.tcs.web.dto.response.SimpleCertificateIssuedDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import java.util.Date;
import java.util.List;

/**
 * @author ssanchez
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TrustCertificationServiceImpl implements ITrustCertificationService {

    private final SimpleCertificateIssuedMapper simpleCertificateIssuedMapper;
    private final SimpleUserMapper simpleUserMapper;
    private final CertificationCourseEditionDetailMapper certificationCourseEditionDetailMapper;
    private final UserRepository userRepository;
    private final CertificateIssuanceRequestRepository certificateIssuanceRequestRepository;
    private final ITrustCertificationBlockchainRepository trustCertificationRepository;
    private final CertificationCourseEditionRepository certificationCourseEditionRepository;
    private final StreamBridge streamBridge;
    private final StreamChannelsProperties streamChannelsProperties;
    private final CertificateIssuanceRequestMapper certificateIssuanceRequestMapper;
    private final IipfsGateway ipfsService;
    private final ICryptService cryptService;
    private final IQRCodeGenerator qrCodeGenerator;

    /**
     * @param ownerWallet
     * @param certificationId
     * @return
     * @throws Throwable
     */
    @Override
    public void enable(final String ownerWallet, final String certificationId) throws Throwable {
        Assert.notNull(ownerWallet, "ownerWallet can not be null");
        Assert.notNull(certificationId, "certificationId can not be null");
        streamBridge.send(streamChannelsProperties.getCertificationManagement(), EnableCertificateRequestEvent.builder()
                .certificationId(certificationId)
                .studentWalletHash(ownerWallet)
                .build());
    }

    /**
     * @param ownerWallet
     * @param certificationId
     * @return
     * @throws Throwable
     */
    @Override
    public void disable(final String ownerWallet, final String certificationId) throws Throwable {
        Assert.notNull(ownerWallet, "ownerWallet can not be null");
        Assert.notNull(certificationId, "certificationId can not be null");
        streamBridge.send(streamChannelsProperties.getCertificationManagement(), DisableCertificateRequestEvent.builder()
                .certificationId(certificationId)
                .studentWalletHash(ownerWallet)
                .build());
    }

    /**
     * @param ownerWallet
     * @param certificationId
     * @param isVisible
     * @return
     * @throws Throwable
     */
    @Override
    public void updateVisibility(final String ownerWallet, final String certificationId, final Boolean isVisible) throws Throwable {
        Assert.notNull(ownerWallet, "ownerWallet can not be null");
        Assert.notNull(certificationId, "certificationId can not be null");
        streamBridge.send(streamChannelsProperties.getCertificationManagement(), UpdateCertificateVisibilityRequestEvent.builder()
                .studentWalletHash(ownerWallet)
                .certificationId(certificationId)
                .isVisible(isVisible)
                .build());
    }

    /**
     * @param certificationId
     * @return
     * @throws Throwable
     */
    @Override
    public CertificateIssuedDetailDTO getDetail(final String certificationId) throws Throwable {
        Assert.notNull(certificationId, "certificationId can not be null");
        final CertificateIssuedEntity certificate = trustCertificationRepository.getCertificateDetail(certificationId);
        return mapToCertificateIssuedDetail(certificate);
    }

    /**
     * @param ownerWallet
     * @return
     * @throws Throwable
     */
    @Override
    public Iterable<SimpleCertificateIssuedDTO> getMyCertificatesAsRecipient(final String ownerWallet) throws Throwable {
        Assert.notNull(ownerWallet, "ownerWallet can not be null");
        final List<CertificateIssuedEntity> myCertificates = trustCertificationRepository.getMyCertificatesAsRecipient(ownerWallet);
        return simpleCertificateIssuedMapper.entityToDTO(myCertificates);
    }

    /**
     * @param ownerWallet
     * @return
     * @throws Throwable
     */
    @Override
    public Iterable<SimpleCertificateIssuedDTO> getMyCertificatesAsIssuer(final String ownerWallet) throws Throwable {
        Assert.notNull(ownerWallet, "ownerWallet can not be null");
        final List<CertificateIssuedEntity> myCertificates = trustCertificationRepository.getMyCertificatesAsIssuer(ownerWallet);
        return simpleCertificateIssuedMapper.entityToDTO(myCertificates);
    }

    /**
     * @param issueCertificate
     * @throws Throwable
     */
    @Override
    public CertificateIssuanceRequestDTO issueCertificateRequest(final IssueCertificateRequestDTO issueCertificate) throws Throwable {
        Assert.notNull(issueCertificate.getStudentWalletHash(), "Student Wallet Hash can not be null");
        Assert.notNull(issueCertificate.getCertificateCourseEditionId(), "certificateCourseId can not be null");
        Assert.notNull(issueCertificate.getQualification(), "qualification can not be null");
        final CertificationCourseEditionEntity certificationCourseEditionEntity = certificationCourseEditionRepository.findById(new ObjectId(issueCertificate.getCertificateCourseEditionId()))
                .orElseThrow(() -> new IllegalStateException("Course not found"));
        final UserEntity caEntity = certificationCourseEditionEntity.getCaMember();
        final UserEntity studentEntity = userRepository.findOneByWalletHash(issueCertificate.getStudentWalletHash())
                .orElseThrow(() -> new IllegalStateException("Student not found"));
        final CertificateIssuanceRequestEntity certificateRequest = CertificateIssuanceRequestEntity
                .builder()
                .caMember(caEntity)
                .courseEdition(certificationCourseEditionEntity)
                .createdAt(new Date())
                .status(CertificateStatusEnum.PENDING_REVIEW)
                .qualification(issueCertificate.getQualification())
                .type(CertificateTypeEnum.valueOf(issueCertificate.getType()))
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
     * @param id
     */
    @Override
    public CertificateIssuanceRequestDTO acceptCertificateRequest(final String id) {
        Assert.notNull(id, "Id can not be null");
        final CertificateIssuanceRequestEntity certificateRequestUpdated = certificateIssuanceRequestRepository.updateStatus(new ObjectId(id), CertificateStatusEnum.REVIEWED);
        final CertificateIssuanceRequestDTO certificateIssuanceRequestDTO = certificateIssuanceRequestMapper.entityToDTO(certificateRequestUpdated);
        streamBridge.send(streamChannelsProperties.getCertificationManagement(), OnNewCertificateRequestAcceptedEvent
                .builder()
                .certificationRequestId(certificateIssuanceRequestDTO.getId())
                .build());
        streamBridge.send(streamChannelsProperties.getNotificationDeliveryRequest(), CertificateRequestAcceptedNotificationEvent.builder()
                .id(certificateIssuanceRequestDTO.getId())
                .build());
        return certificateIssuanceRequestDTO;
    }

    /**
     * @param id
     */
    @Override
    public CertificateIssuanceRequestDTO rejectCertificateRequest(final String id) {
        Assert.notNull(id, "Id can not be null");
        final CertificateIssuanceRequestEntity certificateRequestUpdated = certificateIssuanceRequestRepository.updateStatus(new ObjectId(id), CertificateStatusEnum.REJECTED);
        final CertificateIssuanceRequestDTO certificateIssuanceRequestDTO = certificateIssuanceRequestMapper.entityToDTO(certificateRequestUpdated);
        streamBridge.send(streamChannelsProperties.getNotificationDeliveryRequest(), CertificateRequestRejectedNotificationEvent.builder()
                .id(certificateIssuanceRequestDTO.getId())
                .build());
        return certificateIssuanceRequestDTO;
    }

    /**
     * @param ownerWallet
     * @param certificationId
     * @return
     * @throws Throwable
     */
    @Override
    public CertificateIssuedDetailDTO renewCertificate(final String ownerWallet, final String certificationId) throws Throwable {
        Assert.notNull(ownerWallet, "ownerWallet can not be null");
        Assert.notNull(certificationId, "certificationId can not be null");
        final CertificateIssuedEntity certificate = trustCertificationRepository.renewCertificate(ownerWallet, certificationId);
        streamBridge.send(streamChannelsProperties.getNotificationDeliveryRequest(), CertificateRenewedNotificationEvent.builder()
                .id(certificate.getId())
                .build());
        return mapToCertificateIssuedDetail(certificate);
    }

    /**
     * @param studentWalletHash
     * @return
     */
    @Override
    public Iterable<CertificateIssuanceRequestDTO> getCertificatesIssuanceRequestsFromStudent(final String studentWalletHash) {
        Assert.notNull(studentWalletHash, "Student Wallet hash can not be null");
        Iterable<CertificateIssuanceRequestEntity> certificateRequests = certificateIssuanceRequestRepository.findByStudentOrderByUpdatedAtDesc(studentWalletHash);
        return certificateIssuanceRequestMapper.entityToDTO(certificateRequests);
    }

    /**
     * @param ownerId
     * @return
     */
    @Override
    public Iterable<CertificateIssuanceRequestDTO> getCertificatesIssuanceRequestsFromCa(final String ownerId) {
        Assert.notNull(ownerId, "Owner Id can not be null");
        Iterable<CertificateIssuanceRequestEntity> certificateRequests = certificateIssuanceRequestRepository.findByCaMemberOrderByUpdatedAtDesc(new ObjectId(ownerId));
        return certificateIssuanceRequestMapper.entityToDTO(certificateRequests);
    }

    /**
     * @param certificateId
     * @return
     * @throws Exception
     */
    @Override
    public FileInfoDTO getCertificateFile(final String certificateId) throws Exception {
        Assert.notNull(certificateId, "Certificate Id can not be null");
        final CertificateIssuedEntity certificate = trustCertificationRepository.getCertificateDetail(certificateId);
        final byte[] fileContents = ipfsService.get(certificate.getFileCid());
        return FileInfoDTO
                .builder()
                .content(fileContents)
                .contentType(MediaType.APPLICATION_PDF_VALUE)
                .size((long) fileContents.length)
                .build();
    }

    /**
     *
     * @param certificateId
     * @param width
     * @param height
     * @return
     * @throws Exception
     */
    @Override
    public FileInfoDTO generateCertificateQr(final String certificateId, final Integer width, final Integer height) throws Exception {
        Assert.notNull(certificateId, "Certificate Id can not be null");
        Assert.notNull(width, "Width can not be null");
        Assert.notNull(height, "Height can not be null");
        final String certificateQrDataEncrypted = cryptService.encrypt(certificateId);
        final byte[] qrCodeData = qrCodeGenerator.getQRCodeImage(certificateQrDataEncrypted, width, height);
        return FileInfoDTO
                .builder()
                .content(qrCodeData)
                .contentType(MediaType.IMAGE_PNG_VALUE)
                .size((long) qrCodeData.length)
                .build();
    }

    /**
     *
     * @param certificateId
     * @return
     * @throws Exception
     */
    @Override
    public FileInfoDTO getCertificateImage(final String certificateId) throws Exception {
        Assert.notNull(certificateId, "Certificate Id can not be null");
        final CertificateIssuedEntity certificate = trustCertificationRepository.getCertificateDetail(certificateId);
        final byte[] fileContents = ipfsService.get(certificate.getImageCid());
        return FileInfoDTO
                .builder()
                .content(fileContents)
                .contentType(MediaType.IMAGE_PNG_VALUE)
                .size((long) fileContents.length)
                .build();
    }

    /**
     *
     * @param certificatePayload
     * @return
     * @throws Exception
     */
    @Override
    public CertificateIssuedDetailDTO validateCertificate(final String certificatePayload) throws Exception {
        Assert.notNull(certificatePayload, "CertificatePayload can not be null");
        final String certificateId = cryptService.decrypt(certificatePayload);
        if(!trustCertificationRepository.isCertificateValid(certificateId)) {
            throw new CertificateInvalidException();
        }
        final CertificateIssuedEntity certificate = trustCertificationRepository.getCertificateDetail(certificateId);
        return mapToCertificateIssuedDetail(certificate);
    }

    private CertificateIssuedDetailDTO mapToCertificateIssuedDetail(final CertificateIssuedEntity certificate) {
        final CertificateIssuanceRequestEntity certificateIssuanceRequestEntity = certificateIssuanceRequestRepository.findByCertificateId(certificate.getId())
                .orElseThrow(() -> new IllegalStateException("Certification can not be found"));
        return CertificateIssuedDetailDTO.builder()
                .student(simpleUserMapper.entityToDTO(certificateIssuanceRequestEntity.getStudent()))
                .caMember(simpleUserMapper.entityToDTO(certificateIssuanceRequestEntity.getCaMember()))
                .course(certificationCourseEditionDetailMapper.entityToDTO(certificateIssuanceRequestEntity.getCourseEdition()))
                .certificate(simpleCertificateIssuedMapper.entityToDTO(certificate))
                .build();
    }
}
