package com.dreamsoftware.tcs.processor.handlers;

import com.dreamsoftware.tcs.persistence.bc.repository.ICertificationCourseBlockchainRepository;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEditionEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseEditionRepository;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseRepository;
import com.dreamsoftware.tcs.stream.events.AbstractEvent;
import com.dreamsoftware.tcs.stream.events.course.RemoveCertificationCourseEvent;
import com.dreamsoftware.tcs.stream.events.notifications.course.CourseDeletedNotificationEvent;
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
public class RemoveCertificationCourseHandler extends AbstractProcessAndReturnHandler<RemoveCertificationCourseEvent> {

    /**
     * Certification Course Repository
     */
    private final CertificationCourseRepository certificationCourseRepository;

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
    public AbstractEvent onHandle(final RemoveCertificationCourseEvent event) throws RepositoryException {
        Assert.notNull(event, "Event can not be null");
        final CertificationCourseEntity certificationCourseEntity = certificationCourseRepository.updateStatus(new ObjectId(event.getCourseId()), CertificationCourseStateEnum.REMOVED);
        log.debug("RemoveCertificationCourseHandler CALLED!");
        final Iterable<CertificationCourseEditionEntity> certificationCourseEditionEntities = certificationCourseEditionRepository.findAllByCourse(certificationCourseEntity.getId());
        for (CertificationCourseEditionEntity edition: certificationCourseEditionEntities) {
            if(edition.getStatus() == CertificationCourseStateEnum.NOT_VALIDATED) {
                certificationCourseBlockchainRepository.remove(event.getCaWalletHash(), edition.getId().toString());
                certificationCourseEditionRepository.updateStatus(edition.getId(), CertificationCourseStateEnum.REMOVED);
            }
        }
        return CourseDeletedNotificationEvent.builder()
                .id(event.getCourseId())
                .build();
    }
}
