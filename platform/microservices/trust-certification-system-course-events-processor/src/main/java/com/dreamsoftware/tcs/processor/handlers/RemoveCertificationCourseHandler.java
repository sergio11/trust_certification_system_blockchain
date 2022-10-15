package com.dreamsoftware.tcs.processor.handlers;

import com.dreamsoftware.tcs.persistence.bc.repository.ICertificationCourseBlockchainRepository;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseStateEnum;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseRepository;
import com.dreamsoftware.tcs.stream.events.course.RemoveCertificationCourseEvent;
import com.dreamsoftware.tcs.stream.events.notifications.course.CourseDeletedNotificationEvent;
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
public class RemoveCertificationCourseHandler extends AbstractCourseManagementHandler<RemoveCertificationCourseEvent, CourseDeletedNotificationEvent> {

    /**
     * Certification Course Repository
     */
    private final CertificationCourseRepository certificationCourseRepository;

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
    public CourseDeletedNotificationEvent onHandle(final RemoveCertificationCourseEvent event) throws RepositoryException {
        Assert.notNull(event, "Event can not be null");
        certificationCourseBlockchainRepository.remove(event.getCaWalletHash(), event.getCourseId());
        final CertificationCourseEntity certificationCourseEntity = certificationCourseRepository.updateStatus(new ObjectId(event.getCourseId()), CertificationCourseStateEnum.REMOVED);
        return CourseDeletedNotificationEvent.builder()
                .id(event.getCourseId())
                .name(certificationCourseEntity.getName())
                .build();
    }
}
