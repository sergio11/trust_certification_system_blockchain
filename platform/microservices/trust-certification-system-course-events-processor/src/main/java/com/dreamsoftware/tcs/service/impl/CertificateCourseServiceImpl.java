package com.dreamsoftware.tcs.service.impl;

import com.dreamsoftware.tcs.stream.events.course.CertificationCourseRegisteredEvent;
import com.dreamsoftware.tcs.stream.events.course.CourseCertificateRegistrationRequestEvent;
import com.dreamsoftware.tcs.persistence.bc.repository.ICertificationCourseBlockchainRepository;
import com.dreamsoftware.tcs.persistence.bc.repository.entity.CertificationCourseModelEntity;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseRepository;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.service.ICertificateCourseService;
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
    public CertificationCourseEntity register(final CertificationCourseRegisteredEvent event) {
        Assert.notNull(event, "event can not be null");
        Assert.notNull(event.getCaWalletHash(), "caWalletHash can not be null");
        Assert.notNull(event.getCertificationCourse().getId(), "courseId can not be null");
        final String caWalletHash = event.getCaWalletHash();
        final String courseId = event.getCertificationCourse().getId();
        log.debug("register: caWalletHash: " + caWalletHash + " courseId: " + courseId + " CALLED!");
        final UserEntity caUser = userRepository.findOneByWalletHash(caWalletHash).orElseThrow(() -> new IllegalStateException("CA Wallet Hash not found"));
        final CertificationCourseEntity certificationCourseEntity = CertificationCourseEntity
                .builder()
                .ca(caUser)
                .createdAt(new Date())
                .status(CertificationCourseStateEnum.ENABLED)
                .courseId(courseId)
                .build();
        return certificationCourseRepository.save(certificationCourseEntity);
    }
}
