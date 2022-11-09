package com.dreamsoftware.tcs.processor.handlers;

import com.dreamsoftware.tcs.persistence.bc.repository.ICertificationCourseBlockchainRepository;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEditionEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseEditionRepository;
import com.dreamsoftware.tcs.stream.events.course.DisableCertificationCourseEditionEvent;
import com.dreamsoftware.tcs.stream.events.notifications.course.CourseEditionDisabledNotificationEvent;
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
public class DisableCertificationCourseEditionHandler extends AbstractProcessAndReturnHandler<DisableCertificationCourseEditionEvent, CourseEditionDisabledNotificationEvent> {

    /**
     * Certification Course Edition Repository
     */
    private final CertificationCourseEditionRepository certificationCourseEditionRepository;

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
    public CourseEditionDisabledNotificationEvent onHandle(final DisableCertificationCourseEditionEvent event) throws RepositoryException {
        Assert.notNull(event, "Event can not be null");
        final CertificationCourseEditionEntity certificationCourseEditionEntity = certificationCourseEditionRepository.findById(new ObjectId(event.getEditionId()))
                .orElseThrow(() -> new IllegalStateException("Course Edition can not be found"));
        log.debug("DisableCertificationCourseEditionHandler CALLED!");
        if(certificationCourseEditionEntity.getStatus() != CertificationCourseStateEnum.ENABLED) {
            throw new IllegalStateException("Course Edition can not be disabled");
        }
        certificationCourseBlockchainRepository.disable(event.getCaWalletHash(), certificationCourseEditionEntity.getId().toString());
        certificationCourseEditionRepository.updateStatus(certificationCourseEditionEntity.getId(), CertificationCourseStateEnum.DISABLED);
        return CourseEditionDisabledNotificationEvent.builder()
                .courseId(event.getCourseId())
                .editionId(event.getEditionId())
                .name(certificationCourseEditionEntity.getCourse().getName())
                .build();
    }
}
