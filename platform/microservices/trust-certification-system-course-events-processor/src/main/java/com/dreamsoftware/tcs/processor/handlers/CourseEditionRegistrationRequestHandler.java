package com.dreamsoftware.tcs.processor.handlers;

import com.dreamsoftware.tcs.persistence.bc.repository.ICertificationCourseBlockchainRepository;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEditionEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseEditionRepository;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseRepository;
import com.dreamsoftware.tcs.stream.events.course.NewCertificationCourseEditionRegistrationRequestEvent;
import com.dreamsoftware.tcs.stream.events.notifications.course.CourseEditionRegisteredNotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
@Slf4j
public class CourseEditionRegistrationRequestHandler extends AbstractCourseManagementHandler<NewCertificationCourseEditionRegistrationRequestEvent, CourseEditionRegisteredNotificationEvent> {

    /**
     * Certification Course Repository
     */
    private final CertificationCourseRepository certificationCourseRepository;

    /**
     * Certification Course Edition Repository
     */
    private final CertificationCourseEditionRepository  certificationCourseEditionRepository;

    /**
     * Certification Course Blockchain Repository
     */
    private final ICertificationCourseBlockchainRepository certificationCourseBlockchainRepository;

    /**
     *
     * @param event
     * @return
     * @throws RepositoryException
     */
    @Override
    public CourseEditionRegisteredNotificationEvent onHandle(final NewCertificationCourseEditionRegistrationRequestEvent event) throws RepositoryException {
        Assert.notNull(event, "Event can not be null");
        final CertificationCourseEditionEntity certificationCourseEditionEntity = certificationCourseEditionRepository.findById(new ObjectId(event.getCourseEditionId()))
                .orElseThrow(() -> new IllegalStateException("Course Edition can not be found"));
        log.debug("CourseCertificateRegistrationRequestHandler CALLED!");
        certificationCourseBlockchainRepository.addCertificationCourse(event.getCaWalletHash(), certificationCourseEditionEntity.getId().toString(),
                certificationCourseEditionEntity.getCostOfIssuingCertificate(), certificationCourseEditionEntity.getDurationInHours(), certificationCourseEditionEntity.getExpirationInDays(),
                certificationCourseEditionEntity.getCanBeRenewed(), certificationCourseEditionEntity.getCostOfRenewingCertificate());
        log.debug("Certification Course Edition REGISTERED!");
        final CertificationCourseEntity certificationCourseEntity = certificationCourseRepository.findById(new ObjectId(event.getCourseId()))
                .orElseThrow(() -> new IllegalStateException("Course can not be found"));
        certificationCourseEditionRepository.updateStatus(certificationCourseEditionEntity.getId(), certificationCourseEntity.getStatus());
        return CourseEditionRegisteredNotificationEvent.builder()
                .courseId(certificationCourseEntity.getId().toString())
                .editionId(certificationCourseEditionEntity.getId().toString())
                .courseName(certificationCourseEntity.getName())
                .caWalletHash(event.getCaWalletHash())
                .build();
    }
}
