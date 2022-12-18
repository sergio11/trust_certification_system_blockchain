package com.dreamsoftware.tcs.processor.handlers;

import com.dreamsoftware.tcs.persistence.bc.repository.ITokenManagementBlockchainRepository;
import com.dreamsoftware.tcs.persistence.exception.RepositoryException;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseAttendeeControlEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEditionAttendeeEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.CertificationCourseEditionEntity;
import com.dreamsoftware.tcs.persistence.nosql.entity.UserEntity;
import com.dreamsoftware.tcs.persistence.nosql.repository.CertificationCourseEditionRepository;
import com.dreamsoftware.tcs.persistence.nosql.repository.UserRepository;
import com.dreamsoftware.tcs.service.ISecurityTokenGeneratorService;
import com.dreamsoftware.tcs.stream.events.AbstractEvent;
import com.dreamsoftware.tcs.stream.events.course.CourseEditionEnrollmentRequestEvent;
import com.dreamsoftware.tcs.stream.events.notifications.course.CourseEditionEnrolledNotificationEvent;
import com.dreamsoftware.tcs.utils.AbstractProcessAndReturnHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Date;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
@Slf4j
public class CourseEditionEnrollmentRequestHandler extends AbstractProcessAndReturnHandler<CourseEditionEnrollmentRequestEvent> {

    private final CertificationCourseEditionRepository certificationCourseEditionRepository;

    private final ITokenManagementBlockchainRepository tokenManagementBlockchainRepository;

    private final UserRepository userRepository;

    private final ISecurityTokenGeneratorService securityTokenGeneratorService;

    /**
     * @param event
     * @return
     * @throws RepositoryException
     */
    @Override
    public AbstractEvent onHandle(final CourseEditionEnrollmentRequestEvent event) throws RepositoryException {
        Assert.notNull(event, "Event can not be null");
        Assert.notNull(event.getCourseId(), "Course Id can not be null");
        Assert.notNull(event.getStudentWalletHash(), "Student Wallet hash can not be null");
        Assert.notNull(event.getEditionId(), "Course Edition Id can not be null");
        log.debug("CourseEditionEnrollmentRequestHandler onHandle CALLED!");
        AbstractEvent notificationEvent = null;
        final UserEntity studentEntity = userRepository.findOneByWalletHash(event.getStudentWalletHash()).orElseThrow();
        final CertificationCourseEditionEntity certificationCourseEditionEntity =
                certificationCourseEditionRepository.findById(new ObjectId(event.getEditionId())).orElseThrow();
        final CertificationCourseAttendeeControlEntity attendeeControlEntity = certificationCourseEditionEntity.getAttendeeControl();
        if (attendeeControlEntity != null && (attendeeControlEntity.getMaxAttendanceCount() == 0 || attendeeControlEntity.getMaxAttendanceCount() > 0 &&
                attendeeControlEntity.getAttendedUsers().size() < attendeeControlEntity.getMaxAttendanceCount())) {
            if (attendeeControlEntity.getEnrollCost() > 0) {
                log.debug("tokenManagementBlockchainRepository.transfer enroll cost CALLED!");
                tokenManagementBlockchainRepository.transfer(
                        event.getStudentWalletHash(),
                        certificationCourseEditionEntity.getCaMember().getWalletHash(),
                        attendeeControlEntity.getEnrollCost());
            }
            log.debug("add CertificationCourseEditionAttendeeEntity CALLED!");
            attendeeControlEntity.getAttendedUsers().add(CertificationCourseEditionAttendeeEntity.builder()
                    .student(studentEntity)
                    .enrolledAt(new Date())
                    .securityToken(securityTokenGeneratorService.generateToken(studentEntity.getId().toString()))
                    .build());
            certificationCourseEditionRepository.save(certificationCourseEditionEntity);

            notificationEvent = CourseEditionEnrolledNotificationEvent.builder()
                    .studentWalletHash(event.getStudentWalletHash())
                    .editionId(event.getEditionId())
                    .courseId(event.getCourseId())
                    .courseName(certificationCourseEditionEntity.getName().isBlank() ? certificationCourseEditionEntity.getCourse().getName() : certificationCourseEditionEntity.getName())
                    .build();
        }
        return notificationEvent;
    }
}
