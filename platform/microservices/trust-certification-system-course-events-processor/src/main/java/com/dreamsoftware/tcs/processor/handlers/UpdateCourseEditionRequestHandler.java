package com.dreamsoftware.tcs.processor.handlers;

import com.dreamsoftware.tcs.persistence.bc.repository.ICertificationCourseBlockchainRepository;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEditionEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseEditionRepository;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseRepository;
import com.dreamsoftware.tcs.stream.events.course.UpdateCertificationCourseEditionRequestEvent;
import com.dreamsoftware.tcs.stream.events.notifications.course.CertificationCourseEditionUpdatedNotificationEvent;
import com.dreamsoftware.tcs.utils.AbstractProcessAndReturnHandler;
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
public class UpdateCourseEditionRequestHandler extends AbstractProcessAndReturnHandler<UpdateCertificationCourseEditionRequestEvent, CertificationCourseEditionUpdatedNotificationEvent> {

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
    public CertificationCourseEditionUpdatedNotificationEvent onHandle(final UpdateCertificationCourseEditionRequestEvent event) throws RepositoryException {
        Assert.notNull(event, "Event can not be null");
        final CertificationCourseEditionEntity certificationCourseEditionEntity = certificationCourseEditionRepository.findById(new ObjectId(event.getCourseEditionId()))
                .orElseThrow(() -> new IllegalStateException("Course Edition can not be found"));
        log.debug("UpdateCourseEditionRequestHandler CALLED!");
        certificationCourseBlockchainRepository.update(event.getCaWalletHash(), certificationCourseEditionEntity.getId().toString(),
                certificationCourseEditionEntity.getCostOfIssuingCertificate(), certificationCourseEditionEntity.getDurationInHours(),
                certificationCourseEditionEntity.getExpirationInDays(), certificationCourseEditionEntity.getCanBeRenewed(),
                certificationCourseEditionEntity.getCostOfRenewingCertificate());
        log.debug("Certification Course Edition UPDATED!");
        return CertificationCourseEditionUpdatedNotificationEvent.builder()
                .courseId(event.getCourseId())
                .editionId(event.getCourseEditionId())
                .courseName(certificationCourseEditionEntity.getCourse().getName())
                .build();
    }
}
