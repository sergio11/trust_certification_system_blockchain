package com.dreamsoftware.tcs.processor.handlers;

import com.dreamsoftware.tcs.persistence.bc.repository.ICertificationCourseBlockchainRepository;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEditionEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseEditionRepository;
import com.dreamsoftware.tcs.stream.events.course.RemoveCertificationCourseEditionEvent;
import com.dreamsoftware.tcs.stream.events.notifications.course.CourseDeletedNotificationEvent;
import com.dreamsoftware.tcs.stream.events.notifications.course.CourseEditionDeletedNotificationEvent;
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
public class RemoveCertificationCourseEditionHandler extends AbstractCourseManagementHandler<RemoveCertificationCourseEditionEvent, CourseEditionDeletedNotificationEvent> {

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
    public CourseEditionDeletedNotificationEvent onHandle(final RemoveCertificationCourseEditionEvent event) throws RepositoryException {
        Assert.notNull(event, "Event can not be null");
        final CertificationCourseEditionEntity certificationCourseEditionEntity = certificationCourseEditionRepository.findById(new ObjectId(event.getEditionId()))
                .orElseThrow(() -> new IllegalStateException("Course Edition can not be found"));
        log.debug("RemoveCertificationCourseEditionHandler CALLED!");
        final CertificationCourseStateEnum currentState = certificationCourseEditionEntity.getStatus();
        if(currentState == CertificationCourseStateEnum.NOT_VALIDATED || currentState == CertificationCourseStateEnum.REMOVED) {
            throw new IllegalStateException("Course Edition can not be removed");
        }
        certificationCourseBlockchainRepository.remove(event.getCaWalletHash(), certificationCourseEditionEntity.getId().toString());
        certificationCourseEditionRepository.updateStatus(certificationCourseEditionEntity.getId(), CertificationCourseStateEnum.REMOVED);
        return CourseEditionDeletedNotificationEvent.builder()
                .courseId(event.getCourseId())
                .editionId(event.getEditionId())
                .name(certificationCourseEditionEntity.getCourse().getName())
                .build();


    }
}
