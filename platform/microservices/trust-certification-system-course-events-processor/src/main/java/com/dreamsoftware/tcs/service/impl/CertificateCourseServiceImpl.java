package com.dreamsoftware.tcs.service.impl;

import com.dreamsoftware.tcs.model.events.CertificationCourseRegisteredEvent;
import com.dreamsoftware.tcs.model.events.CourseCertificateRegistrationRequestEvent;
import com.dreamsoftware.tcs.persistence.bc.repository.ICertificationCourseBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationCourseModelEntity;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseRepository;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.service.ICertificateCourseService;
import com.dreamsoftware.tcs.service.INotificationService;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 *
 * @author ssanchez
 */
@Service
@RequiredArgsConstructor
public class CertificateCourseServiceImpl implements ICertificateCourseService {

    private static final Logger logger = LoggerFactory.getLogger(CertificateCourseServiceImpl.class);

    /**
     * Certification Course Repository
     */
    private final CertificationCourseRepository certificationCourseRepository;

    /**
     * User Repository
     */
    private final UserRepository userRepository;

    /**
     * Certification Course Blockchain Repository
     */
    private final ICertificationCourseBlockchainRepository certificationCourseBlockchainRepository;

    /**
     * Notification Service
     */
    private final INotificationService notificationService;

    /**
     *
     * @param event
     * @throws RepositoryException
     */
    @Override
    public CertificationCourseModelEntity register(CourseCertificateRegistrationRequestEvent event) throws RepositoryException {
        Assert.notNull(event, "event can not be null");
        return certificationCourseBlockchainRepository.register(event.getCaWalletHash(), event.getName(),
                event.getCostOfIssuingCertificate(), event.getDurationInHours(), event.getExpirationInDays(), event.getCanBeRenewed(), event.getCostOfRenewingCertificate());
    }

    /**
     *
     * @param event
     */
    @Override
    public void register(CertificationCourseRegisteredEvent event) {
        Assert.notNull(event, "event can not be null");
        Assert.notNull(event.getCaWalletHash(), "caWalletHash can not be null");
        Assert.notNull(event.getCertificationCourse().getId(), "courseId can not be null");
        final String caWalletHash = event.getCaWalletHash();
        final String courseId = event.getCertificationCourse().getId();
        logger.debug("register: caWalletHash: " + caWalletHash + " courseId: " + courseId + " CALLED!");
        final UserEntity caUser = userRepository.findOneByWalletHash(caWalletHash).orElseThrow(() -> new IllegalStateException("CA Wallet Hash not found"));
        final CertificationCourseEntity certificationCourseEntity = CertificationCourseEntity
                .builder()
                .ca(caUser)
                .createdAt(new Date())
                .status(CertificationCourseStateEnum.ENABLED)
                .courseId(courseId)
                .build();
        certificationCourseRepository.save(certificationCourseEntity);
        notificationService.onCACertificationCourseRegistered(certificationCourseEntity);
    }
}