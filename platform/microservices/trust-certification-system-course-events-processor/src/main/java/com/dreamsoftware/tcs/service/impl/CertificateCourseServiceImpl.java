package com.dreamsoftware.tcs.service.impl;

import com.dreamsoftware.tcs.i18n.service.I18NService;
import com.dreamsoftware.tcs.mail.model.CertificationCourseRegisteredMailRequestDTO;
import com.dreamsoftware.tcs.mail.service.IMailClientService;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 *
 * @author ssanchez
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CertificateCourseServiceImpl implements ICertificateCourseService {

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
     * Mail Client Service
     */
    private final IMailClientService mailClientService;

    /**
     * I18N Service
     */
    private final I18NService i18nService;

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
    public void register(final CertificationCourseRegisteredEvent event) {
        Assert.notNull(event, "event can not be null");
        Assert.notNull(event.getCaWalletHash(), "caWalletHash can not be null");
        Assert.notNull(event.getCertificationCourse().getId(), "courseId can not be null");
        final String caWalletHash = event.getCaWalletHash();
        final String courseId = event.getCertificationCourse().getId();
        final String courseName = event.getCertificationCourse().getName();
        log.debug("register: caWalletHash: " + caWalletHash + " courseId: " + courseId + " CALLED!");
        final UserEntity caUser = userRepository.findOneByWalletHash(caWalletHash).orElseThrow(() -> new IllegalStateException("CA Wallet Hash not found"));
        final CertificationCourseEntity certificationCourseEntity = CertificationCourseEntity
                .builder()
                .ca(caUser)
                .createdAt(new Date())
                .status(CertificationCourseStateEnum.ENABLED)
                .courseId(courseId)
                .build();
        final CertificationCourseEntity certificationCourseEntitySaved = certificationCourseRepository.save(certificationCourseEntity);
        notificationService.onCACertificationCourseRegistered(certificationCourseEntitySaved);
        mailClientService.sendMail(CertificationCourseRegisteredMailRequestDTO.builder()
                .courseId(certificationCourseEntitySaved.getCourseId())
                .courseName(courseName)
                .email(caUser.getEmail())
                .locale(i18nService.parseLocaleOrDefault(caUser.getLanguage()))
                .build());
    }
}
