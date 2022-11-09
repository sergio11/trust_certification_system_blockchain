package com.dreamsoftware.tcs.processor.handlers;

import com.dreamsoftware.tcs.persistence.bc.repository.ICertificationCourseBlockchainRepository;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEditionEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseEditionRepository;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseRepository;
import com.dreamsoftware.tcs.stream.events.course.EnableCertificationCourseEvent;
import com.dreamsoftware.tcs.stream.events.notifications.course.CourseEnabledNotificationEvent;
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
public class EnableCertificationCourseHandler extends AbstractProcessAndReturnHandler<EnableCertificationCourseEvent, CourseEnabledNotificationEvent> {

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
    public CourseEnabledNotificationEvent onHandle(final EnableCertificationCourseEvent event) throws RepositoryException {
        Assert.notNull(event, "Event can not be null");
        log.debug("EnableCertificationCourseHandler CALLED!");
        final CertificationCourseEntity certificationCourseEntity = certificationCourseRepository.updateStatus(new ObjectId(event.getCourseId()), CertificationCourseStateEnum.ENABLED);
        final Iterable<CertificationCourseEditionEntity> certificationCourseEditionEntities = certificationCourseEditionRepository.findAllByCourse(certificationCourseEntity.getId());
        for (CertificationCourseEditionEntity edition: certificationCourseEditionEntities) {
            if(edition.getStatus() == CertificationCourseStateEnum.DISABLED) {
                certificationCourseBlockchainRepository.enable(event.getCaWalletHash(), edition.getId().toString());
                certificationCourseEditionRepository.updateStatus(edition.getId(), CertificationCourseStateEnum.ENABLED);
            }
        }
        return CourseEnabledNotificationEvent.builder()
                .id(event.getCourseId())
                .name(certificationCourseEntity.getName())
                .build();
    }
}
